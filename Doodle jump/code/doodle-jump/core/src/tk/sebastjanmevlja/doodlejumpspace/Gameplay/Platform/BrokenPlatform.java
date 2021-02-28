package tk.sebastjanmevlja.doodlejumpspace.Gameplay.Platform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Asset;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Shield;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Sound;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Slime;


public class BrokenPlatform extends Platform {
    private  static final TextureAtlas.AtlasRegion plaformTextureRegionBrown = Asset.atlas.findRegion("broken_platform");
    private static final float FALLING_VELOCITY = VELOCITY * -4f;
    public Animation<TextureRegion> runningAnimation;
    // A variable for tracking elapsed time for the animation
    float stateTime;

    public BrokenPlatform(World world, float x, float y) {
        super(plaformTextureRegionBrown, world, x, y);



        runningAnimation = new Animation<TextureRegion>(0.15f, Asset.atlas.findRegions("broken_platform"), Animation.PlayMode.NORMAL);
        this.stateTime = 0f;



    }


    float calculateSlimePositionX(){
        return sprite.getX() + PLATFORM_WIDTH / 2 -  Slime.TRAMPOLINE_WIDTH / 2;
    }

    float calculateSlimePositionY(){
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
            if (slime != null){
                slime.draw(batch, parentAlpha);
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
