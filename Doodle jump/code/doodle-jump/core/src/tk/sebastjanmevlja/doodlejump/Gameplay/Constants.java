package tk.sebastjanmevlja.doodlejump.Gameplay;

import com.badlogic.gdx.Gdx;

public class Constants {

    public static final int WIDTH = Gdx.graphics.getWidth();
    public static final int HEIGHT = Gdx.graphics.getHeight();
    public static final float PPM = 100f;

    public static final short PLATFORM_BIT = 1;
    public static final short PLAYER_BIT = 2;
    public static final short MONSTER_BIT = 4;
    public static final short WALLS_BIT = 8;
    public static final short TRAMPOLINE_BIT = 16;
    public static final short BULLET_BIT = 32;


}
