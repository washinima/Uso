package com.mygdx.game;

/**
 * Created by Whisp on 08/01/2018.
 */

import android.content.Context;
import android.graphics.Rect;
import android.os.Environment;

import java.io.File;
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
    int sampleRate = 22050, bufferSize = 1024, bufferOverlap = 0;
    FlowGenerator generator;

    ArrayList<Circle> list;

    public void Setup(Context context, String dir)
    {
        new AndroidFFMPEGLocator(context);
        MusicDirectory(dir);
    }

    public void Start()
    {
        list = new ArrayList<Circle>();
        generator = new FlowGenerator((int)(Gdx.graphics.getHeight() * 0.35f), (int)(Gdx.graphics.getHeight() * 0.10f), 1);

        PipedAudioStream stream = new PipedAudioStream(directory);

        TarsosDSPAudioInputStream audioStream = stream.getMonoStream(sampleRate, bufferOverlap);

        dispatcher = new AudioDispatcher(audioStream, bufferSize, bufferOverlap);

        PercussionDetection();


    }

    private void MusicDirectory(String dir)
    {
        this.directory = dir;
    }

    public void PercussionDetection()
    {
        PercussionOnsetDetector a = new PercussionOnsetDetector(dispatcher.getFormat().getSampleRate(), bufferSize,
                new OnsetHandler()
                {
                    @Override
                    public void handleOnset(double v, double v1) {
                        ArrayList<Circle> toAdd = generator.GenerateCombo(v);
                        for (Circle circle : toAdd)
                        {
                            list.add(circle);
                        }
                    }
                },
                50, 4);
        dispatcher.addAudioProcessor(a);

        thread = new Thread(dispatcher);
        thread.run();
    }
}
