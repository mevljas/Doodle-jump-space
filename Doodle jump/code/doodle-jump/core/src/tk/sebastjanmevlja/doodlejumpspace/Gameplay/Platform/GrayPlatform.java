package tk.sebastjanmevlja.doodlejumpspace.Gameplay.Platform;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.World;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Asset;


public class GrayPlatform extends Platform {

    private  static final TextureAtlas.AtlasRegion plaformTextureRegionGreen = Asset.atlas.findRegion("gray_platform");


    public GrayPlatform(World world, float x, float y, boolean items) {
        super(plaformTextureRegionGreen, world, x, y);

        if (items)
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
        if (slime != null){
            slime.draw(batch, parentAlpha);
        }
        if (shield != null){
            shield.draw(batch, parentAlpha);
        }

    }



}
