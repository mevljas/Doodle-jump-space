package tk.sebastjanmevlja.doodlejump.Gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import tk.sebastjanmevlja.doodlejump.Level.Level1Screen;

import java.util.ArrayList;

import static tk.sebastjanmevlja.doodlejump.Gameplay.Constants.PPM;




enum VerticalDirection {
    UP,DOWN, STILL
}


enum HorizontalDirection {
    LEFT, RIGHT, STILL
}


public class Player extends Actor  {

    static Player player;
    public static int lives;
    public static int score;
    public static ArrayList<Shield> removedShields;

    Sprite sprite;
    World world;
    Body body;

    VerticalDirection verticalDirection = VerticalDirection.STILL;
    HorizontalDirection horizontalDirection = HorizontalDirection.STILL;

    public static final float JUMP_VELOCITY = Constants.HEIGHT * 0.0034f;
    public static final float JUMP_VELOCITY_TRAMPOLINE = Constants.HEIGHT * 0.005f;
    public static final float HORIZONTAL_VELOCITY = Constants.WIDTH * 0.005f;



    public static float WIDTH = Constants.WIDTH / 3.5f;
    public static float HEIGHT = Constants.HEIGHT / 8f;
    private static float accelerometerSensitivity = 0.8f;

    private static TextureAtlas.AtlasRegion up = Asset.atlas.findRegion("player_up");
    private static TextureAtlas.AtlasRegion leftFall = Asset.atlas.findRegion("player_left_jump");
    private static TextureAtlas.AtlasRegion rightFall = Asset.atlas.findRegion("player_right_jump");
    private static TextureAtlas.AtlasRegion leftJump = Asset.atlas.findRegion("player_left");
    private static TextureAtlas.AtlasRegion rightJump = Asset.atlas.findRegion("player_right");
    private boolean rotating = false;
    public static ArrayList<Bullet> bullets;
    private Shield shield;
    private boolean imunity = false;


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
        fixtureDef.filter.maskBits = Constants.PLATFORM_BIT | Constants.MONSTER_BIT | Constants.WALLS_BIT | Constants.TRAMPOLINE_BIT | Constants.SHIELD_BIT;
        fixtureDef.shape = shape;
        fixtureDef.density = 0.1f;
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
        // Shape is the only disposable of the lot, so get rid of it
        shape.dispose();
        player = this;
        lives = 5;
        score = 0;

        bullets = new ArrayList<>();
        removedShields = new ArrayList<>();

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

        if (shield != null){
            shield.updatePos(calculateShieldPositionX(shield),calculateShieldPositionY(shield));
        }
    }

    private void checkAccelerometer(){
        float accelX = Gdx.input.getAccelerometerX();
        if (accelX > accelerometerSensitivity){
            this.moveLeft();
        }
        else if (accelX < -accelerometerSensitivity){
            this.moveRight();
        }
    }

    float calculateShieldPositionX(Shield shield){
        return sprite.getX() + sprite.getWidth() / 2 -  shield.sprite.getWidth() / 2;
    }

    float calculateShieldPositionY(Shield shield){
        return sprite.getY() + HEIGHT / 2 -  shield.sprite.getHeight() * 0.6f;
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
        checkAccelerometer();
        updatePos();
        checkState();
        updateSprite();
        rotate();
//        updateBullets(delta);


    }


//    private void updateBullets(float delta){
//        for (Bullet b: bullets) {
//            b.updatePos();
////            b.act(delta);
//        }
//    }
//
//    private void drawBullets(Batch batch, float parentAlpha){
//        for (Bullet b: bullets) {
//            b.draw(batch, parentAlpha);
//        }
//    }

    private void rotate(){
        if (this.rotating){
            sprite.setRotation((sprite.getRotation() + 5) % 360);
            if (sprite.getRotation() == 0){
                this.rotating = false;
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
//        drawBullets(batch, parentAlpha);
        if (shield != null){
            shield.draw(batch, parentAlpha);
        }
    }

    public void jump(){


        if (verticalDirection != VerticalDirection.UP) {
            verticalDirection = VerticalDirection.UP;
            if (sprite.getY() > Constants.HEIGHT * 0.7){
                PlatformFactory.moveWorld(JUMP_VELOCITY * 1.5f );
                MonsterFactory.moveWorld(JUMP_VELOCITY * 1.5f);
            }
            else {
                PlatformFactory.moveWorld(JUMP_VELOCITY * 0.8f);
                MonsterFactory.moveWorld(JUMP_VELOCITY * 0.8f);
            }
            body.setLinearVelocity(0f,JUMP_VELOCITY);
        }

    }

    public void jumpTrampoline() {
        if (verticalDirection != VerticalDirection.UP) {
            verticalDirection = VerticalDirection.UP;
            if (sprite.getY() > Constants.HEIGHT * 0.7){
                PlatformFactory.moveWorld(JUMP_VELOCITY_TRAMPOLINE * 1.5f ) ;
                MonsterFactory.moveWorld(JUMP_VELOCITY_TRAMPOLINE * 1.5f );
            }
            else {
                PlatformFactory.moveWorld(JUMP_VELOCITY_TRAMPOLINE * 0.8f);
                MonsterFactory.moveWorld(JUMP_VELOCITY_TRAMPOLINE * 0.8f) ;
            }
            body.setLinearVelocity(0f,JUMP_VELOCITY_TRAMPOLINE);
            this.rotating = true;
        }
    }

    public static void moveWorld(float velocity){

        for (Bullet b: bullets) {
            b.body.setLinearVelocity(b.body.getLinearVelocity().x,-velocity * 1.8f);
        }
    }




    void checkState(){

        if (verticalDirection != VerticalDirection.DOWN && body.getLinearVelocity().y < 0){
            verticalDirection = VerticalDirection.DOWN;
            PlatformFactory.stopWorld();
            MonsterFactory.stopWorld();
        }



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

    public void createBullet(int xt, int yt){
        Bullet b = new Bullet(this.sprite.getX() +  WIDTH / 2, this.sprite.getY() +  HEIGHT / 2, world,  xt,  Constants.HEIGHT - yt);
        Level1Screen.middleGroup.addActor(b);
        bullets.add(b);
    }

    public void removeBullet(Bullet bullet){
        bullets.remove(bullet);
    }

    public void equipShield( Shield shield){
        this.shield = shield;
        shield.sprite.setSize(shield.sprite.getWidth() * 2f, shield.sprite.getHeight() * 2f);
        this.imunity = true;

        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        removeShield();
                    }
                },
                2000
        );
    }

    public void removeShield(){
        if (this.shield != null){
            removedShields.add(shield);
//            this.shield.addAction(Actions.removeActor());
            this.shield = null;
            this.imunity = false;
        }

    }


    public Shield getshield() {
        return this.shield;
    }

    public boolean isImunity() {
        return imunity;
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

    public void incrementGlobalObjectCounter() {
        Culling.incrementObjectsCounter();
        if (shield != null) {
            shield.incrementGlobalObjectCounter();
        }

        for (Bullet b : bullets) {
            b.incrementGlobalObjectCounter();

        }

    }
}
