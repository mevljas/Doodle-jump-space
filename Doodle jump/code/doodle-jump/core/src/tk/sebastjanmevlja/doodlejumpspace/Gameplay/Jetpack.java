package tk.sebastjanmevlja.doodlejumpspace.Gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Platform.Platform;

import static tk.sebastjanmevlja.doodlejumpspace.Gameplay.Constants.PPM;


public class Jetpack extends Actor {

    Sprite sprite;
    World world;
    Body body;
    public static float JETPACK_WIDTH = Constants.WIDTH * 0.1f;
    public static float JETPACK_HEIGHT = Constants.HEIGHT * 0.050f;
    public Platform parentPlatform;
    public Animation<TextureRegion> runningAnimation;
    // A variable for tracking elapsed time for the animation
    float stateTime;




    public Jetpack(float x, float y, World world, Platform platform) {
        sprite = new Sprite(Asset.atlas.findRegion("jetpack_item"));
        sprite.setSize(JETPACK_WIDTH, JETPACK_HEIGHT);
        sprite.setPosition(x,y);
        sprite.setCenterX(x);
        this.parentPlatform = platform;

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
        shape.setAsBox(sprite.getWidth() * 0.5f / PPM, sprite.getHeight() * 0.5f / PPM);
        // FixtureDef is a confusing expression for physical properties
        // Basically this is where you, in addition to defining the shape of the body
        // you also define it's properties like density, restitution and others
        // Density and area are used to calculate over all mass
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.filter.categoryBits = Constants.ITEM_BIT;
        fixtureDef.filter.maskBits = Constants.PLAYER_BIT;
        fixtureDef.shape = shape;
        fixtureDef.density = 0.1f;
        fixtureDef.isSensor = true;
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

        // Shape is the only disposable of the lot, so get rid of it
        shape.dispose();

        runningAnimation = new Animation<TextureRegion>(0.15f, Asset.atlas.findRegions("jetpack"), Animation.PlayMode.LOOP);
        this.stateTime = 0f;

    }

    public void updatePos(float x, float y){
        // Set the sprite's position from the updated physics body location
        sprite.setPosition(x,y);
        this.body.setTransform((sprite.getX() + sprite.getWidth()/2) /PPM,
                (sprite.getY() + sprite.getHeight()/2) / PPM, 0 );
        updateAnimations();

    }

    private void updateAnimations(){
        if (this.parentPlatform == null){
            this.stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
        }

    }



    @Override
    public void act(float delta) {
        super.act(delta);
    }




    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (this.parentPlatform != null)
            sprite.draw(batch);
        else {
            // Get current frame of animation for the current stateTime
            TextureRegion currentFrame = runningAnimation.getKeyFrame(stateTime, true);
            batch.draw(currentFrame, sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
        }

    }

    public float spriteHeight(){
        return this.sprite.getHeight();
    }

    public float spriteWidth(){
        return this.sprite.getWidth();
    }

    public Vector2 getBodyPosition(){
        return body.getPosition();
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


    public void incrementGlobalObjectCounter(){
        Culling.incrementObjectsCounter();
    }



}
