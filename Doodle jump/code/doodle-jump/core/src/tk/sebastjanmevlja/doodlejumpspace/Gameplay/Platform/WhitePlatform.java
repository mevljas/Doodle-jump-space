package tk.sebastjanmevlja.doodlejumpspace.Gameplay.Platform;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.World;

import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Asset;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Constants;
import tk.sebastjanmevlja.doodlejumpspace.Helpers.HorizontalDirection;


public class WhitePlatform extends Platform {

    private  static final TextureAtlas.AtlasRegion plaformTextureRegionLightBlue = Asset.atlas.findRegion("white_platform");

    private HorizontalDirection direction = HorizontalDirection.STILL;
    public static final float VELOCITY = PLATFORM_WIDTH * 0.005f;




    public WhitePlatform(World world, float x, float y) {
        super(plaformTextureRegionLightBlue, world, x, y);

        body.setLinearVelocity(VELOCITY, 0);
        this.direction = HorizontalDirection.RIGHT;


    }







    @Override
    public void act(float delta) {
        super.act(delta);
        checkWallColision();


    }

    private void checkWallColision() {
        if (direction == HorizontalDirection.RIGHT && sprite.getX() + spriteWidth() >= Constants.WIDTH ) {
            changeDirection();
        }
        else if (direction == HorizontalDirection.LEFT && sprite.getX() < 0){
            changeDirection();
        }
    }



    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch,parentAlpha);
        sprite.draw(batch);
        if (slime != null){
            slime.draw(batch, parentAlpha);
        }
        if (shield != null){
            shield.draw(batch, parentAlpha);
        }

    }




    public void changeDirection(){
        if (direction == HorizontalDirection.RIGHT) {
            body.setLinearVelocity(-VELOCITY, body.getLinearVelocity().y);
            direction = HorizontalDirection.LEFT;
        }
        else if (direction == HorizontalDirection.LEFT){
            body.setLinearVelocity(VELOCITY, body.getLinearVelocity().y);
            direction = HorizontalDirection.RIGHT;
        }

    }




}
