package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


/**
 * Created by Marcio Rocha on 11/01/2018.
 */

public class MenuState extends Game{

    private PlayScreen playScreen;
    private Stage stage;
    private Texture background;
    private Texture logo, playTexture, optionsBtn, exitBtn;
    private Image playBtn;
    private int width, height, btnSelected;

    public MenuState(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void create() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        playScreen = new PlayScreen(width,height);
        playScreen.create();
        //optionsScreen = new

        btnSelected = -1;

        background = new Texture("menu_background.jpg");
        logo = new Texture("Uso_logo.png");
        playTexture = new Texture("play_button.png");
        optionsBtn = new Texture("Options_Button.png");
        exitBtn = new Texture("Exit_Button.png");

        playBtn = new Image(playTexture);
        playBtn.setSize(playBtn.getWidth()*2,playBtn.getHeight()*2);
        playBtn.setPosition((width/3) + (logo.getWidth()/2) - (playTexture.getWidth()/2),(height/2) + (logo.getHeight()/2) - (playTexture.getHeight()/2));
        playBtn.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                btnSelected = 0;
            }
        });
        stage.addActor(playBtn);
    }

    public void render(SpriteBatch spriteBatch){
        spriteBatch.draw(background, 0,0,width,height);
        spriteBatch.draw(logo,       (width/3) - (logo.getWidth()/2),(height/2) - (logo.getHeight()/2));
        spriteBatch.draw(optionsBtn, (width/3) + (logo.getWidth()/2) - (optionsBtn.getWidth()/2),(height/2) - (optionsBtn.getHeight()/2),optionsBtn.getWidth()*2,optionsBtn.getHeight()*2);
        spriteBatch.draw(exitBtn,    (width/3) + (logo.getWidth()/2) - (exitBtn.getWidth()/2),(height/2) - (logo.getHeight()/2) - (exitBtn.getHeight()/2),exitBtn.getWidth()*2,exitBtn.getHeight()*2);

        stage.act();
        stage.draw();

        switch (btnSelected){
            case 0:
                playScreen.render(spriteBatch);
                break;
            case 1:
                break;
            case 2:
                break;
        }
    }
}
