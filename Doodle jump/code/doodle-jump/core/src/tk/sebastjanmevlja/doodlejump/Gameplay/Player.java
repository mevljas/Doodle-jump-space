package tk.sebastjanmevlja.doodlejump.Gameplay;

import com.badlogic.gdx.graphics.g2d.Sprite;
import tk.sebastjanmevlja.doodlejump.MyGame.Game;


public class Player extends Sprite {


    public Player( ) {
        super(AssetStorage.atlas.findRegion("player_right")); //sprite superclass
        setBounds((GameInfo.WIDTH / 2f) - (getWidth() / 2f), 0, GameInfo.WIDTH / 3.5f, GameInfo.HEIGHT / 8f);
    }


    public void update(float dt) {

    }

    public void draw(Game game) {
        game.getBatch().draw(this, getX(), getY(), getWidth(), getHeight());

    }


}
