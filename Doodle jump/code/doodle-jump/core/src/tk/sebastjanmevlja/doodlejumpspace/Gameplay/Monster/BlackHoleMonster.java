package tk.sebastjanmevlja.doodlejumpspace.Gameplay.Monster;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Asset;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Constants;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Player;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Sound;
import tk.sebastjanmevlja.doodlejumpspace.Level.Level1Screen;

import static tk.sebastjanmevlja.doodlejumpspace.Gameplay.Constants.PPM;


public class BlackHoleMonster extends Monster {


    private  static final TextureAtlas.AtlasRegion texture = Asset.atlas.findRegion("hole");

    private static float playerMovingScale = Constants.HEIGHT * 0.0045f;
    private static float playerDetectingRange = Constants.HEIGHT * 0.0008f;




    public BlackHoleMonster( World world, float x, float y) {
        super(texture, world, x, y);
        HEIGHT = Constants.HEIGHT * 0.1f;
        WIDTH = HEIGHT;
        init( world, x, y);
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
        fixtureDef.filter.maskBits = Constants.PLAYER_BIT;
        fixtureDef.shape = shape;
        fixtureDef.density = 0.1f;
        fixtureDef.isSensor = true;
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
        // Shape is the only disposable of the lot, so get rid of it
        shape.dispose();

    }






    @Override
    public void act(float delta) {
        super.act(delta);

    }


    public void movePlayerCloser(){
        if (getPlayerDistance() < playerDetectingRange){
            Player.player.setLives(0);
            Sound.playFallingSound();
            Level1Screen.gameOver();
        }
        Vector2 vectDirection = (body.getPosition()).sub(Player.player.body.getPosition()).nor().scl(playerMovingScale);
        Player.player.body.setLinearVelocity(vectDirection);
    }

    double getPlayerDistance(){
        return Math.sqrt(Math.pow((body.getPosition().x - Player.player.body.getPosition().x), 2) + Math.pow((body.getPosition().y - Player.player.body.getPosition().y), 2));
    }




    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);


    }


}
