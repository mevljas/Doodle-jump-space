package tk.sebastjanmevlja.doodlejump.Gameplay.Platform;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.World;
import tk.sebastjanmevlja.doodlejump.Gameplay.Asset;
import tk.sebastjanmevlja.doodlejump.Gameplay.Shield;
import tk.sebastjanmevlja.doodlejump.Gameplay.Trampoline;
import tk.sebastjanmevlja.doodlejump.Level.Level1Screen;


public class GreenPlatform extends Platform {

    private  static final TextureAtlas.AtlasRegion plaformTextureRegionGreen = Asset.atlas.findRegion("platform_green");


    public GreenPlatform(World world, float x, float y) {
        super(plaformTextureRegionGreen, world, x, y);

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



}
