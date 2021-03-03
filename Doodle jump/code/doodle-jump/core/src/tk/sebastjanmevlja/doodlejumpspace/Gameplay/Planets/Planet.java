package tk.sebastjanmevlja.doodlejumpspace.Gameplay.Planets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Constants;

import static tk.sebastjanmevlja.doodlejumpspace.Gameplay.Constants.PPM;


public class Planet extends Actor {

    public static float PLANET_WIDTH = Constants.WIDTH * 0.08f;
    public static float PLANET_HEIGHT = Constants.HEIGHT * 0.4f;
    public Sprite sprite;
    World world;
    Body body;


    public Planet(TextureAtlas.AtlasRegion texture, World world, float x, float y) {
        this.sprite = new Sprite(texture);
        this.sprite.setSize(PLANET_WIDTH, PLANET_HEIGHT);
        this.sprite.setPosition(x, y);

        this.world = world;

        // Now create a BodyDefinition.  This defines the physics objects type and position in the simulation
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.gravityScale = 0.0f;
        // We are going to use 1 to 1 dimensions.  Meaning 1 in physics engine  is 1 pixel
        // Set our body to the same position as our sprite
        bodyDef.position.set((sprite.getX() + sprite.getWidth() / 2) /
                        PPM,
                (sprite.getY() + sprite.getHeight() / 2) / PPM);

        // Create a body in the world using our definition
        body = world.createBody(bodyDef);


        // Now define the dimensions of the physics shape
        PolygonShape shape = new PolygonShape();
        float bodyWidth = sprite.getWidth() * 0.4f / PPM;
        float bodyHeight = sprite.getHeight() / 2 / PPM;
        shape.setAsBox(bodyWidth, bodyHeight);
        // Basically set the physics polygon to a box with the same dimensions as our sprite
        // FixtureDef is a confusing expression for physical properties
        // Basically this is where you, in addition to defining the shape of the body
        // you also define it's properties like density, restitution and others
        // Density and area are used to calculate over all mass
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.filter.categoryBits = Constants.PLANET_BIT;
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
        updatePos();
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        sprite.draw(batch);
    }

    public void updatePos() {
        // Set the sprite's position from the updated physics body location
        sprite.setPosition((body.getPosition().x * PPM) - sprite.
                        getWidth() / 2,
                (body.getPosition().y * PPM) - sprite.getHeight() / 2);


    }

    public Sprite getSprite() {
        return sprite;
    }

    public Body getBody() {
        return body;
    }


    @Override
    public float getY() {
        return sprite.getY();
    }

    @Override
    public float getX() {
        return sprite.getX();
    }

    @Override
    public float getWidth() {
        return sprite.getWidth();
    }

    @Override
    public float getHeight() {
        return sprite.getHeight();
    }


    public void changePosition(float y) {
        sprite.setSize(PLANET_WIDTH, PLANET_HEIGHT);
        sprite.setY(y);
        body.setTransform((sprite.getX() + sprite.getWidth() / 2) /
                        PPM,
                (sprite.getY() + sprite.getHeight() / 2) / PPM, 0);
    }


}
