package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

/**
 * Created by Marcio Rocha on 13/01/2018.
 */

public class MusicList extends ApplicationAdapter {
    public Stage stage;
    private Table container;

    private int width, height;

    private Image background;

    public MusicList(int width, int height)
    {
        this.width = width;
        this.height = height;
    }

    @Override
    public void create () {
        stage = new Stage();
        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));


        container = new Table();

        container.setFillParent(true);

        Table table = new Table();

        final ScrollPane scroll = new ScrollPane(table, skin);
        scroll.setScrollingDisabled(true,false); // Move na vertical

        table.pad(10).defaults().expandX().space(4);
        for (int i = 0; i < 100; i++) { // musicCount
            table.row();

            Label label=new Label(i + " Skins gimme cancer", skin);
            label.setAlignment(Align.center);
            label.setWrap(true);
            table.add(label).width(Gdx.graphics.getWidth());
        }
        container.add(scroll).expand().fill();


        stage.addActor(container);
    }

    @Override
    public void resize (int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose () {
        stage.dispose();
    }
}
