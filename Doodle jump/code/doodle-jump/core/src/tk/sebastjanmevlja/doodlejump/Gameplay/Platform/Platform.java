package tk.sebastjanmevlja.doodlejump.Gameplay.Platform;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import tk.sebastjanmevlja.doodlejump.Gameplay.Constants;
import tk.sebastjanmevlja.doodlejump.Gameplay.Culling;
import tk.sebastjanmevlja.doodlejump.Gameplay.Shield;
import tk.sebastjanmevlja.doodlejump.Gameplay.Trampoline;
import tk.sebastjanmevlja.doodlejump.Level.Level1Screen;

import java.util.Random;

import static tk.sebastjanmevlja.doodlejump.Gameplay.Constants.PPM;





public class Platform extends Actor {

    public Sprite sprite;
    World world;
    Body body;
    public static float PLATFORM_WIDTH = Constants.WIDTH / 5f;
    public static float PLATFORM_HEIGHT = Constants.HEIGHT / 33f;
    boolean broken = false;
    boolean alive = true;
    static Random r = new Random();

    public static final float VELOCITY = PLATFORM_WIDTH * 0.005f;
    Trampoline trampoline;
    Shield shield;



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
        // Basically set the physics polygon to a box with the same dimensions as our sprite
        shape.setAsBox(sprite.getWidth() * 0.4f / PPM, sprite.getHeight()
                /2 / PPM);
        // FixtureDef is a confusing expression for physical properties
        // Basically this is where you, in addition to defining the shape of the body
        // you also define it's properties like density, restitution and others
        // Density and area are used to calculate over all mass
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.filter.categoryBits = Constants.PLATFORM_BIT;
        fixtureDef.filter.maskBits = Constants.PLAYER_BIT;
        fixtureDef.shape = shape;
        fixtureDef.density = 0.1f;
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
        // Shape is the only disposable of the lot, so get rid of it
        shape.dispose();



    }



    float calculateTrampolinePositionX(){
        return sprite.getX() + PLATFORM_WIDTH / 2 -  Trampoline.TRAMPOLINE_WIDTH / 2;
    }

    float calculateTrampolinePositionY(){
        return sprite.getY() + PLATFORM_HEIGHT * 0.7f;
    }

    float calculateShieldPositionX(){
        return sprite.getX() + PLATFORM_WIDTH / 2 -  Shield.SHIELD_WIDTH / 2;
    }

    float calculateShieldPositionY(){
        return sprite.getY() + PLATFORM_HEIGHT * 0.7f;
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

        if (trampoline != null){
            trampoline.updatePos(calculateTrampolinePositionX(),calculateTrampolinePositionY());
        }

        if (shield != null){
            shield.updatePos(calculateShieldPositionX(),calculateShieldPositionY());
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



    public Trampoline getTrampoline() {
        return trampoline;
    }
    public Shield getShield() {
        return shield;
    }


    public void removeShield(){
        this.shield = null;
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

        if (trampoline != null){
            trampoline.incrementGlobalObjectCounter();
        }

        if (shield != null){
            shield.incrementGlobalObjectCounter();
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

    public void resetItems() {
        this.broken = false;
        this.alive = true;

        Trampoline t = this.getTrampoline();
        if (t != null) {
            t.addAction(Actions.removeActor());
            world.destroyBody(t.getBody());
            this.trampoline = null;
        }

        Shield s = this.getShield();
        if (s != null) {
            s.addAction(Actions.removeActor());
            world.destroyBody(s.getBody());
            this.shield = null;
        }

        if (r.nextInt(15) > 12){
            this.trampoline = new Trampoline(calculateTrampolinePositionX(),calculateTrampolinePositionY(),world);
            Level1Screen.backgroundGroup.addActor(this.trampoline);
        }
        else if (r.nextInt(15) > 12){
            this.shield = new Shield(calculateShieldPositionX(),calculateShieldPositionY(),world, this);
            Level1Screen.backgroundGroup.addActor(this.shield);
        }
    }


}
