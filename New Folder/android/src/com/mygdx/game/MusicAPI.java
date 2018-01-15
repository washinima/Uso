package com.mygdx.game;

/**
 * Created by Whisp on 08/01/2018.
 */

import android.content.Context;
import android.graphics.Rect;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.ArrayList;
import android.media.*;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import be.tarsos.dsp.*;
import be.tarsos.dsp.io.PipedAudioStream;
import be.tarsos.dsp.io.TarsosDSPAudioInputStream;
import be.tarsos.dsp.io.android.AndroidAudioPlayer;
import be.tarsos.dsp.io.android.AndroidFFMPEGLocator;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.onsets.OnsetHandler;
import be.tarsos.dsp.onsets.PercussionOnsetDetector;


public class MusicAPI
{

    AudioDispatcher dispatcher;
    String directory;
    Thread thread;
    int sampleRate = 22050, bufferSize = 3072, bufferOverlap = 0;
    FlowGenerator generator;

    private BufferedWriter writer = null;

    boolean creating;

    ArrayList<Circle> list;

    public void Setup(Context context, String dir)
    {
        new AndroidFFMPEGLocator(context);
        MusicDirectory(dir);
    }

    public void Start(Context context)
    {
        try {

            int hash = directory.hashCode();

            String source = Integer.toString(hash) + ".txt";

            File file = new File(context.getFilesDir(), source);

            if(file.isFile())
            {
                list = new ArrayList<Circle>();
                ArrayList<String[]> circleStuff;
                circleStuff = ReadFile(file);

                for (String[] circleInfo : circleStuff)
                {
                    /*time,x,y,circleSize,color,num*/
                    Circle circle = new Circle(Double.parseDouble(circleInfo[0]),
                            Integer.getInteger(circleInfo[1]),
                            Integer.getInteger(circleInfo[2]),
                            (int)(Gdx.graphics.getHeight() * 0.10f),
                            new Color(Integer.getInteger(circleInfo[3]), Integer.getInteger(circleInfo[4]), Integer.getInteger(circleInfo[5]), 1),
                            circleInfo[6]);
                    list.add(circle);
                }
            }
            else
            {
                list = new ArrayList<Circle>();
                generator = new FlowGenerator((int)(Gdx.graphics.getHeight() * 0.35f), (int)(Gdx.graphics.getHeight() * 0.10f));

                PipedAudioStream stream = new PipedAudioStream(directory);

                TarsosDSPAudioInputStream audioStream = stream.getMonoStream(sampleRate, bufferOverlap);

                dispatcher = new AudioDispatcher(audioStream, bufferSize, bufferOverlap);

                PercussionDetection(file);

            }
        } catch (Exception e) {}

    }

    private void MusicDirectory(String dir)
    {
        this.directory = dir;
    }

    public void PercussionDetection(File path)
    {
        PercussionOnsetDetector a = null;

        try
        {
            writer = new BufferedWriter(new FileWriter(path));

            a = new PercussionOnsetDetector(dispatcher.getFormat().getSampleRate(), bufferSize,
                new OnsetHandler()
                {
                    @Override
                    public void handleOnset(double v, double v1) {
                        ArrayList<Circle> toAdd = generator.GenerateCombo(v);
                        for (Circle c : toAdd)
                        {
                            list.add(c);
                            try {
                                writer.write(c.getTime() + "," + c.getX() + "," + c.getY() + "\n");
                            } catch (Exception e) {}
                        }
                    }
                },
                28, -1.5);
        } catch (Exception e) {}
        finally {
            try {
                writer.close();
            } catch (Exception e) {}
        }
        dispatcher.addAudioProcessor(a);

        thread = new Thread(dispatcher);
        thread.run();
    }

    public void WriteFile(File path)
    {
        BufferedWriter writer = null;
        try
        {
            writer = new BufferedWriter(new FileWriter(path));
            Log.d("Write", "1");
            /*time,x,y*/
            for (Circle c : list)
            {
                writer.write(c.getTime() + "," +
                        c.getX() + "," +
                        c.getY() + "," +
                        c.getColor().r + "," +
                        c.getColor().g + "," +
                        c.getColor().b + "," +
                        c.getNum() + "\n");
            }
        } catch (Exception e) {}
        finally {
            try {
                writer.close();
            } catch (Exception e) {}
        }
    }

    public ArrayList<String[]> ReadFile(File path)
    {
        BufferedReader br = null;
        Log.d("Read", "2");
        ArrayList<String[]> stuff;
        stuff = new ArrayList<String[]>();

        try
        {
            FileReader r = new FileReader(path);
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                line = br.readLine();
                stuff.add(line.split(","));
            }
        } catch (Exception e) { }
        finally {
            try {br.close();} catch (Exception e) {}
        }
        return stuff;
    }
}
