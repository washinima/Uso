package com.mygdx.game;

/**
 * Created by Whisp on 08/01/2018.
 */

import android.content.Context;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import android.util.Log;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import be.tarsos.dsp.*;
import be.tarsos.dsp.io.PipedAudioStream;
import be.tarsos.dsp.io.TarsosDSPAudioInputStream;
import be.tarsos.dsp.io.android.AndroidFFMPEGLocator;
import be.tarsos.dsp.onsets.OnsetHandler;
import be.tarsos.dsp.onsets.PercussionOnsetDetector;


public class MusicAPI
{

    class ThreadB extends Thread{
        @Override
        public void run(){
            synchronized(this)
            {
                dispatcher.run();
                notify();
            }
            Log.d("Thread", "notified");
        }
    }

    AudioDispatcher dispatcher;
    String directory;
    int sampleRate = 22050, bufferSize = 3072, bufferOverlap = 0;
    FlowGenerator generator;

    File file;

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

            file = new File(context.getFilesDir(), source);

            if(file.exists() && !file.isDirectory())
            {
                SpriteBatch batch = new SpriteBatch();

                list = new ArrayList<Circle>();
                ArrayList<String[]> circleStuff;
                circleStuff = ReadFile(file);

                for (String[] circleInfo : circleStuff)
                {
                    /*time,x,y,circleSize,color,num*/
                    int a = Integer.parseInt(circleInfo[1]);
                    int b = Integer.parseInt(circleInfo[2]);


                    Circle circle = new Circle(
                            Double.parseDouble(circleInfo[0]),
                            a,
                            b,
                            (int)(Gdx.graphics.getHeight() * 0.10f),
                            new Color(
                                    Float.parseFloat(circleInfo[3]),
                                    Float.parseFloat(circleInfo[4]),
                                    Float.parseFloat(circleInfo[5]),
                                    1
                            ),
                            circleInfo[6],
                            batch);
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

                PercussionDetection();

                ThreadFunction();

            }
        } catch (Exception e)
        {
            Log.d("E", e.toString());
        }

    }

    private void MusicDirectory(String dir)
    {
        this.directory = dir;
    }

    public void PercussionDetection()
    {
        PercussionOnsetDetector a = null;

        a = new PercussionOnsetDetector(dispatcher.getFormat().getSampleRate(), bufferSize,
                new OnsetHandler() {
                    @Override
                    public void handleOnset(double v, double v1) {
                        ArrayList<Circle> toAdd = generator.GenerateCombo(v);
                        for (Circle c : toAdd)
                        {
                            list.add(c);
                        }
                    }
                },
                28, -1.5);
        dispatcher.addAudioProcessor(a);
    }

    public void ThreadFunction()
    {
        ThreadB thread = new ThreadB();
        thread.start();

        synchronized (thread)
        {
            try{
                Log.d("Thread", "waiting");
                thread.wait();

            } catch(InterruptedException e){
                e.printStackTrace();
            }

            WriteFile(file);
        }
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
            br = new BufferedReader(r);
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
