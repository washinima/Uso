package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Whisp on 11/01/2018.
 */

public class Hittable extends Actor
{
    private ShapeRenderer shapeRenderer;
    static private boolean projectionMatrixSet;

    private float _time;
    private int _type, _x, _y, _size;

    public float getTime() {return _time;}
    public int GetX() {return _x;}
    public int GetY() {return _y;}
    public int getSize() {return _size;}



    public Hittable(float time, int type){
        shapeRenderer = new ShapeRenderer();
        projectionMatrixSet = false;

        _time = time;
        _type = type;

        _size = 50;
    }

    public void setPosition(int x, int y)
    {
        _x = x;
        _y = y;
    }

    public void draw(SpriteBatch batch)
    {
        if(!projectionMatrixSet){
            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        }
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(_x, _y, _size, _size);
        shapeRenderer.end();
    }
}
