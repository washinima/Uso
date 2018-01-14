package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Marcio Rocha on 14/01/2018.
 */

public class InformationText extends ApplicationAdapter {

    SpriteBatch batch;
    BitmapFont font;


    @Override
    public void create () {
        batch = new SpriteBatch();
        font = new BitmapFont();
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        font.draw(batch,"Hello World",200,200);
        batch.end();
    }
}