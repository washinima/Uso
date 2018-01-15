package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by bruno on 12/01/2018.
 */

public class Circle {
    private ShapeRenderer shapeRenderer;
    static private boolean projectionMatrixSet;

    private Color color;
    public Color getColor() {return color;}
    public CharSequence getNum() {return num;}
    private CharSequence num;
    private int circleSize;
    private int x;
    public int getX() {return x;}
    public int getY() {return y;}
    private int y;
    private int score;
    public boolean wasClicked;
    public int getScore() {return score;}
    public void setScore(int score) {this.score = score;}
    public boolean IsActive() {return isActive;}
    public void SetActive(boolean active) {isActive = active;}
    private boolean isActive;
    private float haloSize;
    private float haloTime;
    private float initHaloTime, initHaloSize;
    private double clickTime;

    private SpriteBatch batch;
    private BitmapFont font;
    private CharSequence str;

    public double getTime(){ return clickTime; }

    public Circle(double time, int x, int y, int circleSize, Color color, CharSequence num)
    {
        shapeRenderer = new ShapeRenderer();
        projectionMatrixSet = false;
        isActive = true;
        initHaloSize = 4.0f * circleSize;
        initHaloTime = 1.0f;
        haloTime = initHaloTime;
        haloSize = initHaloSize;
        clickTime = time;
        score = 0;
        this.num = num;
        str = num;
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().scale(circleSize / 40.0f);
        wasClicked = false;

        this.x = x;
        this.y = y;
        this.circleSize = circleSize;
        this.color = color;
    }

    public void update()
    {
        if (isActive) {
            haloTime -= Gdx.graphics.getDeltaTime();

            haloSize = (circleSize * (initHaloTime - haloTime) + initHaloSize * (haloTime - 0)) / (initHaloTime - 0);
        }
    }

    public void draw(SpriteBatch batch)
    {
        if (isActive) {
            if (!projectionMatrixSet) {
                shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
            }

            //Actual Circle
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.circle(x, y, circleSize);
            //Circle Inside *aesthetic reasons*
            shapeRenderer.setColor(color);
            shapeRenderer.circle(x, y, circleSize * 0.9f);
            shapeRenderer.end();

            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            //Circle Halo to show when to click
            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.circle(x, y, haloSize);
            shapeRenderer.setColor(color);
            shapeRenderer.circle(x, y, haloSize + 1);
            shapeRenderer.setColor(color);
            shapeRenderer.circle(x, y, haloSize + 2);
            shapeRenderer.setColor(color);
            shapeRenderer.circle(x, y, haloSize + 3);
            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.circle(x, y, haloSize + 4);
            shapeRenderer.end();

            this.batch.begin();
            font.draw(this.batch, str, x - font.getXHeight() / 2, y + font.getSpaceWidth());
            this.batch.end();
        }
    }

    public boolean CheckIfClicked(int xInput, int yInput, double timeWasClicked, double diff)
    {
        yInput = Gdx.graphics.getHeight() - yInput;
        if (Math.abs(Math.sqrt(Math.pow(x - xInput, 2) + Math.pow(y - yInput, 2))) < circleSize) {
            isActive = false;

            double playerTime = (clickTime + diff / 1.85) - timeWasClicked;
            if (playerTime <= 0.15 && playerTime >= -0.15)
                score = 300;
            else if (playerTime <= 0.3)
                score = 100;
            else if (playerTime <= 0.5)
                score = 50;
            else if (playerTime <= 0.7)
                score = 0;

            wasClicked = true;

            return true;
        }
        return false;
    }
}
