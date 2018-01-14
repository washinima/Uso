package com.mygdx.game;

import com.badlogic.gdx.Game;
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
 * Created by Marcio Rocha on 13/01/2018.
 */

public class PlayScreen extends Game{

    public Stage stage, aux_stage;
    private ActualGame game;

    private Image background, playBtn, chooseBtn;

    private MusicInterface musicInterface;

    private int state;

    private int width;
    private int height;

    public PlayScreen(int width, int height, MusicInterface musicInterface) {
        this.width = width;
        this.height = height;

        game = new ActualGame(width, height);

        this.musicInterface = musicInterface;

    }

    @Override
    public void create(){

        state = 0;

        stage = new Stage(new ScreenViewport());
        background = new Image(new Texture("menulist_background.png"));
        background.setSize(width,height);

        ////Cria os butoes e a informaçao que quiseres neste stage Marcio
        playBtn = new Image(new Texture("play.png"));
        playBtn.setSize(playBtn.getWidth()/3,playBtn.getHeight()/3);
        playBtn.setPosition(width - playBtn.getWidth(),height - playBtn.getHeight());
        playBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });

        chooseBtn = new Image(new Texture("play.png"));
        chooseBtn.setSize(chooseBtn.getWidth()/3,chooseBtn.getHeight()/3);
        chooseBtn.setPosition(width - chooseBtn.getWidth(),chooseBtn.getHeight());
        chooseBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });
        // Nao ponhas a cena do input que ja esta feito noutra classe. Faz so o design.

        // Funçao para chamar o music picker.
        // musicInterface.showPicker();

        // O play muda o state para 1. Depois quando acaba muda-se para o music picker outravez

        // Um butao back para voltar ao menu screen.

        stage.addActor(background);


        aux_stage = stage;
    }

    public Stage getStage()
    {
        return aux_stage;
    }

    public void render(SpriteBatch batch)
    {
        switch (state)
        {
            case 0:
                aux_stage = stage;
                break;
            case 1:
                game.render();
                aux_stage = game.stage;
                break;
        }
    }

    @Override
    public void dispose () {
        stage.dispose();
    }
}
