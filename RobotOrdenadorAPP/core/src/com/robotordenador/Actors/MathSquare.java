package com.robotordenador.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Felipe on 06-12-2017.
 */

public class MathSquare extends Actor {

    private BitmapFont font;
    private int color; public int getColorz(){ return color; } public void setColorz(int c){ this.color = c; }
    private Texture square;

    private int value; public int getValue(){ return value; }

    public MathSquare(int value)
    {
        this.value = value;

        // gives random color square
        color = (int)(Math.random()*6) + 1;
        switch(color)
        {
            case 1:
                square = new Texture("pink.png");
                break;
            case 2:
                square = new Texture("red.png");
                break;
            case 3:
                square = new Texture("closetocyan.png");
                break;
            case 4:
                square = new Texture("orange.png");
                break;
            case 5:
                square = new Texture("yellow.png");
                break;
            case 6:
                square = new Texture("purple.png");
                break;

        }

        setWidth(square.getWidth());
        setHeight(square.getHeight());

        font = new BitmapFont(Gdx.files.internal("UI/outlinedBerlin.fnt"), false);
        font.setColor(Color.WHITE);

    }

    public MathSquare(int value, int col)
    {
        this.value = value;
        this.color = col;

        switch(color)
        {
            case 1:
                square = new Texture("pink.png");
                break;
            case 2:
                square = new Texture("red.png");
                break;
            case 3:
                square = new Texture("closetocyan.png");
                break;
            case 4:
                square = new Texture("orange.png");
                break;
            case 5:
                square = new Texture("yellow.png");
                break;
            case 6:
                square = new Texture("purple.png");
                break;

        }

        // meanwhile set to 50
        setWidth(square.getWidth());
        setHeight(square.getHeight());

        font = new BitmapFont(Gdx.files.internal("testFont.fnt"), false);
        font.setColor(Color.BLACK);
        font.getData().setScale(.2f);
    }

    public void changeToGreen()
    {
        square = new Texture("green.png");
    }


    @Override
    public void act(float delta) {
        super.act(delta);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.draw(square, getX(), getY());
        font.draw(batch, ""+value, getX()+getWidth()/2 - font.getXHeight()*(""+value).length()/2 , getY()+getHeight()/2 + 10);

    }
}
