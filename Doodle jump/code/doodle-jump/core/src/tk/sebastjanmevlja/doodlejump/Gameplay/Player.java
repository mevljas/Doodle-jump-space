package tk.sebastjanmevlja.doodlejump.Gameplay;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;



public class Player extends Actor {

    Sprite sprite;
    World world;
    Body body;


    public Player(TextureAtlas.AtlasRegion texture, World world) {
        sprite = new Sprite(texture);
        sprite.setSize(GameInfo.WIDTH / 3.5f, GameInfo.HEIGHT / 8f);
        sprite.setPosition(0,GameInfo.HEIGHT / 4f);
        sprite.setCenterX(GameInfo.WIDTH / 2f);

        this.world = world;

        // Now create a BodyDefinition.  This defines the physics objects type and position in the simulation
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        // We are going to use 1 to 1 dimensions.  Meaning 1 in physics engine  is 1 pixel
        // Set our body to the same position as our sprite
        bodyDef.position.set(sprite.getX() + sprite.getWidth() / 2f , sprite.getY() + sprite.getHeight() / 2f);



        // Create a body in the world using our definition
        body = world.createBody(bodyDef);


        // Now define the dimensions of the physics shape
        PolygonShape shape = new PolygonShape();
        // Basically set the physics polygon to a box with the same dimensions as our sprite
        shape.setAsBox(sprite.getWidth() / 2, sprite.getHeight() / 2);
        // FixtureDef is a confusing expression for physical properties
        // Basically this is where you, in addition to defining the shape of the body
        // you also define it's properties like density, restitution and others
        // Density and area are used to calculate over all mass
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        Fixture fixture = body.createFixture(fixtureDef);
        // Shape is the only disposable of the lot, so get rid of it
        shape.dispose();

    }


    public void spritePos(float x, float y){
        sprite.setPosition(x, y);
    }

    public void updatePos(){
        sprite.setPosition(body.getPosition().x - sprite.getWidth()/2f,
                body.getPosition().y - sprite.getHeight()/2f );
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        updatePos();

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }

    public void moveUp(){
        body.applyForceToCenter(0f,1000000000000000f,true);
    }





}
