package tk.sebastjanmevlja.doodlejump.Gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import tk.sebastjanmevlja.doodlejump.Gameplay.Monster.MonsterFactory;
import tk.sebastjanmevlja.doodlejump.Gameplay.Platform.PlatformFactory;
import tk.sebastjanmevlja.doodlejump.Helpers.HorizontalDirection;
import tk.sebastjanmevlja.doodlejump.Helpers.VerticalDirection;
import tk.sebastjanmevlja.doodlejump.Level.Level1Screen;

import java.util.ArrayList;

import static tk.sebastjanmevlja.doodlejump.Gameplay.Constants.PPM;


public class Player extends Actor  {


    public static Player player;
    public static int lives;
    public static int score;
    public static ArrayList<Shield> removedShields;

    Sprite sprite;
    World world;
    public Body body;

    VerticalDirection verticalDirection = VerticalDirection.STILL;
    HorizontalDirection horizontalDirection = HorizontalDirection.STILL;

    public static final float JUMP_VELOCITY = Constants.HEIGHT * 0.0038f;
    public static final float JUMP_VELOCITY_TRAMPOLINE = JUMP_VELOCITY * 1.3f;
    public static final float HORIZONTAL_VELOCITY = Constants.WIDTH * 0.005f;
    public static final float WORLD_VELOCITY = Constants.HEIGHT * 0.003f;



//    public static float WIDTH = Constants.WIDTH / 5.3f;
    public static float HEIGHT = Constants.HEIGHT / 11.8f;
    public static float WIDTH = HEIGHT;
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
    private float bodyHeight = 0;
    private float bodyWidth = 0;


    public Player( World world, float x, float y) {
        sprite = new Sprite(up);
        sprite.setSize(WIDTH, HEIGHT);
        sprite.setPosition(x, y);
        sprite.setCenterX(x);
        sprite.setOriginCenter();


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
        bodyWidth = sprite.getWidth() / 4 / PPM;
        bodyHeight = sprite.getHeight() * 0.33f / PPM;
        shape.setAsBox(bodyWidth, bodyHeight);
        // FixtureDef is a confusing expression for physical properties
        // Basically this is where you, in addition to defining the shape of the body
        // you also define it's properties like density, restitution and others
        // Density and area are used to calculate over all mass
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.filter.categoryBits = Constants.PLAYER_BIT;
        fixtureDef.filter.maskBits = Constants.PLATFORM_BIT | Constants.MONSTER_BIT | Constants.TRAMPOLINE_BIT | Constants.SHIELD_BIT;
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
        checkBorderCollision();


    }

    void checkBorderCollision() {
        if (horizontalDirection == HorizontalDirection.RIGHT && sprite.getX() + sprite.getWidth() >= Constants.WIDTH  || horizontalDirection == HorizontalDirection.LEFT && sprite.getX() < 0){
            resolveBorderCollision();
        }
    }

    void resolveBorderCollision(){
        body.setLinearVelocity(0, body.getLinearVelocity().y);
    }






    private void rotate(){
        if (this.rotating){
            sprite.setRotation((sprite.getRotation() + 5) % 360);
            body.setTransform( body.getPosition(),body.getAngle() + (float) Math.toRadians(5) );
            if (sprite.getRotation() == 0){
                this.rotating = false;
                body.setTransform( body.getPosition(), 0);
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


        verticalDirection = VerticalDirection.UP;
//        PlatformFactory.moveWorld(JUMP_VELOCITY);
//        MonsterFactory.moveWorld(JUMP_VELOCITY);
        body.setLinearVelocity(0f,JUMP_VELOCITY);

    }

    public void jumpTrampoline() {
        verticalDirection = VerticalDirection.UP;
//        PlatformFactory.moveWorld(JUMP_VELOCITY_TRAMPOLINE);
//        MonsterFactory.moveWorld(JUMP_VELOCITY_TRAMPOLINE) ;
        body.setLinearVelocity(0f,JUMP_VELOCITY_TRAMPOLINE);
        this.rotating = true;
    }

    public static void moveWorld(float velocity){

        for (Bullet b: bullets) {
            b.body.setLinearVelocity(b.body.getLinearVelocity().x,-velocity * 1.8f);
        }
    }




    void checkState(){

        if (verticalDirection != VerticalDirection.DOWN && body.getLinearVelocity().y < 0){
            verticalDirection = VerticalDirection.DOWN;
        }
        if (sprite.getY() <= Constants.HEIGHT * 0.5f){
            PlatformFactory.stopWorld();
            MonsterFactory.stopWorld();
        }
        else {
            PlatformFactory.moveWorld(WORLD_VELOCITY + player.sprite.getY()  * 0.001f);
            MonsterFactory.moveWorld(WORLD_VELOCITY + player.sprite.getY()  * 0.001f);
        }



    }

    public void moveLeft(){
        body.setLinearVelocity(-HORIZONTAL_VELOCITY, body.getLinearVelocity().y);
        horizontalDirection = HorizontalDirection.LEFT;
    }

    public void moveRight(){
//        body.applyForceToCenter(new Vector2(HORIZONTAL_VELOCITY, 0), true);
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

    public float getBodyHeight() {
        return this.bodyHeight;
    }

    public float getBodyWidth() {
        return this.bodyWidth;
    }
}
