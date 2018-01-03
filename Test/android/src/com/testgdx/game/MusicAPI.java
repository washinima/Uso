package com.testgdx.game;

/**
 * Created by Whisp on 02/01/2018.
 */


import android.content.Context;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.FileHandler;

import be.tarsos.dsp.*;
import be.tarsos.dsp.io.android.AndroidFFMPEGLocator;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.onsets.OnsetHandler;
import be.tarsos.dsp.onsets.PercussionOnsetDetector;


public class MusicAPI
{
    private class Hit
    {
        public Hit(float time, int type){
            this.time = time;
            this.type = type;
        }

        float time;
        int type;
    }

    AudioDispatcher dispatcher;
    String directory;

    ArrayList<Hit> list;

    public void Setup(Context context, String dir)
    {
        new AndroidFFMPEGLocator(context);
        MusicDirectory(dir);
    }

    public void Start()
    {
        list = new ArrayList<Hit>(100);
        dispatcher = AudioDispatcherFactory.fromPipe(directory, 1024,0,0);

        PercussionDetection();
    }

    private void MusicDirectory(String dir)
    {
        this.directory = dir;
    }

    public void PercussionDetection()
    {
        OnsetHandler onsetHandler = new OnsetHandler() {
            @Override
            public void handleOnset(double v, double v1) {
                Hit a = new Hit( (float)v, 0);

                list.add(a);
            }
        };

        PercussionOnsetDetector a = new PercussionOnsetDetector(2250, 1024, 0 ,onsetHandler);
        dispatcher.addAudioProcessor(a);

        new Thread(dispatcher).start();
    }


    public int MakeMap()
    {
        return 0;
    }
}