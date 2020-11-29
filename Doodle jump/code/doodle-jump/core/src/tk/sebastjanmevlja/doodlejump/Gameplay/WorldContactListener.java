package tk.sebastjanmevlja.doodlejump.Gameplay;

import com.badlogic.gdx.physics.box2d.*;

import static tk.sebastjanmevlja.doodlejump.Gameplay.Constants.PPM;

public class WorldContactListener implements ContactListener {

    private boolean movingOut;
    private boolean jump;
    private Player player;
    private Platform platform;
    private static int test = 0;

    public WorldContactListener(Player player) {
        this.player = player;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        movingOut = false;
        jump = false;

        Object objA = fixA.getUserData() instanceof Player ? fixA.getUserData() : fixB.getUserData();
        Object objB = fixA.getUserData() instanceof Platform ? fixA.getUserData() : fixB.getUserData();


        if (objA instanceof Player && objB instanceof Platform){
            player = (Player) objA;
            platform = (Platform) objB;
            if (player.getBodyPosition().y < platform.getBodyPosition().y) {
                movingOut = true;
                jump = false;
            }
            else if ((player.getBodyPosition().y + (player.sprite.getHeight() / PPM) ) > platform.getBodyPosition().y) {
                movingOut = false;
                jump = true;

            }
        }



    }

    @Override
    public void endContact(Contact contact) {


    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        if (movingOut){
            contact.setEnabled(false);
        }
        else if (jump){
            player.jump();
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}

