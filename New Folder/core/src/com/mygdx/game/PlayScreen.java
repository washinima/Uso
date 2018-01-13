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
    private ActualGame game;

    private int state;

    private int width;
    private int height;

    public PlayScreen(int width, int height) {
        this.width = width;
        this.height = height;
        musicList = new MusicList(width, height);
        game = new ActualGame(width, height);

    }

    @Override
    public void create(){

        state = 0;

        musicList.create();

        stage = musicList.stage;
    }

    public void render()
    {
        switch (state)
        {
            case 0:
                stage = musicList.stage;
                break;
            case 1:
                stage = game.stage;
                break;
        }
    }

    @Override
    public void dispose () {
        stage.dispose();
    }
}
