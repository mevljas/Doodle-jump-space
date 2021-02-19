package tk.sebastjanmevlja.doodlejump.Gameplay.Monster;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import tk.sebastjanmevlja.doodlejump.Gameplay.*;
import tk.sebastjanmevlja.doodlejump.Helpers.HorizontalDirection;

import static tk.sebastjanmevlja.doodlejump.Gameplay.Constants.PPM;




public class Monster extends Actor {

    public Sprite sprite;
    World world;
    public Body body;


    public static float WIDTH = Constants.WIDTH / 5f;
    public static float HEIGHT = Constants.HEIGHT / 8f;


    boolean alive = true;


    public static final float VELOCITY = Constants.HEIGHT * 0.0015f;

    //    private boolean broken = false;
    public Animation<TextureRegion> runningAnimation;

    // A variable for tracking elapsed time for the animation
    float stateTime;

    HorizontalDirection direction = HorizontalDirection.STILL;


    public Monster( Array<TextureAtlas.AtlasRegion> textures, World world, float x, float y) {
        sprite = new Sprite(textures.get(0));


        init(world, x, y);



    }



    public Monster( TextureAtlas.AtlasRegion texture, World world, float x, float y) {
        sprite = new Sprite(texture);



        init(world, x, y);
    }


    private void init( World world, float x, float y) {
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





    public void updatePos(){
        // Set the sprite's position from the updated physics body location
        sprite.setPosition((body.getPosition().x * PPM) - sprite.
                        getWidth()/2 ,
                (body.getPosition().y * PPM) -sprite.getHeight()/2 );
    }



    void updateAnimations(){
        this.stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

    }



    @Override
    public void act(float delta) {
        super.act(delta);
        if (this.alive){
            updatePos();
            checkWallColision();

        }

    }

    void checkWallColision() {
        if (sprite.getX() + spriteWidth() >= Constants.WIDTH || sprite.getX() <= 0) {
            changeDirection();
        }
    }



    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (this.alive){
            sprite.draw(batch);
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

    public boolean isAlive() {
        return alive;
    }


    public void changeDirection(){
        body.setLinearVelocity(-body.getLinearVelocity().x, body.getLinearVelocity().y);
    }

    public void kill() {
        this.alive = false;
        Player.incScore();
        Sound.playMonsterSound();
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
