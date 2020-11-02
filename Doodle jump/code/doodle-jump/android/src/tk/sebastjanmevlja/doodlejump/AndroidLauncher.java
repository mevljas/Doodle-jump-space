package tk.sebastjanmevlja.doodlejump;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import tk.sebastjanmevlja.doodlejump.MyGame.Main;
import tk.sebastjanmevlja.doodlejump.MyGdxGame;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useWakelock = true; //screen stays on
		initialize(new Main(), config);
	}
}
