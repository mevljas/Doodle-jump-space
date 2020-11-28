package tk.sebastjanmevlja.doodlejump.Gameplay;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static tk.sebastjanmevlja.doodlejump.Gameplay.Constants.PPM;


public class Platform extends Actor {

    Sprite sprite;
    World world;
    Body body;
    public static float PLATFORM_WIDTH = Constants.WIDTH / 3.5f;
    public static float PLATFORM_HEIGHT = Constants.HEIGHT / 22f;


    public Platform(TextureAtlas.AtlasRegion texture, World world, float x, float y) {
        sprite = new Sprite(texture);
        sprite.setSize(Constants.WIDTH / 3.5f, Constants.HEIGHT / 22f);
        sprite.setPosition(x,y);
        sprite.setCenterX(x);

        this.world = world;

        // Now create a BodyDefinition.  This defines the physics objects type and position in the simulation
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
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
        shape.setAsBox(sprite.getWidth()/2 / PPM, sprite.getHeight()
                /2 / PPM);
        // FixtureDef is a confusing expression for physical properties
        // Basically this is where you, in addition to defining the shape of the body
        // you also define it's properties like density, restitution and others
        // Density and area are used to calculate over all mass
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.filter.categoryBits = Constants.PLATFORM_BIT;
        fixtureDef.filter.maskBits = Constants.HERO_BIT;
        Fixture fixture = body.createFixture(fixtureDef);
        // Shape is the only disposable of the lot, so get rid of it
        shape.dispose();

    }



    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }

    public float spriteHeight(){
        return this.sprite.getHeight();
    }

    public float spriteWidth(){
        return this.sprite.getWidth();
    }





}
