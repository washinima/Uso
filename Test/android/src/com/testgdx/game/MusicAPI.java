package com.testgdx.game;

/**
 * Created by Whisp on 02/01/2018.
 */


import android.content.Context;
import android.net.rtp.AudioStream;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import android.media.*;

import be.tarsos.dsp.*;
import be.tarsos.dsp.io.PipedAudioStream;
import be.tarsos.dsp.io.TarsosDSPAudioFormat;
import be.tarsos.dsp.io.TarsosDSPAudioInputStream;
import be.tarsos.dsp.io.UniversalAudioInputStream;
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
    InputStream inStream;

    ArrayList<Hit> list;

    public void Setup(Context context, String dir)
    {
        new AndroidFFMPEGLocator(context);
        MusicDirectory(dir);
    }

    public void Start()
    {
        list = new ArrayList<Hit>(100);

        int sampleRate = 440100, bufferSize = 1024, bufferOverlap = 0;

        PipedAudioStream stream = new PipedAudioStream(directory);

        TarsosDSPAudioInputStream audioStream = stream.getMonoStream(44100, 0);

        dispatcher = new AudioDispatcher(audioStream, 5000, 0);



       /* try {
            inStream = new FileInputStream(directory);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        dispatcher = new AudioDispatcher(
                new UniversalAudioInputStream(
                        inStream, new TarsosDSPAudioFormat(sampleRate, bufferSize, 1, true, true)
                )
                , bufferSize, bufferOverlap);*/

    }

    public void Run()
    {
        new Thread(dispatcher).run();
    }

    public void Test()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                File externalStorage = Environment.getExternalStorageDirectory();
                File mp3 = new File(externalStorage.getAbsolutePath() , "/test.mp3");
                AudioDispatcher adp;
                adp = AudioDispatcherFactory.fromPipe(mp3.getAbsolutePath(),44100,5000,2500);
                adp.addAudioProcessor(new AndroidAudioPlayer(adp.getFormat(),5000, AudioManager.STREAM_MUSIC));
                adp.run();
            }
        }).start();
    }

    private void MusicDirectory(String dir)
    {
        this.directory = dir;
    }

    public void PercussionDetection()
    {
        PercussionOnsetDetector a = new PercussionOnsetDetector(dispatcher.getFormat().getSampleRate(), 1024,
                new OnsetHandler()
                {
                    @Override
                    public void handleOnset(double v, double v1) {
                        Hit a = new Hit( (float)v, 0);

                        list.add(a);
                    }
                },
                8, 20);
        dispatcher.addAudioProcessor(a);
    }


    public int MakeMap()
    {
        return 0;
    }
}