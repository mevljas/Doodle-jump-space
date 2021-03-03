package tk.sebastjanmevlja.doodlejumpspace.Gameplay;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Monster.BlackHole;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Monster.Enemy;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Monster.MagnetoEnemy;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Platform.BrokenPlatform;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Platform.Platform;

public class WorldContactListener implements ContactListener {

    private Player player;
    private Platform platform;
    private Enemy enemy;
    private Bullet bullet;
    private Slime slime;
    private Shield shield;
    private Jetpack jetpack;

    public WorldContactListener(Player player) {
        this.player = player;
    }


    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();



        if ((fixA.getUserData() instanceof Player && fixB.getUserData() instanceof  Platform) ||
                (fixA.getUserData() instanceof Platform && fixB.getUserData() instanceof  Player)){
            player = (Player) (fixA.getUserData() instanceof Player ? fixA.getUserData() : fixB.getUserData());
            platform = (Platform) (fixA.getUserData() instanceof Platform ? fixA.getUserData() : fixB.getUserData());

            if (player.getJetpack() != null){
                return;
            }

            if (player.getBodyPosition().y  > platform.getBodyPosition().y  + platform.getBodyHeight()) {
//                Break brown platform
                if (platform instanceof BrokenPlatform){
                    ( (BrokenPlatform) platform).breakPlatform();
                }
                else {
//              Jump off a platform
                    player.jump();
                }

            }

        }
        else if ((fixA.getUserData() instanceof Player && fixB.getUserData() instanceof Slime) ||
                (fixA.getUserData() instanceof Slime && fixB.getUserData() instanceof  Player)){
            player = (Player) (fixA.getUserData() instanceof Player ? fixA.getUserData() : fixB.getUserData());
            slime = (Slime) (fixA.getUserData() instanceof Slime ? fixA.getUserData() : fixB.getUserData());

            if (player.getJetpack() != null){
                return;
            }

            if (player.getBodyPosition().y  >= slime.getBodyPosition().y + slime.getBodyHeight()   ) {
                jumpOffTrampoline();

            }
        }
        else if ((fixA.getUserData() instanceof Player && fixB.getUserData() instanceof Enemy) ||
                (fixA.getUserData() instanceof Enemy && fixB.getUserData() instanceof  Player)){
            player = (Player) (fixA.getUserData() instanceof Player ? fixA.getUserData() : fixB.getUserData());
            enemy = (Enemy) (fixA.getUserData() instanceof Enemy ? fixA.getUserData() : fixB.getUserData());

            if (player.getJetpack() != null){
                return;
            }

            if (player.isImunity()){
                return;
            }
            if (enemy instanceof MagnetoEnemy){
                ((MagnetoEnemy) enemy).enableZone();
                ((MagnetoEnemy) enemy).movePlayerCloser();
            }
            else if (enemy instanceof BlackHole){
//                Sound.playBlackHoleSound();
                player.die();
            }
            else {
                Player.decLives();
                Sound.playMonsterSound();
            }

        }

        else if ((fixA.getUserData() instanceof Bullet && fixB.getUserData() instanceof Enemy) ||
                (fixA.getUserData() instanceof Enemy && fixB.getUserData() instanceof  Bullet)){
            bullet = (Bullet) (fixA.getUserData() instanceof Bullet ? fixA.getUserData() : fixB.getUserData());
            enemy = (Enemy) (fixA.getUserData() instanceof Enemy ? fixA.getUserData() : fixB.getUserData());
            enemy.kill();
            bullet.deactivate();
            if (enemy instanceof MagnetoEnemy){
                Sound.stopMagnetoSound();
            }

        }

        else if ((fixA.getUserData() instanceof Player && fixB.getUserData() instanceof  Shield) ||
                (fixA.getUserData() instanceof Shield && fixB.getUserData() instanceof  Player)){


            shield = (Shield) (fixA.getUserData() instanceof Shield ? fixA.getUserData() : fixB.getUserData());
            player = (Player) (fixA.getUserData() instanceof Player ? fixA.getUserData() : fixB.getUserData());

            if (player.getJetpack() != null){
                return;
            }

            if (shield == player.getshield()){
                return;
            }
            player.removeShield();
            player.equipShield(shield);
            shield.parentPlatform.removeShield();

        }


        else if ((fixA.getUserData() instanceof Player && fixB.getUserData() instanceof  Jetpack) ||
                (fixA.getUserData() instanceof Jetpack && fixB.getUserData() instanceof  Player)){


            jetpack = (Jetpack) (fixA.getUserData() instanceof Jetpack ? fixA.getUserData() : fixB.getUserData());
            player = (Player) (fixA.getUserData() instanceof Player ? fixA.getUserData() : fixB.getUserData());
            if (player.getJetpack() != null){
                return;
            }
//            player.removeJetpack();
            player.equipJetpack(jetpack);
            jetpack.parentPlatform.removeJetpack();

        }




    }

    @Override
    public void endContact(Contact contact) {


    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        contact.setEnabled(false);

    }


    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    private void jumpOffTrampoline(){
        slime.playerJump();
        player.jumpTrampoline();
    }
}

