package tk.sebastjanmevlja.doodlejumpspace.Gameplay.Monster;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.World;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Asset;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Constants;
import tk.sebastjanmevlja.doodlejumpspace.Helpers.HorizontalDirection;


public class RedEnemy extends Enemy {

    private float leftLocation;
    private float rightLocation;
    private float VELOCITY = Constants.WIDTH * 0.003f;
    private  static final TextureAtlas.AtlasRegion texture = Asset.atlas.findRegion("red_enemy");



    public RedEnemy(World world, float x, float y) {
        super(texture, world, x, y);

        WIDTH = Constants.WIDTH / 8f;
        HEIGHT = WIDTH;
        init(world, x, y);

        rightLocation = x + WIDTH * 0.8f;
        leftLocation = x - WIDTH * 0.8f;
        direction = HorizontalDirection.RIGHT;
        body.setLinearVelocity(VELOCITY, body.getLinearVelocity().y);
    }







    @Override
    public void act(float delta) {
        super.act(delta);
        changeDirection();
    }

    public void changeDirection(){
        if (direction == HorizontalDirection.RIGHT && sprite.getX() + sprite.getWidth() >= rightLocation){
            direction = HorizontalDirection.LEFT;
            body.setLinearVelocity(-VELOCITY, body.getLinearVelocity().y);

        }
        else if (direction == HorizontalDirection.LEFT && sprite.getX() <= leftLocation){
            direction = HorizontalDirection.RIGHT;
            body.setLinearVelocity(VELOCITY, body.getLinearVelocity().y);

        }
    }



    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (this.alive){
            sprite.draw(batch);
        }


    }


}
