package tk.sebastjanmevlja.doodlejumpspace;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import tk.sebastjanmevlja.doodlejumpspace.MyGame.Game;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useWakelock = true; //screen stays on
		config.useAccelerometer = true;
		initialize(new Game(), config);
	}
}
