package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by bruno on 12/01/2018.
 */

public class Circle {
    private ShapeRenderer shapeRenderer;
    static private boolean projectionMatrixSet;

    private Color color;
    private float approachRate;
    private int circleSize;
    private int x, y;

    public boolean IsActive() {return isActive;}
    public void SetActive(boolean active) {isActive = active;}
    private boolean isActive;
    private float haloSize;
    private float haloTime;
    private float initHaloTime, initHaloSize;
    private double clickTime;

    public double getTime(){ return clickTime; }

    public Circle(double time, int x, int y, int circleSize, float approachRate, Color color)
    {
        shapeRenderer = new ShapeRenderer();
        projectionMatrixSet = false;
        isActive = true;
        initHaloSize = 4.0f * circleSize;
        initHaloTime = 1.0f / approachRate;
        haloTime = initHaloTime;
        haloSize = initHaloSize;
        clickTime = time;

        this.x = x;
        this.y = y;
        this.circleSize = circleSize;
        this.approachRate = approachRate;
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
            shapeRenderer.setColor(color);
            shapeRenderer.circle(x, y, haloSize);
            shapeRenderer.setColor(color);
            shapeRenderer.circle(x, y, haloSize + 1);
            shapeRenderer.setColor(color);
            shapeRenderer.circle(x, y, haloSize + 2);
            shapeRenderer.setColor(color);
            shapeRenderer.circle(x, y, haloSize + 3);
            shapeRenderer.end();
        }
    }

    public void CheckIfClicked(int xInput, int yInput)
    {
        //if (Math.abs(Math.sqrt(Math.pow(x - xInput, 2) + Math.pow(y - yInput, 2))) < circleSize)
        if (xInput < (x + circleSize) && xInput > (x - circleSize) && yInput < (y + circleSize) && yInput > (y - circleSize)) {
            isActive = false;
        }
    }
}
