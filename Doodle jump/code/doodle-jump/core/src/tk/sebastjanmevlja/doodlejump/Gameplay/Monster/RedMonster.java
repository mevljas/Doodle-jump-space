package tk.sebastjanmevlja.doodlejump.Gameplay.Monster;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import tk.sebastjanmevlja.doodlejump.Gameplay.Constants;


public class RedMonster extends Monster {


    public RedMonster(Array<TextureAtlas.AtlasRegion> textures, World world, float x, float y) {
        super(textures, world, x, y);


        HEIGHT = Constants.HEIGHT / 10f;
        WIDTH = HEIGHT;


        init( world, x, y);

        body.setLinearVelocity(VELOCITY, 0);


    }



    public RedMonster( TextureAtlas.AtlasRegion texture, World world, float x, float y) {
        super(texture, world, x, y);


        WIDTH = Constants.WIDTH / 8f;
        HEIGHT = WIDTH;


        init(world, x, y);
    }







    @Override
    public void act(float delta) {
        super.act(delta);

    }



    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (this.alive){
            sprite.draw(batch);
        }


    }


}
