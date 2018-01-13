package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


/**
 * Created by Marcio Rocha on 11/01/2018.
 */

public class MenuScreen extends Game{

    private PlayScreen playScreen;
    private OptionsScreen optionsScreen;
    private Stage stage, aux_stage;
    private Image background, logo, playBtn, optionsBtn, exitBtn, userBtn;
    private int width, height, btnSelected;
    private boolean userOnline;

    MusicInterface music;

    public MenuScreen(int width, int height, MusicInterface musicInterface) {
        this.width = width;
        this.height = height;

        this.music = musicInterface;
    }

    @Override
    public void create() {
        stage = new Stage(new ScreenViewport());

        playScreen = new PlayScreen(width,height, music);
        playScreen.create();
        optionsScreen = new OptionsScreen(width,height);
        optionsScreen.create();

        btnSelected = -1;
        userOnline = false;

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

        userBtn = new Image(new Texture("user.png"));
        userBtn.setSize(userBtn.getWidth()/3,userBtn.getHeight()/3);
        userBtn.setPosition(width - userBtn.getWidth(),height - userBtn.getHeight());
        userBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(userOnline == false) {
                    // registar ou login
                    userBtn.setDrawable(new SpriteDrawable(new Sprite(new Texture("user_on.png"))));
                    userOnline= true;
                }
                else {
                    userBtn.setDrawable(new SpriteDrawable(new Sprite(new Texture("user.png"))));
                    userOnline = false;
                }
            }
        });

        stage.addActor(background);
        stage.addActor(playBtn);
        stage.addActor(optionsBtn);
        stage.addActor(exitBtn);
        stage.addActor(logo);
        stage.addActor(userBtn);

        aux_stage = stage;
    }

    public void render(SpriteBatch batch){
        Gdx.gl.glClearColor(0, 0, 0, 1);

        switch (btnSelected){
            case -1:
                aux_stage = stage;
                break;
            case 0:
                //playScreen.render(batch);
                aux_stage = playScreen.getStage();
                break;
            case 1:
                aux_stage = optionsScreen.getStage();
                if(optionsScreen.getLeave())
                {
                    btnSelected = -1;
                    optionsScreen.setLeave(false);
                }
                break;
            case 2:
                Gdx.app.exit();
                break;
        }
        Gdx.input.setInputProcessor(aux_stage);
        aux_stage.act();
        aux_stage.draw();
    }

    @Override
    public void dispose () {
        stage.dispose();
    }
}
