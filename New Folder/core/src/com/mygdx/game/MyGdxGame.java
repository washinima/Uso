package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    Music music;
    private final MusicInterface m;

    private MenuScreen menu;
    public int WIDTH, HEIGHT;

    public MyGdxGame(MusicInterface musicInterface)
    {
        this.m = musicInterface;
        this.music = null;
    }

    @Override
    public void create () {
        batch = new SpriteBatch();

        WIDTH = Gdx.graphics.getWidth();
        HEIGHT = Gdx.graphics.getHeight();

        menu = new MenuScreen(WIDTH,HEIGHT, m);
        menu.create();
    }

    @Override
    public void render () {
        menu.render(batch);
    }

    @Override
    public void dispose () {
        batch.dispose();
        music.dispose();
    }
}