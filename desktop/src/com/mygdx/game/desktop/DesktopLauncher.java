package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.RaceGame;

public class DesktopLauncher{
	public static void main (String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1440;
		config.height = 900;
		config.resizable = false;
		new LwjglApplication(new RaceGame(), config);
	}
}
