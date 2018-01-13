package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Random;

import jdk.nashorn.internal.runtime.Debug;

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
    private ArrayList<Color> colors;
    private Color currentColor;
    private int indexColor;
    private int colorCycle;
    private int numCombo;
    private CharSequence num;

    private float timeBetweenJump = 0.1f;

    public FlowGenerator(int distance, int circleSize, float approachRate)
    {
        this.circleSize = circleSize;
        this.approachRate = approachRate;
        this.distance = distance;
        random = new Random();
        lastX = 0;
        lastY = 0;
        colors = new ArrayList<Color>();
        colors.add(new Color(226/255.0f, 15/255.0f, 15/255.0f, 1));
        colors.add(new Color(46/255.0f, 206/255.0f, 18/255.0f, 1));
        colors.add(new Color(14/255.0f, 35/255.0f, 201/255.0f, 1));
        colors.add(new Color(255/255.0f, 232/255.0f, 30/255.0f, 1));
        indexColor = 0;
        currentColor = colors.get(indexColor);
        colorCycle = 1;
        numCombo = 1;
        num = Integer.toString(numCombo);
    }

    public ArrayList<Circle> GenerateCombo(double time)
    {
        colorCycle++;
        if (colorCycle > 4)
            CycleColors();
        num = Integer.toString(numCombo);
        numCombo++;

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
        combo.add(new Circle(time, pos.X, pos.Y, circleSize, approachRate, currentColor, num));
    }

    private void TripleJump(ArrayList<Circle> combo)
    {
        Position pos = CalculatePosition();
        combo.add(new Circle(time, pos.X, pos.Y, circleSize, approachRate, currentColor, num));
        pos = CalculatePosition();
        combo.add(new Circle(time + timeBetweenJump, pos.X, pos.Y, circleSize, approachRate, currentColor, num));
        pos = CalculatePosition();
        combo.add(new Circle(time + timeBetweenJump * 2, pos.X, pos.Y, circleSize, approachRate, currentColor, num));
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
        } while ((pos.X > Gdx.graphics.getWidth() - circleSize || pos.X < 0) || (pos.Y > Gdx.graphics.getHeight() - circleSize || pos.Y < 0));

        lastX = pos.X;
        lastY = pos.Y;
        return pos;
    }

    private void CycleColors()
    {
        indexColor++;
        if (indexColor == colors.size())
            indexColor = 0;
        currentColor = colors.get(indexColor);
        colorCycle = 1;
        numCombo = 1;
    }
}
