package tk.sebastjanmevlja.doodlejump.Gameplay.Platform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import tk.sebastjanmevlja.doodlejump.Gameplay.Asset;
import tk.sebastjanmevlja.doodlejump.Gameplay.Shield;
import tk.sebastjanmevlja.doodlejump.Gameplay.Sound;
import tk.sebastjanmevlja.doodlejump.Gameplay.Trampoline;


public class BrownPlatform extends Platform {
    private  static final TextureAtlas.AtlasRegion plaformTextureRegionBrown = Asset.atlas.findRegion("brown_platform");
    private static final float FALLING_VELOCITY = VELOCITY * -3f;
    public Animation<TextureRegion> runningAnimation;
    // A variable for tracking elapsed time for the animation
    float stateTime;

    public BrownPlatform(World world, float x, float y) {
        super(plaformTextureRegionBrown, world, x, y);



        runningAnimation = new Animation<TextureRegion>(0.15f, Asset.atlas.findRegions("brown_platform"), Animation.PlayMode.NORMAL);
        this.stateTime = 0f;



    }


    float calculateTrampolinePositionX(){
        return sprite.getX() + PLATFORM_WIDTH / 2 -  Trampoline.TRAMPOLINE_WIDTH / 2;
    }

    float calculateTrampolinePositionY(){
        return sprite.getY() + PLATFORM_HEIGHT * 0.7f;
    }

    float calculateShieldPositionX(){
        return sprite.getX() + PLATFORM_WIDTH / 2 -  Shield.SHIELD_WIDTH / 2;
    }

    float calculateShieldPositionY(){
        return sprite.getY() + PLATFORM_HEIGHT * 0.7f;
    }



    @Override
    public void act(float delta) {
        super.act(delta);
        updateAnimations();

    }



    private void updateAnimations(){
        if (this.broken){
            this.stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
            if (this.runningAnimation.isAnimationFinished(this.stateTime)){
                this.alive = false;
//              Move platform offscreen.
//                this.body.setLinearVelocity(0f,-1000);
            }
        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch,parentAlpha);
        if (!broken){
            sprite.draw(batch);
            if (trampoline != null){
                trampoline.draw(batch, parentAlpha);
            }
            if (shield != null){
                shield.draw(batch, parentAlpha);
            }

        }
        else if (alive){
            // Get current frame of animation for the current stateTime
            TextureRegion currentFrame = runningAnimation.getKeyFrame(stateTime, false);
            batch.draw(currentFrame, sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());

        }

    }


    public void breakPlatform(){
        this.broken = true;
        this.stateTime = 0f;
        Sound.playPlatformBreakingSound();
        this.body.setLinearVelocity(0, FALLING_VELOCITY);

    }




}
