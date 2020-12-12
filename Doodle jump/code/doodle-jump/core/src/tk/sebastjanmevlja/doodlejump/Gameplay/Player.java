package tk.sebastjanmevlja.doodlejump.Gameplay;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static tk.sebastjanmevlja.doodlejump.Gameplay.Constants.PPM;
import static tk.sebastjanmevlja.doodlejump.Gameplay.PlatformFactory.moveWorld;
import static tk.sebastjanmevlja.doodlejump.Gameplay.PlatformFactory.stopWorld;


enum Direction {
    UP,DOWN, STILL
}


public class Player extends Actor {

    Sprite sprite;
    World world;
    Body body;

    Direction direction = Direction.STILL;

    public static final float JUMP_VELOCITY = 4f;

    public static float WIDTH = Constants.WIDTH / 3.5f;
    public static float HEIGHT = Constants.HEIGHT / 8f;


    public Player(TextureAtlas.AtlasRegion texture, World world, float x, float y) {
        sprite = new Sprite(texture);
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
        shape.setAsBox(sprite.getWidth() / 3 / PPM, sprite.getHeight()
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

    public Vector2 getBodyPosition(){
        return body.getPosition();
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
        updatePos();
        checkState();

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }

    public void jump(){


        if (direction != Direction.UP) {
            direction = Direction.UP;
            if (sprite.getY() > Constants.HEIGHT * 0.7){
                body.setLinearVelocity(0f,JUMP_VELOCITY * 0.7f);
            }
            else {
                body.setLinearVelocity(0f,JUMP_VELOCITY);
            }
            moveWorld(JUMP_VELOCITY * 0.7f);
        }

    }


    void checkState(){
        if (body.getLinearVelocity().y > 0){
            direction = Direction.UP;
        }
        else{
            direction = Direction.DOWN;
            stopWorld();
        }


    }







}
