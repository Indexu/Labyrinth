package com.ru.tgra.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ru.tgra.LabyrinthGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "Labyrinth";
		config.width = 800;
		config.height = 800;
		config.x = 300;
		config.y = 50;

		new LwjglApplication(new LabyrinthGame(), config);
	}
}
