package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Created by Marcio on 13/01/2018.
 */

public class OptionsScreen extends Game {

    public Stage stage;
    private MusicList musicList;

    private int width;
    private int height;
    private boolean leave;

    private Image background, inactive, backBtn;

    public OptionsScreen(int width, int height) {
        this.width = width;
        this.height = height;
        leave = false;
    }

    @Override
    public void create(){

        stage = new Stage(new ScreenViewport());

        background = new Image(new Texture("options_background.png"));
        background.setPosition((width/2)- (background.getWidth()/2),(height/2) - (background.getHeight()/2));
        inactive = new Image(new Texture("inactive_background.png"));
        inactive.setSize(width,height);
        inactive.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                leave = true;
            }
        });
        backBtn = new Image(new Texture("back_button.png"));
        backBtn.setPosition((width/2)- (backBtn.getWidth()/2),(height/2) - (background.getHeight()/4)- (backBtn.getHeight()/2));
        backBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                leave = true;
            }
        });

        stage.addActor(inactive);
        stage.addActor(background);
        stage.addActor(backBtn);
    }

    public Stage getStage()
    {
        return stage;
    }

    public boolean getLeave(){ return leave; }
    public void setLeave(boolean value){ leave = value; }

    @Override
    public void dispose () {
        stage.dispose();
    }
}