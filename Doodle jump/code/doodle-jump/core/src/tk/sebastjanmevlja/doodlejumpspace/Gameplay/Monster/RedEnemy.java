package tk.sebastjanmevlja.doodlejumpspace.Gameplay.Monster;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Asset;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Constants;
import tk.sebastjanmevlja.doodlejumpspace.Helpers.HorizontalDirection;


public class RedEnemy extends Enemy {

    private final float leftLocation;
    private final float rightLocation;
    private final float VELOCITY = Constants.WIDTH * 0.003f;
    private  static final TextureAtlas.AtlasRegion texture = Asset.atlas.findRegion("red_enemy");



    public RedEnemy(World world, float x, float y) {
        super(texture, world, x, y);

        WIDTH = Constants.WIDTH / 8f;
        //noinspection SuspiciousNameCombination
        HEIGHT = WIDTH;
        init(world, x, y);

        rightLocation = x + WIDTH * 0.8f;
        leftLocation = x - WIDTH * 0.8f;
        direction = HorizontalDirection.RIGHT;
        body.setLinearVelocity(VELOCITY, body.getLinearVelocity().y);
        runningAnimation = new Animation<TextureRegion>(0.15f, Asset.atlas.findRegions("red_enemy"), Animation.PlayMode.LOOP);
        this.stateTime = 0f;
    }







    @Override
    public void act(float delta) {
        super.act(delta);
        changeDirection();
        updateAnimations();
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

    public void updateAnimations(){
        this.stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

    }





    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (this.alive){
            TextureRegion currentFrame = runningAnimation.getKeyFrame(stateTime, true);
            batch.draw(currentFrame, sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
        }


    }


}
