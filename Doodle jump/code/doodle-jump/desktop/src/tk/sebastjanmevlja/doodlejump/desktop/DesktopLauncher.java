package tk.sebastjanmevlja.doodlejump.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import tk.sebastjanmevlja.doodlejump.MyGame.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 400;
		config.height = 800;
//		Bug fix for crashing while exiting the program.
		config.forceExit = false;

		new LwjglApplication(new Game(), config);
	}
}


