package tk.sebastjanmevlja.doodlejump.Gameplay;

import com.badlogic.gdx.physics.box2d.*;

import static tk.sebastjanmevlja.doodlejump.Gameplay.Constants.PPM;

public class WorldContactListener implements ContactListener {

    private boolean movingOut;
    private boolean jump;
    private Player player;
    private Platform platform;
    private static int test = 0;
    private boolean ignoreCollsion = true;

    public WorldContactListener(Player player) {
        this.player = player;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        movingOut = false;
        jump = false;
        ignoreCollsion = false;

        Object objA = fixA.getUserData() instanceof Player ? fixA.getUserData() : fixB.getUserData();
        Object objB = fixA.getUserData() instanceof Platform ? fixA.getUserData() : fixB.getUserData();


        if (objA instanceof Player && objB instanceof Platform){
            if (!((Platform) objB).isAlive()){
                ignoreCollsion = true;
            }
            player = (Player) objA;
            platform = (Platform) objB;

            if (player.getBodyPosition().y  < platform.getBodyPosition().y + (platform.sprite.getHeight() / PPM / 2)  ) {
                movingOut = true;
                jump = false;

            }
            else if (player.getBodyPosition().y  > platform.getBodyPosition().y +  (platform.sprite.getHeight() / PPM / 2)   ) {
                movingOut = false;
                jump = true;
//                Break brown platform
                ((Platform) objB).breakPlatform();
                Sound.playJumpSound();
                Player.incScore();

            }
        }
        else if (objA instanceof Player){
            objB = fixA.getUserData() instanceof Monster ? fixA.getUserData() : fixB.getUserData();
            if (objB instanceof Monster){
                Monster m = (Monster) objB;
                ignoreCollsion = true;
                if (m.getMonsterType() == MonsterType.UFO){
                    m.sprite.setRegion(MonsterFactory.alienUfoLight);
                    m.movePlayerCloser();
                }
                else {
                    Player.decLives();
                    Sound.playMonsterSound();
                }
            }

        }




    }

    @Override
    public void endContact(Contact contact) {


    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        if (movingOut || ignoreCollsion){
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

