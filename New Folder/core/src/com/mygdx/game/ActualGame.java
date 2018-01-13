package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by Whisp on 13/01/2018.
 */

public class ActualGame
{
    private Image background;
    public Stage stage;

    private int width, height;
    public ActualGame(int width, int height)
    {
        this.width = width;
        this.height = height;
    }

    public void create()
    {
        stage = new Stage();

        background = new Image(new Texture("menulist_background.png"));
        background.setSize(width,height);

        stage.addActor(background);
    }

    public void render()
    {

    }
}
