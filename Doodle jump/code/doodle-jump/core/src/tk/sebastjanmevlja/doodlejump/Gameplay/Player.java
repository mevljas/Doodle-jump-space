package tk.sebastjanmevlja.doodlejump.Gameplay;

import com.badlogic.gdx.graphics.g2d.Sprite;
import tk.sebastjanmevlja.doodlejump.MyGame.Main;


public class Player extends Sprite {


    public Player() {
        super(AssetManager.mainAtlas.findRegion("player_right")); //sprite superclass
        setBounds((GameInfo.WIDTH / 2f) - (getWidth() / 2f), 0, GameInfo.WIDTH / 3.5f, GameInfo.HEIGHT / 8f);
    }


    public void update(float dt) {

    }

    public void draw(Main game) {
        game.getBatch().draw(this, getX(), getY(), getWidth(), getHeight());

    }


}
