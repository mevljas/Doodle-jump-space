package tk.sebastjanmevlja.doodlejump.Gameplay.Platform;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.World;
import tk.sebastjanmevlja.doodlejump.Gameplay.Asset;
import tk.sebastjanmevlja.doodlejump.Gameplay.Constants;
import tk.sebastjanmevlja.doodlejump.Gameplay.Shield;
import tk.sebastjanmevlja.doodlejump.Gameplay.Trampoline;
import tk.sebastjanmevlja.doodlejump.Helpers.HorizontalDirection;
import tk.sebastjanmevlja.doodlejump.Level.Level1Screen;


public class BluePlatform extends Platform {

    private  static final TextureAtlas.AtlasRegion plaformTextureRegionLightBlue = Asset.atlas.findRegion("platform__blue");

    private HorizontalDirection direction = HorizontalDirection.STILL;
    public static final float VELOCITY = PLATFORM_WIDTH * 0.005f;




    public BluePlatform( World world, float x, float y) {
        super(plaformTextureRegionLightBlue, world, x, y);

        body.setLinearVelocity(VELOCITY, 0);
        this.direction = HorizontalDirection.RIGHT;


        if (r.nextInt(15) > 12){
            this.trampoline = new Trampoline(calculateTrampolinePositionX(),calculateTrampolinePositionY(),world);
            Level1Screen.backgroundGroup.addActor(this.trampoline);
        }
        else if (r.nextInt(15) > 12){
            this.shield = new Shield(calculateShieldPositionX(),calculateShieldPositionY(),world, this);
            Level1Screen.backgroundGroup.addActor(this.shield);
        }


    }







    @Override
    public void act(float delta) {
        super.act(delta);
        updatePos();
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
        if (trampoline != null){
            trampoline.draw(batch, parentAlpha);
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
