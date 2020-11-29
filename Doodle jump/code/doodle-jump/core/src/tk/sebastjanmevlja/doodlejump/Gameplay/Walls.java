package tk.sebastjanmevlja.doodlejump.Gameplay;

import com.badlogic.gdx.physics.box2d.*;

public class Walls {

    private static final float width = Constants.WIDTH / Constants.PPM;
    private static final float height = Constants.HEIGHT/Constants.PPM;



    public Walls(World world) {

//        Left wall
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
//        bodyDef.position.set(0,0);

        FixtureDef fixtureDef = new FixtureDef();
        EdgeShape edgeShape = new EdgeShape();
        edgeShape.set(0,0,0,height);
        fixtureDef.shape = edgeShape;

        Body bodyEdgeScreen = world.createBody(bodyDef);
        bodyEdgeScreen.createFixture(fixtureDef);


        //        Right wall

        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        fixtureDef = new FixtureDef();
        edgeShape = new EdgeShape();
        edgeShape.set(width,width,height,height);
        fixtureDef.shape = edgeShape;

        bodyEdgeScreen = world.createBody(bodyDef);
        bodyEdgeScreen.createFixture(fixtureDef);
    }
}
