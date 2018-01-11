package com.testgdx.game;

import android.os.Bundle;
import android.media.*;
import android.util.Log;

import java.io.*;
import java.util.ArrayList;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.audio.AudioRecorder;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.testgdx.game.MyTestGame;

import be.tarsos.dsp.io.android.AndroidFFMPEGLocator;


public class AndroidLauncher extends AndroidApplication {

    public class AndroidMusic implements MusicInterface {
        private final MusicAPI api;

        public AndroidMusic()
        {
            api = new MusicAPI();
        }

        public int MakeMap()
        {
            return api.MakeMap();
        }

        public void Setup(String source) {
            api.Setup(getApplicationContext(), source);
            api.Start();
        }

        public void Start()
        {
            api.Run();
        }


        public boolean isStopped()
        {
            return api.dispatcher.isStopped();
        }

        public void Log()
        {
            float a = api.dispatcher.secondsProcessed();
            Log.d("List",api.list.toString());
            Log.d("Time", Float.toString(a));
        }
    }

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		AndroidMusic androidMusic = new AndroidMusic();

		initialize(new MyTestGame(androidMusic), config);

	}
}
