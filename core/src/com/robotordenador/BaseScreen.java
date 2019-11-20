package com.robotordenador;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.robotordenador.Actors.Menu;
import com.robotordenador.Actors.MyTimer;

/**
 * Created by Felipe on 30-11-2017.
 */

public abstract class BaseScreen implements Screen{

    protected MainGame game; public MainGame getGame(){ return game; }
    public Stage stage; public Stage getStage(){ return stage; }
    public int score; public int getScore(){ return score; }
    //public boolean sound = true; public boolean getSound(){ return sound; }
    public int screenType; // 0=MainGameScreen, 1 = screen without timer, 2= screen with timer
    public MyTimer myTimer; public MyTimer getMyTimer(){ return myTimer; }
    public int screenNumber; public void setScreenNumber(int b){ this.screenNumber = b; } public int getScreenNumber(){ return screenNumber; }
    public Menu menu;

    public BaseScreen(MainGame game)
    {
        this.game = game;
    }

    // this is useful
    public void prepareLevel(){}

    public void prepareLevel(int a){}

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }


    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
