package tk.sebastjanmevlja.doodlejumpspace.Gameplay;

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

import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Platforms.Platform;
import tk.sebastjanmevlja.doodlejumpspace.Helpers.Assets;
import tk.sebastjanmevlja.doodlejumpspace.Helpers.Constants;

import static tk.sebastjanmevlja.doodlejumpspace.Helpers.Constants.PPM;


public class Shield extends Actor {

    public static float SHIELD_WIDTH = tk.sebastjanmevlja.doodlejumpspace.Helpers.Constants.WIDTH * 0.09f;
    @SuppressWarnings("SuspiciousNameCombination")
    public static float SHIELD_HEIGHT = SHIELD_WIDTH;
    public Platform parentPlatform;
    Sprite sprite;
    World world;
    Body body;
    TextureAtlas.AtlasRegion shieldActivatedAtlasRegion = Assets.atlas.findRegion("shiled_radius");


    public Shield(float x, float y, World world, Platform platform) {
        sprite = new Sprite(Assets.atlas.findRegion("shiled_item"));
        sprite.setSize(SHIELD_WIDTH, SHIELD_HEIGHT);
        sprite.setPosition(x, y);
        sprite.setCenterX(x);
        this.parentPlatform = platform;

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
        // Basically set the physics polygon to a box with the same dimensions as our sprite
        shape.setAsBox(sprite.getWidth() * 0.5f / PPM, sprite.getHeight() * 0.5f / PPM);
        // FixtureDef is a confusing expression for physical properties
        // Basically this is where you, in addition to defining the shape of the body
        // you also define it's properties like density, restitution and others
        // Density and area are used to calculate over all mass
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.filter.categoryBits = tk.sebastjanmevlja.doodlejumpspace.Helpers.Constants.ITEM_BIT;
        fixtureDef.filter.maskBits = Constants.PLAYER_BIT;
        fixtureDef.shape = shape;
        fixtureDef.density = 0.1f;
        fixtureDef.isSensor = true;
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

        // Shape is the only disposable of the lot, so get rid of it
        shape.dispose();

    }

    public void updatePos(float x, float y) {
        // Set the sprite's position from the updated physics body location
        sprite.setPosition(x, y);
        this.body.setTransform((sprite.getX() + sprite.getWidth() / 2) / PPM,
                (sprite.getY() + sprite.getHeight() / 2) / PPM, 0);

    }

    public void setRadiusTexture() {
        this.sprite = new Sprite(shieldActivatedAtlasRegion);
    }


    @Override
    public void act(float delta) {
        super.act(delta);
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {

        sprite.draw(batch);

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


}
