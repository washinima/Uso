package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by bruno on 13/01/2018.
 */

class Position
{
    public int X;
    public int Y;
};

public class FlowGenerator {
    private ArrayList<Circle> circles;
    private Random random;
    private int distance;
    private int lastX, lastY;
    private double time;
    private float approachRate;
    private int circleSize;

    private float timeBetweenJump = 0.1f;

    public FlowGenerator(int distance, int circleSize, float approachRate)
    {
        this.circleSize = circleSize;
        this.approachRate = approachRate;
        this.distance = distance;
        random = new Random();
        lastX = 0;
        lastY = 0;
    }

    public ArrayList<Circle> GenerateCombo(double time)
    {
        this.time = time;
        circles = new ArrayList<Circle>();
        int opt = random.nextInt(1);
        switch (opt)
        {
            case 0:
                SingleCircle(circles);
                break;
            case 1:
                TripleJump(circles);
                break;
            case 2:
                break;
            case 3:
                break;
        }

        return circles;
    }

    private void SingleCircle(ArrayList<Circle> combo)
    {
        Position pos = CalculatePosition();
        combo.add(new Circle(time, pos.X, pos.Y, circleSize, approachRate, Color.RED));
    }

    private void TripleJump(ArrayList<Circle> combo)
    {
        Position pos = CalculatePosition();
        combo.add(new Circle(time, pos.X, pos.Y, circleSize, approachRate, Color.RED));
        pos = CalculatePosition();
        combo.add(new Circle(time + timeBetweenJump, pos.X, pos.Y, circleSize, approachRate, Color.RED));
        pos = CalculatePosition();
        combo.add(new Circle(time + timeBetweenJump * 2, pos.X, pos.Y, circleSize, approachRate, Color.RED));
    }

    private void TripleStream(ArrayList<Circle> combo)
    {
        Position pos = CalculatePosition();
    }

    private Position CalculatePosition()
    {
        Position pos = new Position();
        do
        {
            double angle = random.nextInt(359);
            pos.X = (int)(lastX + distance * (float)Math.cos(angle));
            pos.Y = (int)(lastY + distance * -(float)(Math.sin(angle)));
        } while ((pos.X > Gdx.graphics.getWidth() || pos.X < 0) || (pos.Y > Gdx.graphics.getHeight() || pos.Y < 0));

        lastX = pos.X;
        lastY = pos.Y;
        return pos;
    }
}
