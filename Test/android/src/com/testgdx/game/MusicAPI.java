package com.testgdx.game;

/**
 * Created by Whisp on 02/01/2018.
 */


import android.widget.TextView;

import java.io.File;

import be.tarsos.dsp.*;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.onsets.OnsetHandler;
import be.tarsos.dsp.onsets.PercussionOnsetDetector;


public class MusicAPI
{
    private class Hit
    {
        float time;
        int type;
    }

    AudioDispatcher dispatcher;
    String directory;

    Hit[] a;

    public void Start()
    {

        dispatcher = AudioDispatcherFactory.fromPipe(directory, 1024,0,0);
        a = new Hit[]
    }

    public void MusicDirectory(String dir)
    {
        this.directory = dir;
    }

    public void PercussionDetection()
    {
        OnsetHandler onsetHandler = new OnsetHandler() {
            @Override
            public void handleOnset(double v, double v1) {

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