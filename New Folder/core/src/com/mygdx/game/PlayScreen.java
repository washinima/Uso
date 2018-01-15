package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Created by Marcio on 13/01/2018.
 */

public class PlayScreen extends Game{

    public Stage stage, aux_stage;
    private ActualGame game;
    private boolean isSet;

    private SpriteBatch fontBatch;
    private BitmapFont font;
    private String strScore;

    private Image background, playBtn, chooseBtn, backBtn;

    private int state;
    private int width;
    private int height;
    private boolean leave;

    MusicInterface musicInterface;
    FirebaseInterface firebaseInterface;

    public PlayScreen(int width, int height, MusicInterface musicInterface, FirebaseInterface firebaseInterface) {
        this.width = width;
        this.height = height;
        this.musicInterface = musicInterface;
        this.firebaseInterface = firebaseInterface;

        leave = false;
    }

    @Override
    public void create(){
        isSet = false;
        state = 0;

        stage = new Stage(new ScreenViewport());
        background = new Image(new Texture("menulist_background.png"));
        background.setSize(width,height);

        playBtn = new Image(new Texture("noplay.png"));
        playBtn.setSize(playBtn.getWidth()/2,playBtn.getHeight()/2);
        playBtn.setPosition(80, height - (playBtn.getHeight()*2) - 160);
        playBtn.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(musicInterface.isReady()){
                        state = 1;
                        musicInterface.SetupRun();
                        game = new ActualGame(width, height, musicInterface);
                        game.create();
                    }
                    //else{ Imprimir texto de ajuda }

                }
        });

        chooseBtn = new Image(new Texture("choose.png"));
        chooseBtn.setSize(playBtn.getWidth(),playBtn.getHeight());
        chooseBtn.setPosition(80, (height - playBtn.getHeight())-80);
        chooseBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isSet = false;
                musicInterface.showPicker();
            }
        });

        backBtn = new Image(new Texture("back_button.png"));
        backBtn.setSize(backBtn.getWidth(),backBtn.getHeight());
        backBtn.setPosition(20,20);
        backBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                leave = true;
            }
        });

        fontBatch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().scale(3);
        font.setColor(Color.BLACK);
        strScore = Integer.toString(0);


        stage.addActor(background);
        stage.addActor(chooseBtn);
        stage.addActor(playBtn);
        stage.addActor(backBtn);

        aux_stage = stage;
    }

    public Stage getStage()
    {
        return aux_stage;
    }

    public void render(SpriteBatch batch)
    {
        switch (state)
        {
            case 0:
                aux_stage = stage;
                if(musicInterface.isReady() && !isSet){
                    playBtn.setDrawable(new SpriteDrawable(new Sprite(new Texture("play.png"))));
                    isSet = true;
                }

                fontBatch.begin();
                font.draw(fontBatch,"Best Score: " + strScore,width - width/3,height - height/3);
                fontBatch.end();

                break;
            case 1:
                game.render(batch);
                aux_stage = game.stage;

                if(game.endGame)
                {
                    if(firebaseInterface.isOnline())
                        firebaseInterface.updateScore(game.score);
                }
                break;
        }
    }

    public void UpdateTextScore(int score){
        strScore = Integer.toString(score);
    }

    @Override
    public void dispose () {
        stage.dispose();
    }

    public boolean getLeave(){ return leave; }
    public void setLeave(boolean value){ leave = value; }

}
