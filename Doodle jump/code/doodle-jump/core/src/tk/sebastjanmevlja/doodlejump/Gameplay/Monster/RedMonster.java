package tk.sebastjanmevlja.doodlejump.Gameplay.Monster;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import tk.sebastjanmevlja.doodlejump.Gameplay.Constants;

import static tk.sebastjanmevlja.doodlejump.Gameplay.Constants.PPM;


public class RedMonster extends Monster {


    public RedMonster(Array<TextureAtlas.AtlasRegion> textures, World world, float x, float y) {
        super(textures, world, x, y);

        WIDTH = Constants.WIDTH / 6f;
        HEIGHT = Constants.HEIGHT / 8f;


        init( world, x, y);

        body.setLinearVelocity(VELOCITY, 0);


    }



    public RedMonster( TextureAtlas.AtlasRegion texture, World world, float x, float y) {
        super(texture, world, x, y);


        WIDTH = Constants.WIDTH / 8f;
        HEIGHT = WIDTH;


        init(world, x, y);
    }


    private void init(World world, float x, float y) {
        sprite.setSize(WIDTH, HEIGHT);
        sprite.setPosition(x,y);
        sprite.setCenterX(x);

        this.world = world;

        // Now create a BodyDefinition.  This defines the physics objects type and position in the simulation
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.gravityScale = 0.0f;
        // We are going to use 1 to 1 dimensions.  Meaning 1 in physics engine  is 1 pixel
        // Set our body to the same position as our sprite
        bodyDef.position.set((sprite.getX() + sprite.getWidth()/2) /
                        PPM,
                (sprite.getY() + sprite.getHeight()/2) / PPM);

        // Create a body in the world using our definition
        body = world.createBody(bodyDef);


        // Now define the dimensions of the physics shape
        PolygonShape shape = new PolygonShape();
        // Basically set the physics polygon to a box with the same dimensions as our sprite
        shape.setAsBox(sprite.getWidth() * 0.4f / PPM, sprite.getHeight()
                * 0.4f / PPM);
        // FixtureDef is a confusing expression for physical properties
        // Basically this is where you, in addition to defining the shape of the body
        // you also define it's properties like density, restitution and others
        // Density and area are used to calculate over all mass
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.filter.categoryBits = Constants.MONSTER_BIT;
        fixtureDef.filter.maskBits = Constants.BULLET_BIT | Constants.PLAYER_BIT;
        fixtureDef.shape = shape;
        fixtureDef.density = 0.1f;
        fixtureDef.isSensor = true;
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
        // Shape is the only disposable of the lot, so get rid of it
        shape.dispose();

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
