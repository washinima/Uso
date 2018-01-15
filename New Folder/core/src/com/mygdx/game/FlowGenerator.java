package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    private int circleSize;
    private ArrayList<Color> colors;
    private Color currentColor;
    private int indexColor;
    private int colorCycle;
    private int numCombo;
    private CharSequence num;
    private float timeBetweenJump = 0.3f;
    SpriteBatch batch;

    public FlowGenerator(int distance, int circleSize)
    {
        batch = new SpriteBatch();
        this.circleSize = circleSize;
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
        numCombo = 0;
        num = Integer.toString(numCombo);
    }

    public ArrayList<Circle> GenerateCombo(double time)
    {
        colorCycle++;
        if (colorCycle > 4)
            CycleColors();

        numCombo++;
        num = Integer.toString(numCombo);

        this.time = time;
        circles = new ArrayList<Circle>();
        int r = random.nextInt(101);
        int opt = 0;
        if (r > 90)
            opt = 1;
        else if (r > 80)
            opt = 2;
        switch (opt)
        {
            case 0:
                SingleCircle(circles);
                break;
            case 1:
                TripleJump(circles);
                break;
            case 2:
                DoubleStream(circles);
                break;
        }

        return circles;
    }

    private void SingleCircle(ArrayList<Circle> combo)
    {
        Position pos = CalculatePosition(distance);
        combo.add(new Circle(time, pos.X, pos.Y, circleSize, currentColor, num, batch));
    }

    private void TripleJump(ArrayList<Circle> combo)
    {
        Position pos = CalculatePosition(distance);
        combo.add(new Circle(time, pos.X, pos.Y, circleSize, currentColor, num, batch));
        numCombo++;
        num = Integer.toString(numCombo);
        pos = CalculatePosition(distance);
        combo.add(new Circle(time + timeBetweenJump, pos.X, pos.Y, circleSize, currentColor, num, batch));
        numCombo++;
        num = Integer.toString(numCombo);
        pos = CalculatePosition(distance);
        combo.add(new Circle(time + timeBetweenJump * 2, pos.X, pos.Y, circleSize, currentColor, num,batch));
    }

    private void DoubleStream(ArrayList<Circle> combo)
    {
        Position pos = CalculatePosition(distance);
        combo.add(new Circle(time, pos.X, pos.Y, circleSize, currentColor, num,batch));
        numCombo++;
        num = Integer.toString(numCombo);
        pos = CalculatePosition((int)(distance / 10.0));
        combo.add(new Circle(time + timeBetweenJump, pos.X, pos.Y, circleSize, currentColor, num,batch));
    }

    private Position CalculatePosition(int distance)
    {
        Position pos = new Position();
        do
        {
            double angle = random.nextInt(360);
            pos.X = (int)(lastX + distance * (float)Math.cos(angle));
            pos.Y = (int)(lastY + distance * -(float)(Math.sin(angle)));
        } while ((pos.X > Gdx.graphics.getWidth() - circleSize || pos.X < 0 + circleSize) || (pos.Y > Gdx.graphics.getHeight() - circleSize || pos.Y < 0 + circleSize));

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
        numCombo = 0;
    }
}
