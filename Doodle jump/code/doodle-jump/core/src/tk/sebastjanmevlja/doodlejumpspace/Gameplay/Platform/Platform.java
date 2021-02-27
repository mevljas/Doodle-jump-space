package tk.sebastjanmevlja.doodlejumpspace.Gameplay.Platform;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import java.util.Random;

import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Constants;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Culling;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Jetpack;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Shield;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Slime;
import tk.sebastjanmevlja.doodlejumpspace.Level.Level1Screen;

import static tk.sebastjanmevlja.doodlejumpspace.Gameplay.Constants.PPM;





public class Platform extends Actor {

    private final float bodyWidth, bodyHeight;
    public Sprite sprite;
    World world;
    Body body;
    public static float PLATFORM_WIDTH = Constants.WIDTH / 5f;
    public static float PLATFORM_HEIGHT = Constants.HEIGHT / 33f;
    boolean broken = false;
    boolean alive = true;
    static Random r = new Random();

    public static final float VELOCITY = PLATFORM_WIDTH * 0.005f;
    Slime slime;
    Shield shield;
    Jetpack jetpack;



    public Platform(TextureAtlas.AtlasRegion texture, World world, float x, float y) {
        this.sprite = new Sprite(texture);
        this.sprite.setSize(PLATFORM_WIDTH, PLATFORM_HEIGHT);
        this.sprite.setPosition(x,y);

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
        bodyWidth = sprite.getWidth() * 0.4f / PPM;
        bodyHeight = sprite.getHeight() /2 / PPM;
        shape.setAsBox(bodyWidth, bodyHeight);
        // Basically set the physics polygon to a box with the same dimensions as our sprite
        // FixtureDef is a confusing expression for physical properties
        // Basically this is where you, in addition to defining the shape of the body
        // you also define it's properties like density, restitution and others
        // Density and area are used to calculate over all mass
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.filter.categoryBits = Constants.PLATFORM_BIT;
        fixtureDef.filter.maskBits = Constants.PLAYER_BIT;
        fixtureDef.shape = shape;
        fixtureDef.density = 0.1f;
        fixtureDef.isSensor = true;
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
        // Shape is the only disposable of the lot, so get rid of it
        shape.dispose();



    }



    float calculateSlimePositionX(){
        return sprite.getX() + PLATFORM_WIDTH / 2 -  Slime.TRAMPOLINE_WIDTH / 2;
    }

    float calculateSlimePositionY(){
        return sprite.getY() + sprite.getHeight() * 0.9f;
    }

    float calculateShieldPositionX(){
        return sprite.getX() + PLATFORM_WIDTH / 2 -  Shield.SHIELD_WIDTH / 2;
    }

    float calculateShieldPositionY(){
        return sprite.getY() + sprite.getHeight() * 0.7f;
    }

    float calculateJetpackPositionX(){
        return sprite.getX() + PLATFORM_WIDTH / 2 -  Jetpack.JETPACK_WIDTH / 2;
    }

    float calculateJetpackPositionY(){
        return sprite.getY() + sprite.getHeight();
    }



    @Override
    public void act(float delta) {
        super.act(delta);
        updatePos();
    }




    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch,parentAlpha);
    }

    public void updatePos(){
        // Set the sprite's position from the updated physics body location
        sprite.setPosition((body.getPosition().x * PPM) - sprite.
                        getWidth()/2 ,
                (body.getPosition().y * PPM) -sprite.getHeight()/2 );

        if (slime != null){
            slime.updatePos(calculateSlimePositionX(), calculateSlimePositionY());
        }

        if (shield != null){
            shield.updatePos(calculateShieldPositionX(),calculateShieldPositionY());
        }

        if (jetpack != null){
            jetpack.updatePos(calculateJetpackPositionX(),calculateJetpackPositionY());
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



    public Slime getSlime() {
        return slime;
    }
    public Shield getShield() {
        return shield;
    }

    public Jetpack getJetpack() {
        return jetpack;
    }


    public void removeShield(){
        this.shield = null;
    }
    public void removeJetpack(){
        this.jetpack.parentPlatform = null;
        this.jetpack = null;
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
        if (shield != null){
            shield.incrementGlobalObjectCounter();
        }

        if (slime != null){
            slime.incrementGlobalObjectCounter();
        }


        if (jetpack != null){
            jetpack.incrementGlobalObjectCounter();
        }
    }



    public void changeTexture(TextureAtlas.AtlasRegion atlasRegion) {
        sprite = new Sprite(atlasRegion);
    }

    public void changePosition(float x, float y) {
        sprite.setSize(PLATFORM_WIDTH, PLATFORM_HEIGHT);
        sprite.setPosition(x,y);
        body.setTransform((sprite.getX() + sprite.getWidth()/2) /
                        PPM,
                (sprite.getY() + sprite.getHeight()/2) / PPM,0);
    }


    public void reEnable(){
        this.broken = false;
        this.alive = true;
    }

    public void resetItems() {
        Slime t = this.getSlime();
        if (t != null) {
            t.addAction(Actions.removeActor());
            world.destroyBody(t.getBody());
            this.slime = null;
        }

        Shield s = this.getShield();
        if (s != null) {
            s.addAction(Actions.removeActor());
            world.destroyBody(s.getBody());
            this.shield = null;
        }

        Jetpack j = this.getJetpack();
        if (j != null) {
            j.addAction(Actions.removeActor());
            world.destroyBody(j.getBody());
            this.jetpack = null;
        }

        generateItems();
    }

    void generateItems(){
        if (r.nextInt(40) == 1){
            this.slime = new Slime(calculateSlimePositionX(),calculateSlimePositionY(),world);
            Level1Screen.backgroundGroup.addActor(this.slime);
        }
        else if (r.nextInt(40) == 20){
            this.shield = new Shield(calculateShieldPositionX(),calculateShieldPositionY(),world, this);
            Level1Screen.backgroundGroup.addActor(this.shield);
        }
        else if (r.nextInt(40) == 39){
            this.jetpack = new Jetpack(calculateJetpackPositionX(),calculateJetpackPositionY(),world, this);
            Level1Screen.backgroundGroup.addActor(this.jetpack);
        }
    }


    public float getBodyHeight() {
        return this.bodyHeight;
    }
}
