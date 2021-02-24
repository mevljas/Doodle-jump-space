package tk.sebastjanmevlja.doodlejumpspace.Gameplay.Platform;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.World;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Asset;


public class GreenPlatform extends Platform {

    private  static final TextureAtlas.AtlasRegion plaformTextureRegionGreen = Asset.atlas.findRegion("platform_green");


    public GreenPlatform(World world, float x, float y) {
        super(plaformTextureRegionGreen, world, x, y);

        generateItems();
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
