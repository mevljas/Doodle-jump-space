package tk.sebastjanmevlja.doodlejump.Gameplay;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;



public class Player extends Actor {

    Sprite sprite;


    public Player(TextureAtlas.AtlasRegion texture, final String actorName) {
        sprite = new Sprite(texture);
        sprite.setSize(GameInfo.WIDTH / 3.5f, GameInfo.HEIGHT / 8f);
        sprite.setPosition(0,0);
        sprite.setCenterX(GameInfo.WIDTH / 2f);

//        sprite.setBounds((GameInfo.WIDTH / 2f) - (sprite.getWidth() ), 0, GameInfo.WIDTH / 3.5f, GameInfo.HEIGHT / 8f);

        spritePos(sprite.getX(), sprite.getY());
        setTouchable(Touchable.enabled);

    }


    public void spritePos(float x, float y){
        sprite.setPosition(x, y);
        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }





}
