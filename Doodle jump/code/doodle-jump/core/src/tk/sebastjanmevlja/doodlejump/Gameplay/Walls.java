package tk.sebastjanmevlja.doodlejump.Gameplay;

import com.badlogic.gdx.physics.box2d.*;

public class Walls {

    private static final float width = Constants.WIDTH / Constants.PPM;
    private static final float height = Constants.HEIGHT/Constants.PPM;





    public Walls(World world) {

//        Left wall
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0,0);

        FixtureDef fixtureDef = new FixtureDef();
        EdgeShape edgeShape = new EdgeShape();
        edgeShape.set(0,0,0,height);
        fixtureDef.shape = edgeShape;


        Body bodyEdgeScreen = world.createBody(bodyDef);
        Fixture f = bodyEdgeScreen.createFixture(fixtureDef);
        f.setUserData(this);
        fixtureDef.filter.categoryBits = Constants.WALLS_BIT;
        fixtureDef.filter.maskBits = Constants.PLAYER_BIT | Constants.MONSTER_BIT | Constants.PLATFORM_BIT;
        fixtureDef.isSensor = true;


        //        Right wall

        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
//        bodyDef.position.set(Constants.WIDTH,0);

        fixtureDef = new FixtureDef();
        edgeShape = new EdgeShape();
        edgeShape.set(width,0,width,height);
        fixtureDef.shape = edgeShape;

        bodyEdgeScreen = world.createBody(bodyDef);
        f = bodyEdgeScreen.createFixture(fixtureDef);
        f.setUserData(this);
        fixtureDef.filter.categoryBits = Constants.WALLS_BIT;
        fixtureDef.filter.maskBits = Constants.PLAYER_BIT | Constants.MONSTER_BIT | Constants.PLATFORM_BIT;
        fixtureDef.isSensor = true;

        //        Bottom wall - sensor

//        bodyDef = new BodyDef();
//        bodyDef.type = BodyDef.BodyType.StaticBody;
////        bodyDef.position.set(Constants.WIDTH,0);
//
//        fixtureDef = new FixtureDef();
//        edgeShape = new EdgeShape();
//        edgeShape.set(0,0,width,0);
//        fixtureDef.shape = edgeShape;
//         object will no longer cause a reaction when collided with, except whatever you provide in code.

//
//        bodyEdgeScreen = world.createBody(bodyDef);
//        f = bodyEdgeScreen.createFixture(fixtureDef);
//        f.setUserData(this);
//        fixtureDef.filter.categoryBits = Constants.WALLS_BIT;
//        fixtureDef.filter.maskBits = Constants.PLAYER_BIT | Constants.MONSTER_BIT;
//        fixtureDef.isSensor = true;



    }
}
