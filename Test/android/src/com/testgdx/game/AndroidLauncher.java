package com.testgdx.game;

import android.os.Bundle;
import android.media.*;
import java.io.*;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.audio.AudioRecorder;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.testgdx.game.MyTestGame;

import be.tarsos.dsp.*;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new MyTestGame(), config);

	}
}
