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

    public Stage stage, aux_stage;
    private ActualGame game;

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

        aux_stage = new Stage();

        ////Cria os butoes e a informaçao que quiseres neste stage Marcio
        // Nao ponhas a cena do input que ja esta feito noutra classe. Faz so o design.

        // Funçao para chamar o music picker.
        // musicInterface.showPicker();

        // O play muda o state para 1. Depois quando acaba muda-se para o music picker outravez

        // Um butao back para voltar ao menu screen.


        stage = aux_stage;
    }

    public void render()
    {
        switch (state)
        {
            case 0:
                stage = aux_stage;
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
