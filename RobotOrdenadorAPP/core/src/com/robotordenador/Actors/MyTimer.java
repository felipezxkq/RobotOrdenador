package com.robotordenador.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.robotordenador.BaseScreen;


/**
 * Created by Felipe on 20-12-2017.
 */

public class MyTimer extends Actor {
    public BitmapFont font; private TextButton gameOver;
    String text;
    int score, colorText; public int getScore(){ return score;} public int getColorText(){ return colorText; }
    private Stage stage;
    private Skin skin;
    private BaseScreen baseScreen;
    private boolean paused;
    public void pauseUnPause(){
        paused = !paused;
    }
    public boolean getGameState(){ return paused; }

    // this will be used to tell the draw function which constructor to draw
    int constructorUsed;

    private int value; public int getValue(){ return value; }
    private float timeCounter, min;


    public MyTimer(float min, BaseScreen baseScreen)
    {
        paused = false;
        this.baseScreen = baseScreen;
        //baseScreen.getStage() = stage;
        this.min = min;
        font = new BitmapFont(Gdx.files.internal("testFont.fnt"), false);
        font.setColor(Color.BLACK);
        font.getData().setScale(.2f);

        skin = new Skin(Gdx.files.internal("starsoldierui/star-soldier-ui.json"));
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        if(!paused) {
            timeCounter += Gdx.graphics.getDeltaTime();
            if (timeCounter >= 1.0f && min > 0) {
                timeCounter = 0;
                min = min - 1;
            }

            // here goes to a score screen if min==0 (time is over)
            if (min == 0) {
                baseScreen.getStage().clear();

                // the next 2 lines are about getting the stage's X and Y size
                final float thisX = baseScreen.getStage().getViewport().getWorldWidth() / 2;
                final float thisY = baseScreen.getStage().getViewport().getWorldHeight() / 2;



                gameOver = new TextButton("Well done, your score is: " + baseScreen.getScore() + "\n tap here to continue", skin);
                gameOver.setSize(500, 100);
                gameOver.setPosition(thisX - gameOver.getWidth() / 2, thisY - gameOver.getHeight() / 2);
                gameOver.addCaptureListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        baseScreen.getStage().clear();
                        baseScreen.prepareLevel();
                        //counter = 0;
                        // Here I go to the game screen again.
                        baseScreen.getGame().setScreen(baseScreen.getGame().mainScreen);
                    }
                });
                baseScreen.getStage().addActor(gameOver);
            }
        }


    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        if(min>0) {
            font.draw(batch, "" + (int) min, getX() + getWidth() / 2, getY() + (getHeight() / 2));
        }

    }
}

