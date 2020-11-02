package tk.sebastjanmevlja.doodlejump.Gameplay;

import com.badlogic.gdx.graphics.g2d.Sprite;
import tk.sebastjanmevlja.doodlejump.MyGame.Main;


public class Platform extends Sprite {


    public Platform() {
        super(AssetManager.mainAtlas.findRegion("platform_green")); //sprite superclass
        setBounds((GameInfo.WIDTH / 2f) - (getWidth() / 2f), (GameInfo.HEIGHT / 2f), GameInfo.WIDTH / 4f, GameInfo.HEIGHT / 25f);
    }


    public void update(float dt) {

    }

    public void draw(Main game) {
        game.getBatch().draw(this, getX(), getY(), getWidth(), getHeight());

    }


}
