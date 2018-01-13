package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


/**
 * Created by Marcio Rocha on 11/01/2018.
 */

public class MenuScreen extends Game{

    private PlayScreen playScreen;
    private OptionsScreen optionsScreen;
    private Stage stage;
    private Image background, logo, playBtn, optionsBtn, exitBtn;
    private int width, height, btnSelected;

    public MenuScreen(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void create() {
        stage = new Stage(new ScreenViewport());

        playScreen = new PlayScreen(width,height);
        playScreen.create();
        optionsScreen = new OptionsScreen(width,height);
        optionsScreen.create();

        btnSelected = -1;

        background = new Image(new Texture("menu_background.jpg"));
        background.setSize(width,height);

        logo = new Image(new Texture("logo.png"));
        logo.setSize(logo.getWidth()*2,logo.getHeight()*2);
        logo.setPosition((width/3) - (logo.getWidth()/2),(height/2) - (logo.getHeight()/2));

        playBtn = new Image(new Texture("play_button.png"));
        playBtn.setSize(playBtn.getWidth()*2,playBtn.getHeight()*2);
        playBtn.setPosition(logo.getX() + logo.getWidth()- 80, logo.getHeight() - logo.getHeight()/4);
        playBtn.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                btnSelected = 0;
            }
        });

        optionsBtn = new Image(new Texture("Options_Button.png"));
        optionsBtn.setSize(optionsBtn.getWidth()*2,optionsBtn.getHeight()*2);
        optionsBtn.setPosition(logo.getX() + logo.getWidth() - 10, logo.getHeight()/2 +40);
        optionsBtn.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                btnSelected = 1;
            }
        });

        exitBtn = new Image(new Texture("Exit_Button.png"));
        exitBtn.setSize(exitBtn.getWidth()*2,exitBtn.getHeight()*2);
        exitBtn.setPosition(logo.getX() + logo.getWidth() -80,logo.getY() + logo.getHeight()/5);
        exitBtn.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                btnSelected = 2;
            }
        });

        stage.addActor(background);
        stage.addActor(playBtn);
        stage.addActor(optionsBtn);
        stage.addActor(exitBtn);
        stage.addActor(logo);
        Gdx.input.setInputProcessor(stage);
    }

    public void render(){
        stage.act();
        stage.draw();

        switch (btnSelected){
            case 0:
                playScreen.render();
                break;
            case 1:
                optionsScreen.render();
                if( optionsScreen.isGoBack() == true)
                   btnSelected = -1;
                break;
            case 2:Gdx.app.exit();
                break;
        }
    }

    @Override
    public void dispose () {
        stage.dispose();
    }
}
