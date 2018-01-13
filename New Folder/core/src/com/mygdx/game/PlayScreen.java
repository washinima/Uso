package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Created by Marcio Rocha on 13/01/2018.
 */

public class PlayScreen extends Game{

    public Stage stage;
    private MusicList musicList;

    private int width;
    private int height;

    private Image background;

    public PlayScreen(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void create(){

        stage = new Stage(new ScreenViewport());
        background = new Image(new Texture("menulist_background.png"));
        background.setSize(width,height);

        musicList = new MusicList();
        musicList.create();
        //musicList.resize(width,200);

        stage.addActor(background);
    }

    @Override
    public void dispose () {
        stage.dispose();
    }
}
