package tk.sebastjanmevlja.doodlejump.Gameplay;

import com.badlogic.gdx.physics.box2d.*;

import static tk.sebastjanmevlja.doodlejump.Gameplay.Constants.PPM;

public class WorldContactListener implements ContactListener {

    private boolean jump;
    private boolean jumpTrampoline;
    private Player player;
    private Platform platform;
    private Monster monster;
    private Bullet bullet;
    private Trampoline trampoline;
    private Shield shield;
    private boolean ignoreCollsion = true;

    public WorldContactListener(Player player) {
        this.player = player;
    }


    @Override
    public void beginContact(Contact contact) {
;
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        jump = false;
        ignoreCollsion = false;
        jumpTrampoline = false;


        if ((fixA.getUserData() instanceof Player && fixB.getUserData() instanceof  Platform) ||
                (fixA.getUserData() instanceof Platform && fixB.getUserData() instanceof  Player)){
            player = (Player) (fixA.getUserData() instanceof Player ? fixA.getUserData() : fixB.getUserData());
            platform = (Platform) (fixA.getUserData() instanceof Platform ? fixA.getUserData() : fixB.getUserData());

            if (player.getBodyPosition().y  < platform.getBodyPosition().y  ) {
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
        else if ((fixA.getUserData() instanceof Player && fixB.getUserData() instanceof  Trampoline) ||
                (fixA.getUserData() instanceof Trampoline && fixB.getUserData() instanceof  Player)){
            player = (Player) (fixA.getUserData() instanceof Player ? fixA.getUserData() : fixB.getUserData());
            trampoline = (Trampoline) (fixA.getUserData() instanceof Trampoline ? fixA.getUserData() : fixB.getUserData());

            if (player.getBodyPosition().y   < trampoline.getBodyPosition().y  ) {
                ignoreCollsion = true;

            }
            else if (player.getBodyPosition().y   > trampoline.getBodyPosition().y + (trampoline.sprite.getHeight() / PPM / 2 ) ) {
                jumpTrampoline = true;
                Sound.playJumpSound();
                Player.incScore();

            }
        }
        else if ((fixA.getUserData() instanceof Player && fixB.getUserData() instanceof  Monster) ||
                (fixA.getUserData() instanceof Monster && fixB.getUserData() instanceof  Player)){
            player = (Player) (fixA.getUserData() instanceof Player ? fixA.getUserData() : fixB.getUserData());
            monster = (Monster) (fixA.getUserData() instanceof Monster ? fixA.getUserData() : fixB.getUserData());
            ignoreCollsion = true;
            if (player.isImunity()){
                return;
            }
            if (monster.getMonsterType() == MonsterType.UFO){
                monster.sprite.setRegion(MonsterFactory.alienUfoLight);
                monster.movePlayerCloser();
            }
            else {
                Player.decLives();
                Sound.playMonsterSound();
            }

        }

        else if ((fixA.getUserData() instanceof Bullet && fixB.getUserData() instanceof  Monster) ||
                (fixA.getUserData() instanceof Monster && fixB.getUserData() instanceof  Bullet)){
            bullet = (Bullet) (fixA.getUserData() instanceof Bullet ? fixA.getUserData() : fixB.getUserData());
            monster = (Monster) (fixA.getUserData() instanceof Monster ? fixA.getUserData() : fixB.getUserData());
            monster.kill();
            bullet.deactivate();

        }

        else if ((fixA.getUserData() instanceof Player && fixB.getUserData() instanceof  Shield) ||
                (fixA.getUserData() instanceof Shield && fixB.getUserData() instanceof  Player)){
            ignoreCollsion = true;
            shield = (Shield) (fixA.getUserData() instanceof Shield ? fixA.getUserData() : fixB.getUserData());
            player = (Player) (fixA.getUserData() instanceof Player ? fixA.getUserData() : fixB.getUserData());
            if (shield == player.getshield()){
                return;
            }
            player.removeShield();
            player.equipShield(shield);
            shield.parentPlatform.removeShield();

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
        else if (jumpTrampoline){
            trampoline.playerJump();
            player.jumpTrampoline();
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}

