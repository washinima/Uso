package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;


/**
 * Created by Marcio Rocha on 11/01/2018.
 */

public class MenuState {

    private ImageButton playBttn;
    private Texture background;
    private Texture logo;
    private Texture playBtn;
    private Texture optionsBtn;
    private Texture exitBtn;
    private int width;
    private int height;

    public MenuState(int width, int height)
    {
        this.width = width;
        this.height = height;

        background = new Texture("menu_background.jpg");
        logo = new Texture("Uso_logo.png");
        playBtn = new Texture("play_button.png");
        optionsBtn = new Texture("Options_Button.png");
        exitBtn = new Texture("Exit_Button.png");

        playBttn = new ImageButton(playBtn,skin);
    }

    public void handleInput(){
        if()
    }

    public void render(SpriteBatch spriteBatch){
        spriteBatch.draw(background, 0,0,width,height);
        spriteBatch.draw(logo,       (width/3) - (logo.getWidth()/2),(height/2) - (logo.getHeight()/2));
        spriteBatch.draw(playBtn,    (width/3) + (logo.getWidth()/2) - (playBtn.getWidth()/2),(height/2) + (logo.getHeight()/2) - (playBtn.getHeight()/2), playBtn.getWidth()*2,playBtn.getHeight()*2);
        spriteBatch.draw(optionsBtn, (width/3) + (logo.getWidth()/2) - (optionsBtn.getWidth()/2),(height/2) - (optionsBtn.getHeight()/2),optionsBtn.getWidth()*2,optionsBtn.getHeight()*2);
        spriteBatch.draw(exitBtn,    (width/3) + (logo.getWidth()/2) - (exitBtn.getWidth()/2),(height/2) - (logo.getHeight()/2) - (exitBtn.getHeight()/2),exitBtn.getWidth()*2,exitBtn.getHeight()*2);
    }
}
