package tk.sebastjanmevlja.doodlejump.Gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import tk.sebastjanmevlja.doodlejump.Gameplay.Platform.Platform;

import static tk.sebastjanmevlja.doodlejump.Gameplay.Constants.PPM;


public class Trampoline extends Actor {

    Sprite sprite;
    World world;
    Body body;
    public static float TRAMPOLINE_WIDTH = Platform.PLATFORM_WIDTH * 0.7f;
    public static float TRAMPOLINE_HEIGHT = Platform.PLATFORM_HEIGHT * 1.3f;
    public Animation<TextureRegion> runningAnimation;


    // A variable for tracking elapsed time for the animation
    float stateTime;

    public boolean playerJumping = false;


    public Trampoline( float x, float y, World world) {
        sprite = new Sprite(Asset.atlas.findRegions("trampoline").get(1));
        sprite.setSize(TRAMPOLINE_WIDTH, TRAMPOLINE_HEIGHT);
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
        shape.setAsBox(sprite.getWidth() * 0.4f / PPM, sprite.getHeight() * 0.3f / PPM);
        // FixtureDef is a confusing expression for physical properties
        // Basically this is where you, in addition to defining the shape of the body
        // you also define it's properties like density, restitution and others
        // Density and area are used to calculate over all mass
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.filter.categoryBits = Constants.TRAMPOLINE_BIT;
        fixtureDef.filter.maskBits = Constants.PLAYER_BIT;
        fixtureDef.shape = shape;
        fixtureDef.density = 0.1f;
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
        // Shape is the only disposable of the lot, so get rid of it
        shape.dispose();


        runningAnimation = new Animation<TextureRegion>(0.15f, Asset.atlas.findRegions("trampoline"), Animation.PlayMode.NORMAL);
        this.stateTime = 0f;
    }

    public void updatePos(float x, float y){
        // Set the sprite's position from the updated physics body location
        sprite.setPosition(x,y);
        this.body.setTransform((sprite.getX() + sprite.getWidth()/2) /PPM,
                (sprite.getY() + sprite.getHeight()/2) / PPM, 0 );

        updateAnimations();
    }



    @Override
    public void act(float delta) {
        super.act(delta);
    }



    private void updateAnimations(){
        if (this.playerJumping){
            this.stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
            if (this.runningAnimation.isAnimationFinished(this.stateTime)){
                this.playerJumping = false;
            }
        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (!playerJumping){
            sprite.draw(batch);
        }
        else {
            // Get current frame of animation for the current stateTime
            TextureRegion currentFrame = runningAnimation.getKeyFrame(stateTime, false);
            batch.draw(currentFrame, sprite.getX(), sprite.getY(), TRAMPOLINE_WIDTH, TRAMPOLINE_HEIGHT);

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

    public void playerJump(){
        this.playerJumping = true;
        this.stateTime = 0f;

    }

    public void incrementGlobalObjectCounter(){
        Culling.incrementObjectsCounter();
    }





}
