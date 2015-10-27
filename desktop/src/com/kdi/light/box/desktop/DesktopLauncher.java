package com.kdi.light.box.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.kdi.light.box.LightGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = LightGame.WIDTH;
		config.height = LightGame.HEIGHT;
		new LwjglApplication(new LightGame(), config);
	}
}
