package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Whisp on 13/01/2018.
 */

public class ActualGame
{
    private Image background;
    public Stage stage;

    public boolean endGame;

    boolean playingMusic;
    Music music;
    SpriteBatch fontBatch;
    ArrayList<Circle> map;

    private BitmapFont font;
    private BitmapFont circleScoreFont;
    private CharSequence strScore;
    private CharSequence strCombo;
    private CharSequence strCircleScore;
    private int xScore, yScore;
    int score;
    int combo;
    private double timer;
    private boolean stopTimer;

    double diff = 1.0;
    private String source;

    private int width, height;

    MusicInterface musicInterface;


    public ActualGame(int width, int height, MusicInterface musicInterface)
    {
        this.width = width;
        this.height = height;
        this.musicInterface = musicInterface;
    }

    public void create()
    {
        endGame = false;
        stage = new Stage();

        background = new Image(new Texture("menulist_background.png"));
        background.setSize(width,height);

        stage.addActor(background);

        music = null;

        map = musicInterface.SendMap();
        source = musicInterface.musicPath();

        fontBatch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().scale(8);
        circleScoreFont = new BitmapFont();
        circleScoreFont.getData().scale(3);
        strScore = Integer.toString(score);
        strCombo = Integer.toString(combo);
        strCircleScore = "";
        timer = 0.0;
        stopTimer = false;
    }

    public void render(SpriteBatch batch)
    {
        strScore = Integer.toString(score);
        strCombo = Integer.toString(combo) + "x";
        if (!stopTimer)
            timer += Gdx.graphics.getDeltaTime();

        batch.begin();
        Game(batch);
        batch.end();
    }

    public void Game(SpriteBatch batch)
    {
        if(!playingMusic)
        {
            music = Gdx.audio.newMusic(Gdx.files.absolute(musicInterface.musicPath()));
            music.play();
            music.setOnCompletionListener(
                    new Music.OnCompletionListener() {
                        @Override
                        public void onCompletion(Music music) {
                            endGame = true;
                            playingMusic = false;
                        }
                    }
            );
            playingMusic = true;
        }

        fontBatch.begin();
        font.draw(fontBatch, strScore, 70, 150);
        font.draw(fontBatch, strCombo, 70, 300);
        if (timer < 1.5)
        circleScoreFont.draw(fontBatch, strCircleScore, xScore, yScore);
        else {
            stopTimer = true;
        }
        fontBatch.end();

        for(int i = map.size() - 1; i >= 0; i--)
        {
            Circle circle = map.get(i);
            if (compareTime(circle, music.getPosition())) {
                circle.update();
                circle.draw(batch);
            }

            if (circle.wasClicked) {
                combo++;
                if (circle.getScore() == 0)
                    combo = 0;
                else
                    score += circle.getScore() * combo;
                strCircleScore = Integer.toString(circle.getScore());
                xScore = circle.getX();
                yScore = circle.getY();
                stopTimer = false;
                timer = 0.0;
                map.remove(i);
                continue;
            }

            if (circle.IsActive() && circle.getTime() < music.getPosition() - diff && !circle.wasClicked) {
                circle.SetActive(false);
                circle.setScore(0);
                strCircleScore = "0";
                xScore = circle.getX();
                yScore = circle.getY();
                stopTimer = false;
                timer = 0.0;
                map.remove(i);
                combo = 0;
                continue;
            }
        }

        for (int x = 0; x < map.size(); x++)
        {
            Circle c = map.get(x);
            if (compareTime(c, music.getPosition())) {
                if (Gdx.input.isTouched(0) && Gdx.input.justTouched()) {
                    if(c.CheckIfClicked(Gdx.input.getX(0), Gdx.input.getY(0), music.getPosition(), diff))
                        break;
                }
                if (Gdx.input.isTouched(1) && Gdx.input.justTouched()) {
                    if(c.CheckIfClicked(Gdx.input.getX(1), Gdx.input.getY(1), music.getPosition(), diff))
                        break;
                }
            }
        }
    }

    private boolean compareTime(Circle circle, float musicTime)
    {
        if(circle.getTime() >= musicTime - diff && circle.getTime() < musicTime + diff / 3)
            return true;
        return false;
    }
}
