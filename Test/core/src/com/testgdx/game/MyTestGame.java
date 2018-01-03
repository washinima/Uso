package com.testgdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;



public class MyTestGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

	int stateManager;

	private final MusicInterface m;


	public MyTestGame(MusicInterface musicInterface)
    {
        this.m = musicInterface;
        this.stateManager = 0;
    }

	@Override
    public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
	}

	@Override
	public void render () {

		switch (stateManager)
		{
			//0 Menu | 1 Escolher Musica | 2 Jogo | 3 Score Screen
			case 0:
				break;
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
		}

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
