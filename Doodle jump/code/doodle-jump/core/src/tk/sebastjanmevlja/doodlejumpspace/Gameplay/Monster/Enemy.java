package tk.sebastjanmevlja.doodlejumpspace.Gameplay.Monster;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.*;
import tk.sebastjanmevlja.doodlejumpspace.Helpers.HorizontalDirection;

import static tk.sebastjanmevlja.doodlejumpspace.Gameplay.Constants.PPM;




public class Enemy extends Actor {

    public Sprite sprite;
    World world;
    public Body body;
    float bodyWidth = 0;
    float bodyHeight = 0;


    public static float WIDTH;
    public static float HEIGHT;


    boolean alive = true;


    public static final float VELOCITY = Constants.HEIGHT * 0.0015f;

    //    private boolean broken = false;
    public Animation<TextureRegion> runningAnimation;

    // A variable for tracking elapsed time for the animation
    float stateTime;

    HorizontalDirection direction = HorizontalDirection.STILL;


    public Enemy(Array<TextureAtlas.AtlasRegion> textures, World world, float x, float y) {
        sprite = new Sprite(textures.get(0));
        init(world, x, y);



    }



    public Enemy(TextureAtlas.AtlasRegion texture, World world, float x, float y) {
        sprite = new Sprite(texture);
        init(world, x, y);
    }


    void init(World world, float x, float y) {
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
        bodyWidth = sprite.getWidth() * 0.4f / PPM;
        bodyHeight = sprite.getHeight()
                * 0.4f / PPM;
        shape.setAsBox(bodyWidth, bodyHeight);
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
        }

    }




    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (this.alive){
            sprite.draw(batch);
        }


    }

    void checkWallColision() {
        if (direction == HorizontalDirection.RIGHT && sprite.getX() + spriteWidth() >= Constants.WIDTH ) {
            changeDirection();
        }
        else if (direction == HorizontalDirection.LEFT && sprite.getX() < 0){
            changeDirection();
        }
    }





    public void changeDirection(){
        if (direction == HorizontalDirection.RIGHT) {
            body.setLinearVelocity(-VELOCITY, body.getLinearVelocity().y);
            direction = HorizontalDirection.LEFT;
        }
        else if (direction == HorizontalDirection.LEFT){
            body.setLinearVelocity(VELOCITY, body.getLinearVelocity().y);
            direction = HorizontalDirection.RIGHT;
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
    public float getBodyHeight() {
        return this.bodyHeight;
    }

    public float getBodyWidth() {
        return this.bodyWidth;
    }
}
