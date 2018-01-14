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
    private InformationText informationText;

    private Image background, playBtn, chooseBtn, backBtn;

    private MusicInterface musicInterface;

    private int state;

    private int width;
    private int height;
    private boolean leave;

    public PlayScreen(int width, int height, MusicInterface musicInterface) {
        this.width = width;
        this.height = height;

        game = new ActualGame(width, height, musicInterface);
        this.musicInterface = musicInterface;

        leave = false;
    }

    @Override
    public void create(){

        state = 0;

        stage = new Stage(new ScreenViewport());
        background = new Image(new Texture("menulist_background.png"));
        background.setSize(width,height);

        playBtn = new Image(new Texture("noplay.png"));
        playBtn.setSize(playBtn.getWidth()/2,playBtn.getHeight()/2);
        playBtn.setPosition(80, height - (playBtn.getHeight()*2) - 160);
        playBtn.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(musicInterface.isReady()){
                        state = 1;
                        musicInterface.SetupRun();
                        game.create();
                    }
                    //else{ Imprimir texto de ajuda }

                }
        });

        chooseBtn = new Image(new Texture("choose.png"));
        chooseBtn.setSize(playBtn.getWidth(),playBtn.getHeight());
        chooseBtn.setPosition(80, (height - playBtn.getHeight())-80);
        chooseBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                musicInterface.showPicker();
            }
        });

        backBtn = new Image(new Texture("back_button.png"));
        backBtn.setSize(backBtn.getWidth(),backBtn.getHeight());
        backBtn.setPosition(20,20);
        backBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                leave = true;
            }
        });

        informationText = new InformationText();
        informationText.create();
        // Nao ponhas a cena do input que ja esta feito noutra classe. Faz so o design.

        // Funçao para chamar o music picker.
        // musicInterface.showPicker();

        // O play muda o state para 1. Depois quando acaba muda-se para o music picker outravez

        // Um butao back para voltar ao menu screen.

        stage.addActor(background);
        stage.addActor(chooseBtn);
        stage.addActor(playBtn);
        stage.addActor(backBtn);

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
                //informationText.render();
                break;
            case 1:
                if(musicInterface.isReady()) {
                    playBtn.setDrawable(new SpriteDrawable(new Sprite(new Texture("play.png"))));
                    musicInterface.SetupRun();
                }

                game.render(batch);
                aux_stage = game.stage;

                if(game.endGame)
                {
                    state = 0;
                    game.create();
                }
                break;
        }
    }

    @Override
    public void dispose () {
        stage.dispose();
    }

    public boolean getLeave(){ return leave; }
    public void setLeave(boolean value){ leave = value; }

}
