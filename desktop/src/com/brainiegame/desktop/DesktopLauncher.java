package com.robotordenador.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.robotordenador.MainGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "hola vite";
		config.useGL30 = true;
		config.height = 600;
		config.width = 1050;

		new LwjglApplication(new MainGame(), config);
	}
}
