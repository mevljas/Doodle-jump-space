package tk.sebastjanmevlja.doodlejump.Gameplay;

import com.badlogic.gdx.Gdx;

public class Constants {

    public static final int WIDTH = Gdx.graphics.getWidth();
    public static final int HEIGHT = Gdx.graphics.getHeight();
    public static final float PPM = 100f;

    public static final short PLATFORM_BIT = 0x0001;
    public static final short PLAYER_BIT = 0x0002;
    public static final short MONSTER_BIT = 0x0004;
    public static final short WALLS_BIT = 0x0008;


}
