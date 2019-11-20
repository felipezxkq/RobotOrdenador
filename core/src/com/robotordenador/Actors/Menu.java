package com.robotordenador.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Timer;
import com.robotordenador.BaseScreen;
import com.robotordenador.OrderScreen;

/**
 * Created by Felipe on 03-01-2018.
 */

public class Menu extends Actor {
    private BitmapFont font; private TextButton gameOver, reintentarButton, resetGameButton, goToMainMenuButton, resumeButton;
    String text;
    int score, colorText; public int getScore(){ return score;} public int getColorText(){ return colorText; }
    private Stage stage;
    private Skin skin;
    private OrderScreen ordenaScreen;
    private Texture gear;
    public Window background;


    private int value; public int getValue(){ return value; }
    private float timeCounter, min;


    public Menu(OrderScreen baseScreen)
    {
        this.ordenaScreen = baseScreen;

        gear = new Texture("gear.png");
        this.setSize(gear.getWidth(), gear.getHeight());

        font = new BitmapFont(Gdx.files.internal("testFont.fnt"), false);
        font.setColor(Color.BLACK);
        font.getData().setScale(.2f);

        skin = new Skin(Gdx.files.internal("starsoldierui/star-soldier-ui.json"));
    }

    // shows the menu stuff
    public void doWhenTouched()
    {
        // specific resume function to memoryArrows game
        if(ordenaScreen.screenNumber == 9)
        {
            ordenaScreen.pause();
        }

        // STOP THE TIME IF NECESSARY :O
        if(ordenaScreen.screenType == 2)
        {
            ordenaScreen.getMyTimer().pauseUnPause(); // TIME FREEZES
        }

        // if there is any way of making all the buttons not touchable, here it goes
        final Actor[] actors = ordenaScreen.getStage().getActors().items;
        for(int i=0; i<actors.length; i++)
        {
            if(actors[i]!=null) {
                actors[i].setVisible(false);
                actors[i].setTouchable(Touchable.disabled);
            }
        }

        // creates the background for the buttons
        background = new Window("          MENU", skin);
        //background.setDebug(true);


        background.setKeepWithinStage(true);
        //background.setPosition(0, Gdx.graphics.getHeight());

        // create buttons
        resumeButton = new TextButton("Continuar", skin);
        resetGameButton = new TextButton("Reiniciar", skin);
        reintentarButton = new TextButton("Reintentar", skin);

        //add functionality to the buttons

        // resume game, time resumes
        resumeButton.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {


                // makes the actors touchable again
                for (Actor element : actors) {
                    if(element!=null)
                    {
                        element.setVisible(true);
                        element.setTouchable(Touchable.enabled);
                    }

                }

                // time resumes if this is a screen with a timer
                if(ordenaScreen.screenType==2)
                {
                    ordenaScreen.getMyTimer().pauseUnPause();
                }

                // removes the Menu window
                background.remove();

            }
        });

        // resets game, reset timer if it has one
        resetGameButton.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                ordenaScreen.getGame().restart();


            }
        });

        reintentarButton.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                ordenaScreen.reintentar();

                // makes the actors touchable again
                for (Actor element : actors) {
                    if(element!=null)
                    {
                        element.setVisible(true);
                        element.setTouchable(Touchable.enabled);
                    }

                }

                // time resumes if this is a screen with a timer
                if(ordenaScreen.screenType==2)
                {
                    ordenaScreen.getMyTimer().pauseUnPause();
                }

                // removes the Menu window
                background.remove();

            }
        });

        // set sizes and positions adjusted to resolution
        // the next 2 lines are about getting the stage's X and Y size
        final float thisX = ordenaScreen.getStage().getViewport().getWorldWidth()/2;
        final float thisY = ordenaScreen.getStage().getViewport().getWorldHeight()/2;


        // add buttons to the stage
        background.add(resumeButton).padBottom(thisY/15);
        background.row();
        background.add(resetGameButton).padBottom(thisY/15);
        background.row();
        background.add(reintentarButton).padBottom(thisY/15);
        ordenaScreen.getStage().addActor(background);
        background.pack();
        background.setPosition(thisX - background.getWidth()/2, thisY - background.getHeight()/2);
    }


    // shows the menu stuff
    public void correcto()
    {
        // specific resume function to memoryArrows game
        if(ordenaScreen.screenNumber == 9)
        {
            ordenaScreen.pause();
        }

        // STOP THE TIME IF NECESSARY :O
        if(ordenaScreen.screenType == 2)
        {
            ordenaScreen.getMyTimer().pauseUnPause(); // TIME FREEZES
        }

        // hace que los actores y botones sean invisibles e intocables mientras se está en el menú
        final Actor[] actors = ordenaScreen.getStage().getActors().items;
        for(int i=0; i<actors.length; i++)
        {
            if(actors[i]!=null) {
                actors[i].setVisible(false);
                actors[i].setTouchable(Touchable.disabled);
            }
        }

        // creates the background for the buttons
        background = new Window("          Correcto", skin);
        //background.setDebug(true);


        background.setKeepWithinStage(true);
        //background.setPosition(0, Gdx.graphics.getHeight());

        // Se crean los botones
        resetGameButton = new TextButton("Reiniciar con mismos numeros", skin);
        reintentarButton = new TextButton("Reiniciar con distintos numeros", skin);

        // resets game, reset timer if it has one
        resetGameButton.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                ordenaScreen.getGame().restart();

            }
        });

        reintentarButton.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                ordenaScreen.reintentar();

                // makes the actors touchable again
                for (Actor element : actors) {
                    if(element!=null)
                    {
                        element.setVisible(true);
                        element.setTouchable(Touchable.enabled);
                    }

                }

                // time resumes if this is a screen with a timer
                if(ordenaScreen.screenType==2)
                {
                    ordenaScreen.getMyTimer().pauseUnPause();
                }

                // removes the Menu window
                background.remove();

            }
        });

        // set sizes and positions adjusted to resolution
        // the next 2 lines are about getting the stage's X and Y size
        final float thisX = ordenaScreen.getStage().getViewport().getWorldWidth()/2;
        final float thisY = ordenaScreen.getStage().getViewport().getWorldHeight()/2;


        // add buttons to the stage
        background.add(resetGameButton).padBottom(thisY/15);
        background.row();
        background.add(reintentarButton).padBottom(thisY/15);
        ordenaScreen.getStage().addActor(background);
        background.pack();
        background.setPosition(thisX - background.getWidth()/2, thisY - background.getHeight()/2);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.draw(gear, getX(), getY());


    }
}
