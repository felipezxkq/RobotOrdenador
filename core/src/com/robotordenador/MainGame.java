package com.robotordenador;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainGame extends Game {

	private AssetManager manager;
	public AssetManager getManager() { return manager; }
	private Boolean sound=true; public boolean getSound(){ return sound; }
	public void changeSound()
	{
		if(this.sound)
		{
			this.sound = false; // turns off the sound
		}
		else
		{
			this.sound = true; // turns the sound back on
		}
	}

	public BaseScreen loadingScreen, mainScreen, cardGameScreen, mathGameScreen
			, matchColorsScreen, triangleMathScreen, figurinesScreen
			, arrowsMemoryScreen, mathOperationScreen, quickQuestionsScreen
			, isThisTheSameScreen, gamesScreen, balanceScreen, orderScreen;

	@Override
	public void create () {
		manager = new AssetManager();
		manager.load("orange.png", Texture.class);
		manager.load("pink.png", Texture.class);
		manager.load("yellow.png", Texture.class);
		manager.load("green.png", Texture.class);
		manager.load("closetocyan.png", Texture.class);
		manager.load("red.png", Texture.class);
		manager.load("correct.png", Texture.class);
		manager.load("incorrect.png", Texture.class);
		manager.load("gear.png", Texture.class);
		manager.load("purple.png", Texture.class);


		manager.finishLoading();

		gamesScreen = new OrderScreen(this);
		setScreen(gamesScreen);

	}

	public void restart()
	{
		gamesScreen = new OrderScreen(this);
		setScreen(gamesScreen);
	}



}