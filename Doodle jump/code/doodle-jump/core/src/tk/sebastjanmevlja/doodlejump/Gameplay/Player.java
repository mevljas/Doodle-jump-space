package tk.sebastjanmevlja.doodlejump.Gameplay;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static tk.sebastjanmevlja.doodlejump.Gameplay.Constants.PPM;




enum VerticalDirection {
    UP,DOWN, STILL
}


enum HorizontalDirection {
    LEFT, RIGHT, STILL
}


public class Player extends Actor {

    static Player player;
    static int lives;
    static int score;

    Sprite sprite;
    World world;
    Body body;

    VerticalDirection verticalDirection = VerticalDirection.STILL;
    HorizontalDirection horizontalDirection = HorizontalDirection.STILL;

    public static final float JUMP_VELOCITY = Constants.HEIGHT * 0.0035f;
    public static final float JUMP_VELOCITY_TRAMPOLINE = Constants.HEIGHT * 0.004f;
    public static final float HORIZONTAL_VELOCITY = Constants.WIDTH * 0.005f;

    public static float WIDTH = Constants.WIDTH / 3.5f;
    public static float HEIGHT = Constants.HEIGHT / 8f;

    private static TextureAtlas.AtlasRegion up = Asset.atlas.findRegion("player_up");
    private static TextureAtlas.AtlasRegion leftFall = Asset.atlas.findRegion("player_left_jump");
    private static TextureAtlas.AtlasRegion rightFall = Asset.atlas.findRegion("player_right_jump");
    private static TextureAtlas.AtlasRegion leftJump = Asset.atlas.findRegion("player_left");
    private static TextureAtlas.AtlasRegion rightJump = Asset.atlas.findRegion("player_right");


    public Player( World world, float x, float y) {
        sprite = new Sprite(up);
        sprite.setSize(WIDTH, HEIGHT);
        sprite.setPosition(x, y);
        sprite.setCenterX(x);


        this.world = world;

        // Now create a BodyDefinition.  This defines the physics objects type and position in the simulation
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        // We are going to use 1 to 1 dimensions.  Meaning 1 in physics engine  is 1 pixel
        // Set our body to the same position as our sprite
//        bodyDef.position.set(sprite.getX() + sprite.getWidth() / 2f , sprite.getY() + sprite.getHeight() / 2f);
        bodyDef.position.set((sprite.getX() + sprite.getWidth()/2) /
                        PPM,
                (sprite.getY() + sprite.getHeight()/2) / PPM);

        bodyDef.fixedRotation = true;



        // Create a body in the world using our definition
        body = world.createBody(bodyDef);


        // Now define the dimensions of the physics shape
        PolygonShape shape = new PolygonShape();
        // Basically set the physics polygon to a box with the same dimensions as our sprite
        shape.setAsBox(sprite.getWidth() / 4 / PPM, sprite.getHeight()
                * 0.33f / PPM);
        // FixtureDef is a confusing expression for physical properties
        // Basically this is where you, in addition to defining the shape of the body
        // you also define it's properties like density, restitution and others
        // Density and area are used to calculate over all mass
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.filter.categoryBits = Constants.PLAYER_BIT;
        fixtureDef.filter.maskBits = Constants.PLATFORM_BIT | Constants.MONSTER_BIT | Constants.WALLS_BIT | Constants.TRAMPOLINE_BIT;
        fixtureDef.shape = shape;
        fixtureDef.density = 0.1f;
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
        // Shape is the only disposable of the lot, so get rid of it
        shape.dispose();
        player = this;
        lives = 5;
        score = 0;

    }

    public Vector2 getBodyPosition(){
        return body.getPosition();
    }






    public void updatePos(){
        // Set the sprite's position from the updated physics body location
        sprite.setPosition((body.getPosition().x * PPM) - sprite.
                        getWidth()/2 ,
                (body.getPosition().y * PPM) -sprite.getHeight() * 0.36f );

        if (sprite.getY() <= 0 && lives > 0){
            Sound.playFallingSound();
            lives = 0;
        }
    }

    void updateSprite(){
        if (verticalDirection == VerticalDirection.UP){
            if (horizontalDirection == HorizontalDirection.LEFT){
                sprite.setRegion(leftJump);
            }
            else if (horizontalDirection == HorizontalDirection.RIGHT){
                sprite.setRegion(rightJump);
            }
            else {
                sprite.setRegion(up);
            }

        }
        else {
            if (horizontalDirection == HorizontalDirection.LEFT){
                sprite.setRegion(leftFall);
            }
            else if (horizontalDirection == HorizontalDirection.RIGHT){
                sprite.setRegion(rightFall);
            }
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        updatePos();
        checkState();
        updateSprite();

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }

    public void jump(){


        if (verticalDirection != VerticalDirection.UP) {
            verticalDirection = VerticalDirection.UP;
            if (sprite.getY() > Constants.HEIGHT * 0.7){
                body.setLinearVelocity(0f,JUMP_VELOCITY * 0.6f);
            }
            else {
                body.setLinearVelocity(0f,JUMP_VELOCITY);
            }
            PlatformFactory.moveWorld(JUMP_VELOCITY * 0.85f);
            MonsterFactory.moveWorld(JUMP_VELOCITY * 0.85f);
        }

    }

    public void jumpTrampoline() {
        if (verticalDirection != VerticalDirection.UP) {
            verticalDirection = VerticalDirection.UP;
            if (sprite.getY() > Constants.HEIGHT * 0.7){
                body.setLinearVelocity(0f,JUMP_VELOCITY_TRAMPOLINE * 0.6f);
            }
            else {
                body.setLinearVelocity(0f,JUMP_VELOCITY_TRAMPOLINE);
            }
            PlatformFactory.moveWorld(JUMP_VELOCITY_TRAMPOLINE * 0.85f);
            MonsterFactory.moveWorld(JUMP_VELOCITY_TRAMPOLINE * 0.85f);
        }
    }


    void checkState(){
//        if (body.getLinearVelocity().y > 0){
//            verticalDirection = VerticalDirection.UP;
//        }
//        else
            if (verticalDirection != VerticalDirection.DOWN && body.getLinearVelocity().y < 0){
            verticalDirection = VerticalDirection.DOWN;
            PlatformFactory.stopWorld();
            MonsterFactory.stopWorld();
        }
//        else {
//            verticalDirection = VerticalDirection.STILL;
//        }

//        if (horizontalDirection != HorizontalDirection.RIGHT && body.getLinearVelocity().x > 0){
//            horizontalDirection = HorizontalDirection.RIGHT;
//        }
//        else if ( horizontalDirection != HorizontalDirection.LEFT && body.getLinearVelocity().x < 0){
//            horizontalDirection = HorizontalDirection.LEFT;
//        }
//        else {
//            horizontalDirection = HorizontalDirection.STILL;
//        }


    }

    public void moveLeft(){
        body.setLinearVelocity(-HORIZONTAL_VELOCITY, body.getLinearVelocity().y);
        horizontalDirection = HorizontalDirection.LEFT;
    }

    public void moveRight(){
        body.applyForceToCenter(new Vector2(HORIZONTAL_VELOCITY, 0), true);
        body.setLinearVelocity(HORIZONTAL_VELOCITY, body.getLinearVelocity().y);
        horizontalDirection = HorizontalDirection.RIGHT;
    }


    public void stopMovingHorizontally(){
        body.setLinearVelocity(0, body.getLinearVelocity().y);
    }

    public static void incScore(){
        score++;
    }

    public static void decLives(){
        lives--;
    }


    public static int getLives() {
        return lives;
    }

    public static int getScore() {
        return score;
    }



}
