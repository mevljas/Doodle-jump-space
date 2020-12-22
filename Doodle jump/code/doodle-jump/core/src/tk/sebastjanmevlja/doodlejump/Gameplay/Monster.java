package tk.sebastjanmevlja.doodlejump.Gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

import static tk.sebastjanmevlja.doodlejump.Gameplay.Constants.PPM;


public class Monster extends Actor {

    Sprite sprite;
    World world;
    Body body;
    PlatformColor platformColor;
    public static float WIDTH = Constants.WIDTH / 5f;
    public static float HEIGHT = Constants.HEIGHT / 8f;
//    private boolean broken = false;
    public Animation<TextureRegion> runningAnimation;
    private boolean alive = true;
    private MonsterType monsterType;

    // A variable for tracking elapsed time for the animation
    float stateTime;

//    Direction direction = Direction.STILL;

    public static final float VELOCITY = 0.3f;

    public MonsterType getMonsterType() {
        return monsterType;
    }

    public Monster(MonsterType monsterType, Array<TextureAtlas.AtlasRegion> textures, World world, float x, float y) {
        sprite = new Sprite(textures.get(0));

        if (monsterType == MonsterType.BLUE){
            WIDTH = Constants.WIDTH / 5f;
            HEIGHT = Constants.HEIGHT / 8f;
        }
        else {
            WIDTH = Constants.WIDTH / 6f;
            HEIGHT = Constants.HEIGHT / 8f;
        }

        runningAnimation = new Animation<TextureRegion>(0.08f, textures, Animation.PlayMode.LOOP);
        this.stateTime = 0f;


        init(monsterType, world, x, y);

        body.setLinearVelocity(VELOCITY, 0);


    }



    public Monster(MonsterType monsterType, TextureAtlas.AtlasRegion texture, World world, float x, float y) {
        sprite = new Sprite(texture);

        if (monsterType == MonsterType.RED) {
            WIDTH = Constants.WIDTH / 8f;
            HEIGHT = WIDTH;
        }
        else if (monsterType == MonsterType.UFO) {
            WIDTH = Constants.WIDTH / 3f;
            HEIGHT = Constants.HEIGHT / 4f;
        }


        init(monsterType, world, x, y);
    }


    private void init(MonsterType monsterType, World world, float x, float y) {
        sprite.setSize(WIDTH, HEIGHT);
        sprite.setPosition(x,y);
        sprite.setCenterX(x);
        this.monsterType = monsterType;

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
        shape.setAsBox(sprite.getWidth() /2 / PPM, sprite.getHeight()
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
        checkPlayerDistance();
        updatePos();
        checkWallColision();
        updateAnimations();
    }

    private void checkWallColision() {
        if (sprite.getX() + spriteWidth() >= Constants.WIDTH || sprite.getX() < 0) {
            changeDirection();
        }
    }

    private void checkPlayerDistance(){
        if (monsterType != MonsterType.RED && body.getLinearVelocity().y == 0 && getPlayerDistance() < 7){
            moveTowardsPlayer();
        }
    }

    private void moveTowardsPlayer(){
        Vector2 vectDirection = Player.player.body.getPosition().sub(body.getPosition()).nor().scl(2);
        body.setLinearVelocity(vectDirection);
    }


    void movePlayerCloser(){
        Vector2 vectDirection = body.getPosition().sub(Player.player.body.getPosition()).nor().scl(2.5f);
        Player.player.body.setLinearVelocity(vectDirection);
    }

    double getPlayerDistance(){
        return Math.sqrt(Math.pow((body.getPosition().x - Player.player.body.getPosition().x), 2) + Math.pow((body.getPosition().y - Player.player.body.getPosition().y), 2));
    }


    private void updateAnimations(){
        if (monsterType == MonsterType.GREEN || monsterType == MonsterType.BLUE){
            this.stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (monsterType == MonsterType.GREEN || monsterType == MonsterType.BLUE){
            TextureRegion currentFrame = runningAnimation.getKeyFrame(stateTime, false);
            batch.draw(currentFrame, sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());


        }
        else {
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
}
