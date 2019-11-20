package com.robotordenador.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Felipe on 09-12-2017.
 */

public class TextActor extends Actor {

    private BitmapFont font; public BitmapFont getFont(){ return font; }
    public String text; public String getText(){ return text; }
    int color, colorText; public int getColors(){ return color;} public int getColorText(){ return colorText; }



    // this will be used to tell the draw function which constructor to draw
    int constructorUsed;


    private int value; public int getValue(){ return value; }

    // used in MathOperationScreen
    private int place; public int getPlace(){ return place; }
    private float textSize; public float getTextSize(){ return textSize; }


    /* this actor constructor is just some text that says "get x number by adding up"
    , it is used in MathGameScreen */
    public TextActor(int value, String textz)
    {
        constructorUsed = 0;
        this.value = value;
        this.text = textz;

        font = new BitmapFont(Gdx.files.internal("testFont.fnt"), false);
        font.setColor(Color.BLACK);
        font.getData().setScale(.2f);

        // text size, usefull to centrate it
        if(value>=10 || (value<0 && value>-10))
        {
            textSize = font.getXHeight()*2f;
        }
        else if(value<=-10)
        {
            textSize = font.getXHeight()*3f;
        }
        else
        {
            textSize = font.getXHeight();
        }
    }

    // this constructor is used in MatchColorScreen
    public TextActor(int value)
    {
        this.value = value; // value here tell if it is up or down (see MatchColor game)
        constructorUsed = 1;
        color = (int)(Math.random()*5);
        colorText = (int)(Math.random()*5);

        font = new BitmapFont(Gdx.files.internal("testFont.fnt"), false);

        // this will tell the color of the font
        switch(color)
        {
            case 0:
                font.setColor(Color.BLACK);
                break;
            case 1:
                font.setColor(Color.BLUE);
                break;
            case 2:
                font.setColor(Color.GREEN);
                break;
            case 3:
                font.setColor(Color.YELLOW);
                break;
            case 4:
                font.setColor(Color.RED);
                break;
        }

        // this will tell the text
        switch(colorText)
        {
            case 0:
                text = "BLACK";
                //setWidth(font.getXHeight()*5);
                break;
            case 1:
                text ="BLUE";
                //setWidth(font.getXHeight()*4);
                break;
            case 2:
                text = "GREEN";
                //setWidth(font.getXHeight()*5);
                break;
            case 3:
                text = "YELLOW";
                //setWidth(font.getXHeight()*6);
                break;
            case 4:
                text = "RED";
                //setWidth(font.getXHeight()*3);
                break;
        }


        font.getData().setScale(.5f);
        // text size, usefull to centrate it
        if(value>=10 || (value<0 && value>-10))
        {
            textSize = font.getXHeight()*2f;
        }
        else if(value<=-10)
        {
            textSize = font.getXHeight()*3f;
        }
        else
        {
            textSize = font.getXHeight();
        }
        textSize = textSize*font.getScaleX();

    }

    // this constructor can be used everywhere, it is just text
    public TextActor(String textz)
    {
        constructorUsed = 2;
        this.text = textz;

        font = new BitmapFont(Gdx.files.internal("testFont.fnt"), false);
        font.setColor(Color.WHITE);
        font.getData().setScale(.3f);
        // text size, usefull to centrate it
        if(value>=10 || (value<0 && value>-10))
        {
            textSize = font.getXHeight()*2f;
        }
        else if(value<=-10)
        {
            textSize = font.getXHeight()*3f;
        }
        else
        {
            textSize = font.getXHeight();
        }
        textSize = textSize*font.getScaleX();

    }

    // constructor used in MathOperationScreen, to make operators
    public TextActor(String textz, int value)
    {
        this.value = value;
        constructorUsed = 2; // doesn't need to be different
        this.text = textz;

        font = new BitmapFont(Gdx.files.internal("testFont.fnt"), false);
        font.setColor(Color.BLACK);
        font.getData().setScale(.2f);
        setBounds(getX(), getY(), 50, 50);

        // text size, usefull to centrate it
        if(value>=10 || (value<0 && value>-10))
        {
            textSize = font.getXHeight()*2f;
        }
        else if(value<=-10)
        {
            textSize = font.getXHeight()*3f;
        }
        else
        {
            textSize = font.getXHeight();
        }

    }

    // constructor used in MathOperationScreen
    public TextActor(int val, int place)
    {
        constructorUsed = 3;
        value = val;
        this.place = place;

        font = new BitmapFont(Gdx.files.internal("testFont.fnt"), false);
        font.setColor(Color.BLACK);
        font.getData().setScale(.2f);

        // text size
        if(value>=10 || (value<0 && value>-10))
        {
            textSize = font.getXHeight()*2f;
        }
        else if(value<=-10)
        {
            textSize = font.getXHeight()*3f;
        }
        else
        {
            textSize = font.getXHeight();
        }

    }

    // changes font size
    public void changeFontSize(float newSize)
    {
        font.getData().setScale(newSize);
        if(value>=10 || (value<0 && value>-10))
        {
            textSize = font.getXHeight()*2f;
        }
        else if(value<=-10)
        {
            textSize = font.getXHeight()*3f;
        }
        else
        {
            textSize = font.getXHeight();
        }

    }

    public void setPositionCentratedAt(float x, float y)
    {
        setPosition(x - getFont().getXHeight()*getText().length()/2, y);
    }


    @Override
    public void act(float delta) {
        super.act(delta);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        switch(constructorUsed)
        {
            case 0: // MathGameScreen
                font.draw(batch, text+value, getX()+getWidth()/2, getY()+getHeight()/2);
                break;
            case 1: // MatchColorScreen
                font.draw(batch, text, getX(), getY());
                break;
            case 2:
                font.draw(batch, text, getX(), getY()+(getHeight()/2));
                break;
            case 3: // MathOperationScreen
                font.draw(batch, ""+value, getX(), getY()+(getHeight()/2));
                break;
        }


    }
}
