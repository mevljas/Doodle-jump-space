package tk.sebastjanmevlja.doodlejump.Gameplay;

import com.badlogic.gdx.physics.box2d.*;

import static tk.sebastjanmevlja.doodlejump.Gameplay.Constants.PPM;

public class WorldContactListener implements ContactListener {

    private boolean jump;
    private Player player;
    private Platform platform;
    private Monster monster;
    private boolean ignoreCollsion = true;

    public WorldContactListener(Player player) {
        this.player = player;
    }

//    TODO: implement filters
//    https://gamefromscratch.com/libgdx-libgdx-tutorial-13-physics-with-box2d-part-4-controlling-collisions-using-filters/

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        jump = false;
        ignoreCollsion = false;



        if ((fixA.getUserData() instanceof Player && fixB.getUserData() instanceof  Platform) ||
                (fixA.getUserData() instanceof Platform && fixB.getUserData() instanceof  Player)){
            player = (Player) (fixA.getUserData() instanceof Player ? fixA.getUserData() : fixB.getUserData());
            platform = (Platform) (fixA.getUserData() instanceof Platform ? fixA.getUserData() : fixB.getUserData());

            if ((player.getBodyPosition().y + (player.sprite.getHeight() / PPM / 2 ) )  < platform.getBodyPosition().y  ) {
                ignoreCollsion = true;

            }
            else if (player.getBodyPosition().y   > platform.getBodyPosition().y + (platform.sprite.getHeight() / PPM / 2 ) ) {
                jump = true;
//                Break brown platform
                platform.breakPlatform();
                Sound.playJumpSound();
                Player.incScore();

            }
        }
        else if ((fixA.getUserData() instanceof Player && fixB.getUserData() instanceof  Monster) ||
                (fixA.getUserData() instanceof Monster && fixB.getUserData() instanceof  Player)){
            player = (Player) (fixA.getUserData() instanceof Player ? fixA.getUserData() : fixB.getUserData());
            monster = (Monster) (fixA.getUserData() instanceof Monster ? fixA.getUserData() : fixB.getUserData());
            ignoreCollsion = true;
            if (monster.getMonsterType() == MonsterType.UFO){
                monster.sprite.setRegion(MonsterFactory.alienUfoLight);
                monster.movePlayerCloser();
            }
            else {
                Player.decLives();
                Sound.playMonsterSound();
            }

        }




    }

    @Override
    public void endContact(Contact contact) {


    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        if (ignoreCollsion){
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

