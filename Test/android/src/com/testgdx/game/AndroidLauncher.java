package com.testgdx.game;

import android.net.rtp.AudioStream;
import android.os.Bundle;
import java.io.File;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.testgdx.game.MyTestGame;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new MyTestGame(), config);

	}


	private int getSixteenBitSample(int high, int low) {
		return (high << 8) + (low & 0x00ff);
	}

	public void AudioTest(String pathname)
	{
		File file = new File(pathname);
		AudioInputStream stream = AudioSystem.getAudioInputStream(file);

		int frameLenght = (int) stream.getFrameLength();
		int frameSize = (int) stream.getFormat().getFrameSize();
		
		byte[] bytes = new byte[frameLenght * frameSize];


		int result = 0;
		try{
			result = stream.read(bytes);
		} catch ( Exception e) {
			e.printStackTrace();
		}


		int channels = stream.getFormat().getChannels();
		
		int[][] wave = new int[channels][frameLenght];

		int sampleIndex = 0;

		for(int i = 0; i < bytes.length;) 
		{
			for(int j = 0; j < channels; j++)
			{
				int low = (int) bytes[i];
				i++;
				int high = (int) bytes[i];
				i++;
				int sample = getSixteenBitSample(high, low);
				wave[j][sampleIndex] = sample;
			}
			sampleIndex++;
		}

	}
}
