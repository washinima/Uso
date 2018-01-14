package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Whisp on 13/01/2018.
 */

public class ActualGame
{
    private Image background;
    public Stage stage;

    boolean playingMusic;
    Music music;
    SpriteBatch fontBatch;
    ArrayList<Circle> map;

    private BitmapFont font;
    private CharSequence strScore;
    private CharSequence strCombo;
    int score;
    int combo;

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
        stage = new Stage();

        background = new Image(new Texture("menulist_background.png"));
        background.setSize(width,height);

        stage.addActor(background);

        map = musicInterface.SendMap();
        source = musicInterface.musicPath();

        fontBatch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().scale(10);
        strScore = Integer.toString(score);
        strCombo = Integer.toString(combo);
    }

    public void render(SpriteBatch batch)
    {
        strScore = Integer.toString(score);
        strCombo = Integer.toString(combo);

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
                    circle.CheckIfClicked(Gdx.input.getX(0), Gdx.input.getY(0), music.getPosition(), diff);
                }
                if (Gdx.input.isTouched(1)) {
                    circle.CheckIfClicked(Gdx.input.getX(1), Gdx.input.getY(1), music.getPosition(), diff);
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
        if(circle.getTime() >= musicTime - diff && circle.getTime() < musicTime + diff / 2 && circle.IsActive())
        {
            return true;
        }
        return false;
    }

}
