package com.testgdx.game;

import android.os.Bundle;
import android.media.*;
import java.io.*;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.audio.AudioRecorder;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.testgdx.game.MyTestGame;


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

        public void StartDirectory(String source) {
            api.Setup(getApplicationContext(), source);
            api.Start();
        }

    }

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		AndroidMusic a = new AndroidMusic();

		initialize(new MyTestGame(a), config);

	}
}
