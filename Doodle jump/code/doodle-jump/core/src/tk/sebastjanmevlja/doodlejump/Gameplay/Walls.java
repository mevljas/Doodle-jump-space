package tk.sebastjanmevlja.doodlejump.Gameplay;

import com.badlogic.gdx.physics.box2d.*;

public class Walls {

    private float width = Constants.WIDTH / Constants.PPM;
    private float height = Constants.HEIGHT/Constants.PPM;



    public Walls(World world) {

        BodyDef bodyDef2 = new BodyDef();
        bodyDef2.type = BodyDef.BodyType.StaticBody;
        bodyDef2.position.set(0,0);
        FixtureDef fixtureDef = new FixtureDef();
        EdgeShape edgeShape = new EdgeShape();
        edgeShape.set(0,0,0,Constants.HEIGHT);
        fixtureDef.shape = edgeShape;
        Body bodyEdgeScreen = world.createBody(bodyDef2);
        bodyEdgeScreen.createFixture(fixtureDef);
        edgeShape.dispose();
    }
}
