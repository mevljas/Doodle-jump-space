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
    public static ArrayList<Jetpack> removedJetpacks;

    Sprite sprite;
    World world;
    public Body body;

    VerticalDirection verticalDirection = VerticalDirection.STILL;
    HorizontalDirection horizontalDirection = HorizontalDirection.STILL;

    public static float JUMP_VELOCITY = Constants.HEIGHT * 0.0027f;
    public static final float HORIZONTAL_VELOCITY = Constants.WIDTH * 0.005f;
    public static final float WORLD_VELOCITY = -Constants.HEIGHT * 0.002f;
    public static final float WORLD_TRAMPOLINE_VELOCITY = WORLD_VELOCITY * 1.5f;
    public static final float WORLD_JETPACK_VELOCITY = WORLD_VELOCITY * 7f;
    public static final float WORLD_JETPACK_VELOCITY_SUBSTRACTION_RATIO = 0.997f;
    public static final float WORLD_JETPACK_VELOCITY_STOP = WORLD_JETPACK_VELOCITY * 0.35f;
    public static final float WORLD_FALL_VELOCITY = -WORLD_VELOCITY * 4;



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
    private Jetpack jetpack;
    private int numberOfShields = 0;
    private boolean imunity = false;
    private float bodyHeight = 0;
    private float bodyWidth = 0;
    private boolean falling = false;
    private Sprite fallingSprite;
    private long shieldExpirationTime = 3000;
    private boolean movePlayerToCenter;


    public Player( World world, float x, float y) {
        sprite = new Sprite(up);
        sprite.setSize(WIDTH, HEIGHT);
        sprite.setPosition(x, y);
        sprite.setCenterX(x);
        sprite.setOriginCenter();


        this.world = world;
        movePlayerToCenter = false;

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
        fixtureDef.filter.maskBits = Constants.PLATFORM_BIT | Constants.MONSTER_BIT | Constants.TRAMPOLINE_BIT | Constants.ITEM_BIT;
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
        removedJetpacks = new ArrayList<>();
        falling = false;

    }

    public Vector2 getBodyPosition(){
        return body.getPosition();
    }






    public void updatePos(){
        // Set the sprite's position from the updated physics body location
        sprite.setPosition((body.getPosition().x * PPM) - sprite.
                        getWidth()/2 ,
                (body.getPosition().y * PPM) -sprite.getHeight() * 0.36f );


        if (sprite.getY() <= - Constants.HEIGHT){
            lives = 0;
        }
        else if (sprite.getY() <= 0 && !falling){
            PlatformFactory.moveWorld(WORLD_FALL_VELOCITY);
            MonsterFactory.moveWorld(WORLD_FALL_VELOCITY);
            Sound.playFallingSound();
            falling = true;
            fallingSprite = new Sprite(up);
            fallingSprite.setSize(WIDTH, HEIGHT);
            fallingSprite.setPosition(sprite.getX(), sprite.getY());
            fallingSprite.setCenterX(sprite.getX());
            fallingSprite.setOriginCenter();
        }

        if (shield != null){
            shield.updatePos(calculateShieldPositionX(shield),calculateShieldPositionY(shield));
        }

        if (jetpack != null){
            jetpack.updatePos(calculateJetpackPositionX(jetpack),calculateJetpackPositionY(jetpack));
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


    float calculateJetpackPositionX(Jetpack jetpack){
        return sprite.getX() + sprite.getWidth() / 2 -  jetpack.sprite.getWidth() / 2;
    }

    float calculateJetpackPositionY(Jetpack jetpack){
        return sprite.getY() + HEIGHT / 2 -  jetpack.sprite.getHeight() * 1.2f;
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
        updateSprite();
        checkBorderCollision();
        if (!falling){
            checkState();
            rotate();
        }



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
        if (jetpack != null){
            jetpack.draw(batch, parentAlpha);
        }
        if (falling){
            fallingSprite.draw(batch);
        }
    }

    public void jump(){
        if (this.jetpack == null){
            verticalDirection = VerticalDirection.UP;
            body.setLinearVelocity(body.getLinearVelocity().x, JUMP_VELOCITY - calculateDynamicVelocity());
            PlatformFactory.moveWorld(WORLD_VELOCITY );
            MonsterFactory.moveWorld(WORLD_VELOCITY);
            Sound.playJumpSound();
            Player.incScore();
        }



    }

    public void jumpTrampoline() {
        if (this.jetpack == null){
            verticalDirection = VerticalDirection.UP;
            body.setLinearVelocity(body.getLinearVelocity().x, JUMP_VELOCITY - calculateDynamicVelocity());
            PlatformFactory.moveWorld(WORLD_TRAMPOLINE_VELOCITY);
            MonsterFactory.moveWorld(WORLD_TRAMPOLINE_VELOCITY);
            this.rotating = true;
            Sound.playJumpSound();
            Player.incScore();
        }

    }


    public void jumpJetpack() {
        verticalDirection = VerticalDirection.UP;
        PlatformFactory.moveWorld(WORLD_JETPACK_VELOCITY);
        MonsterFactory.moveWorld(WORLD_JETPACK_VELOCITY);
        this.movePlayerToCenter = true;


    }

    private void movePlayerScreenCenter(){
        this.body.setTransform(body.getPosition().x, Constants.HEIGHT / 2f / PPM, 0);
    }


    public static void moveWorld(float velocity){

        for (Bullet b: bullets) {
            b.body.setLinearVelocity(b.body.getLinearVelocity().x,-velocity * 1.8f);
        }
    }

    private  float  calculateDynamicVelocity(){
        return sprite.getY() * 0.001f;
    }


    void checkState(){
        if (jetpack == null && verticalDirection != VerticalDirection.DOWN && body.getLinearVelocity().y <= 0){
            verticalDirection = VerticalDirection.DOWN;
            PlatformFactory.stopWorld();
            MonsterFactory.stopWorld();
        }
        else if (jetpack != null){
            decreaseJetpackVelocity();
            if (this.movePlayerToCenter){
                this.movePlayerToCenter = false;
                movePlayerScreenCenter();

            }

        }
//        if (sprite.getY() <= Constants.HEIGHT * 0.5f){
//            PlatformFactory.stopWorld();
//            MonsterFactory.stopWorld();
//        }
//        else{
//            PlatformFactory.moveWorld(WORLD_VELOCITY + player.sprite.getY()  * 0.001f);
//            MonsterFactory.moveWorld(WORLD_VELOCITY + player.sprite.getY()  * 0.001f);
//        }



    }


    private void decreaseJetpackVelocity(){
        if (PlatformFactory.getYVelocity() >= WORLD_JETPACK_VELOCITY_STOP){
            PlatformFactory.stopWorld();
            MonsterFactory.stopWorld();
            removeJetpack();
            verticalDirection = VerticalDirection.DOWN;

        }
        else {
            float velocity = PlatformFactory.getYVelocity() * WORLD_JETPACK_VELOCITY_SUBSTRACTION_RATIO;
            PlatformFactory.moveWorld(velocity);
            MonsterFactory.moveWorld(velocity);
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
        numberOfShields++;

        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        numberOfShields--;
                        if (numberOfShields == 0)
                            removeShield();
                    }
                },
                shieldExpirationTime
        );
    }

    public void equipJetpack( Jetpack jetpack){
        this.jetpack = jetpack;
        jetpack.sprite.setSize(jetpack.sprite.getWidth() * 0.8f, jetpack.sprite.getHeight() * 0.8f);
        body.setGravityScale(0);
        jumpJetpack();
        body.setLinearVelocity(body.getLinearVelocity().x, 0);
        this.imunity = true;


    }

    public void removeShield(){
        if (this.shield != null){
            removedShields.add(shield);
            this.shield = null;
            this.imunity = false;
        }

    }

    public void removeJetpack(){
        body.setGravityScale(1);
        body.setLinearVelocity(body.getLinearVelocity().x, 0);
        body.setAwake(true);
        this.imunity = false;
        if (this.jetpack != null){
            removedJetpacks.add(jetpack);
            this.jetpack = null;
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

        if (jetpack != null) {
            jetpack.incrementGlobalObjectCounter();
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

    public void setLives(int lives) {
        this.lives = lives;
    }

    public Jetpack getJetpack() {
        return this.jetpack;
    }
}
