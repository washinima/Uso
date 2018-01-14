package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Random;

public class MyGdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    SpriteBatch fontBatch;
    boolean playingMusic;
    int stateManager;
    Music music;
    Random generator;
    ArrayList<Circle> map;
    private BitmapFont font;
    private CharSequence strScore;
    private CharSequence strCombo;
    int score;
    int combo;

    float diff = 1f;

    private final MusicInterface m;
    private String source = "test.mp3";

    private MenuScreen menu;
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
        fontBatch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().scale(10);
        strScore = Integer.toString(score);
        strCombo = Integer.toString(combo);

        WIDTH = Gdx.graphics.getWidth();
        HEIGHT = Gdx.graphics.getHeight();

        menu = new MenuScreen(WIDTH,HEIGHT);
        menu.create();

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
                score = 0;
                combo = 0;
                stateManager = 2;
                break;
            case 2:
                Gdx.gl.glClearColor(0.1f, 0.2f, 0.7f, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

                strScore = Integer.toString(score);
                strCombo = Integer.toString(combo);

                batch.begin();
                ActualGame();
                batch.end();
                break;
            case 3:
                menu.render();
                break;
        }
    }

    public void CreateMap()
    {
        map = m.SendMap();
        /*int last_x = 0, last_y = 0, x, y;

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
        }*/

    }

    public void ActualGame()
    {
        if(!playingMusic)
        {
            music = Gdx.audio.newMusic(Gdx.files.absolute(m.musicPath()));
            music.play();
            playingMusic = true;
        }

        fontBatch.begin();
        font.draw(fontBatch, strScore, 200, 200);
        font.draw(fontBatch, strCombo, 200, 500);
        fontBatch.end();

        for(int i = map.size() - 1; i >= 0; i--) {
            Circle circle = map.get(i);
            if (compareTime(circle, music.getPosition())) {
                circle.update();
                if (Gdx.input.isTouched(0)) {
                    circle.CheckIfClicked(Gdx.input.getX(0), Gdx.input.getY(0), music.getPosition());
                }
                if (Gdx.input.isTouched(1)) {
                    circle.CheckIfClicked(Gdx.input.getX(1), Gdx.input.getY(1), music.getPosition());
                }
                if (Gdx.input.isTouched(2)) {
                    circle.CheckIfClicked(Gdx.input.getX(2), Gdx.input.getY(2), music.getPosition());
                }
                if (circle.wasClicked) {
                    combo++;
                    if (circle.getScore() == 0)
                        combo = 0;
                    else
                        score += circle.getScore() * combo;
                }
                circle.draw(batch);
            }

            if (circle.IsActive() && circle.getTime() < music.getPosition() - diff && !circle.wasClicked) {
                map.remove(i);
                circle.SetActive(false);
                circle.setScore(0);
                combo = 0;
            }
        }
    }

    private boolean compareTime(Circle circle, float musicTime)
    {
        if(circle.getTime() >= musicTime - diff && circle.getTime() < musicTime && circle.IsActive())
        {
            return true;
        }
        return false;
    }

    @Override
    public void dispose () {
        batch.dispose();
        //music.dispose();
    }
}