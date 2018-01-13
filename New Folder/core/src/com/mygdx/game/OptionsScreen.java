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
 * Created by Marcio Rocha on 13/01/2018.
 */

public class OptionsScreen extends Game {

    private Stage stage;
    private MusicList musicList;

    private int width;
    private int height;
    private boolean goBack;

    private Image background, inactive, backBtn;

    public OptionsScreen(int width, int height) {
        this.width = width;
        this.height = height;
        goBack = false;
    }

    @Override
    public void create(){

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        background = new Image(new Texture("options_background.png"));
        background.setPosition((width/2)- (background.getWidth()/2),(height/2) - (background.getHeight()/2));
        inactive = new Image(new Texture("inactive_background.png"));
        inactive.setSize(width,height);
        inactive.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                goBack = true;
            }
        });
        backBtn = new Image(new Texture("back_button.png"));
        backBtn.setPosition((width/2)- (backBtn.getWidth()/2),(height/2) - (backBtn.getHeight()/2));
        backBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                goBack = true;
            }
        });

        stage.addActor(inactive);
        stage.addActor(background);
        stage.addActor(backBtn);
    }

    public void render(){
        stage.act();
        stage.draw();
    }

    public boolean isGoBack(){
        if( goBack== false) return false;
        else    return true;
    }

    @Override
    public void dispose () {
        stage.dispose();
    }
}