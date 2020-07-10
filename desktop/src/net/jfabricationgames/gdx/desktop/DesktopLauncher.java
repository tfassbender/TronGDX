package net.jfabricationgames.gdx.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import net.jfabricationgames.gdx.TronGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.vSyncEnabled = true; //don't show more frames than the screen refresh rate
		config.title = "TronGDX";
		config.width = 800;
		config.height = 600;
		new LwjglApplication(TronGdxGame.getInstance(), config);
	}
}
