package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Marcio Rocha on 13/01/2018.
 */

public class PlayScreen extends Game{

    private int width;
    private int height;

    private Texture background;

    public PlayScreen(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void create() {
        background = new Texture("menulist_background.png");
    }

    public void render(SpriteBatch spriteBatch){
        spriteBatch.draw(background,width,height);
    }
}
