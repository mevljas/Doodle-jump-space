package tk.sebastjanmevlja.doodlejump.Gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static tk.sebastjanmevlja.doodlejump.Gameplay.Constants.PPM;





public class Platform extends Actor {

    Sprite sprite;
    World world;
    Body body;
    PlatformType type;
    public static float PLATFORM_WIDTH = Constants.WIDTH / 3.5f;
    public static float PLATFORM_HEIGHT = Constants.HEIGHT / 22f;
    private boolean broken = false;
    public Animation<TextureRegion> runningAnimation;
    private boolean alive = true;

    // A variable for tracking elapsed time for the animation
    float stateTime;



    public Platform(PlatformType type, TextureAtlas.AtlasRegion texture, World world, float x, float y) {
        sprite = new Sprite(texture);
        sprite.setSize(Constants.WIDTH / 3.5f, Constants.HEIGHT / 22f);
        sprite.setPosition(x,y);
        sprite.setCenterX(x);
        this.type = type;

        this.world = world;

        // Now create a BodyDefinition.  This defines the physics objects type and position in the simulation
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
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
                /2 / PPM);
        // FixtureDef is a confusing expression for physical properties
        // Basically this is where you, in addition to defining the shape of the body
        // you also define it's properties like density, restitution and others
        // Density and area are used to calculate over all mass
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.1f;
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
        // Shape is the only disposable of the lot, so get rid of it
        shape.dispose();

        if (type == PlatformType.BROWN){
            runningAnimation = new Animation<TextureRegion>(0.08f, Asset.atlas.findRegions("brown_platform"), Animation.PlayMode.NORMAL);
            this.stateTime = 0f;
        }
    }

    public void updatePos(){
        // Set the sprite's position from the updated physics body location
        sprite.setPosition((body.getPosition().x * PPM) - sprite.
                        getWidth()/2 ,
                (body.getPosition().y * PPM) -sprite.getHeight()/2 );
    }



    @Override
    public void act(float delta) {
        super.act(delta);
        updatePos();
        updateAnimations();
    }


    private void updateAnimations(){
        if (this.broken){
            this.stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
            if (this.runningAnimation.isAnimationFinished(this.stateTime)){
                this.alive = false;
//              Move platform offscreen.
//                this.body.setLinearVelocity(0f,-1000);
            }
        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        if (!broken){
            sprite.draw(batch);
        }
        else if (alive){
            // Get current frame of animation for the current stateTime
            TextureRegion currentFrame = runningAnimation.getKeyFrame(stateTime, false);
            batch.draw(currentFrame, sprite.getX(), sprite.getY());

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

    public void breakPlatform(){
        if (this.type == PlatformType.BROWN){
            this.broken = true;
//            this.world.destroyBody(body);
            this.stateTime = 0f;
        }

    }

    public boolean isAlive() {
        return alive;
    }
}
