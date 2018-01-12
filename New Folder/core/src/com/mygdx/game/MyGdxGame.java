package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Random;

import javax.xml.soap.Text;

public class MyGdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    boolean playingMusic;
    int stateManager;
    Music music;
    Random generator;
    ArrayList<Hittable> map;

    float diff = 1f;


    private final MusicInterface m;
    private String source = "test.mp3";

    private MenuState menu;
    public int WIDTH, HEIGHT;

    public MyGdxGame(MusicInterface musicInterface)
    {
        generator = new Random();
        this.m = musicInterface;
        this.music = null;
        this.stateManager = 0;

    }

    @Override
    public void create () {
        batch = new SpriteBatch();

        /*WIDTH = Gdx.graphics.getWidth();
        HEIGHT = Gdx.graphics.getHeight();

        menu = new MenuState(WIDTH,HEIGHT);*/

        m.showPicker();
    }

    @Override
    public void render () {

        switch (stateManager)
        {
            //0 Menu | 1 Escolher Musica | 2 Jogo | 3 Score Screen
            case 0:
                Gdx.gl.glClearColor(1, 0, 0, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                if(m.isReady())
                {
                    m.SetupRun();
                    CreateMap();
                    stateManager = 1;
                }
                break;
            case 1:
                Gdx.gl.glClearColor(0, 0, 1, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

                batch.begin();
                ActualGame();
                batch.end();
                break;
            case 2:
                break;
            case 3:
                batch.begin();
                menu.render(batch);
                batch.end();
                break;
        }

    }

    public void CreateMap()
    {
        int last_x = 0, last_y = 0, x, y;
        map = m.SendMap();

        for(int i = 0; i <= map.size() - 1; i++)
        {
            do{
                x = generator.nextInt(
                        Gdx.graphics.getWidth()- map.get(i).getSize()
                );
                y = generator.nextInt(
                        Gdx.graphics.getHeight()- map.get(i).getSize()
                );
            }while(
                    (x + map.get(i).getSize() == last_x && x - map.get(i).getSize() == last_x) ||
                            (y + map.get(i).getSize() == last_y && y - map.get(i).getSize() == last_y)
                    );

            map.get(i).setPosition(x, y);

            last_x = x;
            last_y = y;
        }

    }

    public void ActualGame()
    {
        if(!playingMusic)
        {
            music = Gdx.audio.newMusic(Gdx.files.absolute(m.musicPath()));
            music.play();
            playingMusic = true;
        }

        for(int i = 0; i <= map.size() - 1; i++)
        {

            float pos = music.getPosition();
            if(
                    compareTime(map.get(i).getTime(), pos)
                    )
            {
                map.get(i).draw(batch);
            }
        }

        /*for(int i = map.size() - 1; i >= 0; i--)
        {
            if( map.get(i).getTime() > music.getPosition())
            {
                map.remove(i);
            }
        }*/

    }

    private boolean compareTime(float hitTime, float musicTime)
    {
        if( hitTime >= musicTime - diff && hitTime < musicTime)
        {
            return true;
        }

        return false;
    }

    @Override
    public void dispose () {
        batch.dispose();
        music.dispose();
    }
}