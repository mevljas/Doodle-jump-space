package tk.sebastjanmevlja.doodlejump.Gameplay.Monster;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import tk.sebastjanmevlja.doodlejump.Gameplay.Asset;
import tk.sebastjanmevlja.doodlejump.Gameplay.Constants;
import tk.sebastjanmevlja.doodlejump.Helpers.HorizontalDirection;


public class BlueMonster extends Monster {

    private  static final Array<TextureAtlas.AtlasRegion> textures = Asset.atlas.findRegions("aliens2");



    public BlueMonster(World world, float x, float y) {
        super(textures, world, x, y);
        WIDTH = Constants.WIDTH / 6f;
        HEIGHT = Constants.HEIGHT / 10f;
        runningAnimation = new Animation<TextureRegion>(0.08f, textures, Animation.PlayMode.LOOP);
        this.stateTime = 0f;
        init( world, x, y);
        body.setLinearVelocity(VELOCITY, 0);
        this.direction = HorizontalDirection.RIGHT;


    }








    @Override
    public void act(float delta) {
        super.act(delta);
        if (this.alive){
            checkWallColision();
            updateAnimations();
        }
    }



    @Override
    public void draw(Batch batch, float parentAlpha) {
//        super.draw(batch, parentAlpha);
        if (this.alive){
            TextureRegion currentFrame = runningAnimation.getKeyFrame(stateTime, false);
            batch.draw(currentFrame, sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
        }


    }


}
