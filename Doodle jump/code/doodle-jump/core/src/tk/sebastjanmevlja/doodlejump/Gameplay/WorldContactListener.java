package tk.sebastjanmevlja.doodlejump.Gameplay;

import com.badlogic.gdx.physics.box2d.*;
import tk.sebastjanmevlja.doodlejump.Gameplay.Monster.BlackHoleMonster;
import tk.sebastjanmevlja.doodlejump.Gameplay.Monster.Monster;
import tk.sebastjanmevlja.doodlejump.Gameplay.Monster.UfoMonster;
import tk.sebastjanmevlja.doodlejump.Gameplay.Platform.BrownPlatform;
import tk.sebastjanmevlja.doodlejump.Gameplay.Platform.Platform;

public class WorldContactListener implements ContactListener {

    private Player player;
    private Platform platform;
    private Monster monster;
    private Bullet bullet;
    private Trampoline trampoline;
    private Shield shield;
    private boolean blackHoleMonster = false;

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

            if (player.getBodyPosition().y  > platform.getBodyPosition().y  + platform.getBodyHeight()) {
//                Break brown platform
                if (platform instanceof  BrownPlatform){
                    ( (BrownPlatform) platform).breakPlatform();
                }
                else {
//              Jump off a platform
                    player.jump();
                    Sound.playJumpSound();
                    Player.incScore();
                }

            }

        }
        else if ((fixA.getUserData() instanceof Player && fixB.getUserData() instanceof  Trampoline) ||
                (fixA.getUserData() instanceof Trampoline && fixB.getUserData() instanceof  Player)){
            player = (Player) (fixA.getUserData() instanceof Player ? fixA.getUserData() : fixB.getUserData());
            trampoline = (Trampoline) (fixA.getUserData() instanceof Trampoline ? fixA.getUserData() : fixB.getUserData());

            if (player.getBodyPosition().y  >= trampoline.getBodyPosition().y + trampoline.getBodyHeight()   ) {
                jumpOffTrampoline();

            }
        }
        else if ((fixA.getUserData() instanceof Player && fixB.getUserData() instanceof  Monster) ||
                (fixA.getUserData() instanceof Monster && fixB.getUserData() instanceof  Player)){
            player = (Player) (fixA.getUserData() instanceof Player ? fixA.getUserData() : fixB.getUserData());
            monster = (Monster) (fixA.getUserData() instanceof Monster ? fixA.getUserData() : fixB.getUserData());

            if (player.isImunity()){
                return;
            }
            if (monster instanceof UfoMonster){
                ((UfoMonster) monster).showLight();
                ((UfoMonster) monster).movePlayerCloser();
            }
            else if (monster instanceof BlackHoleMonster){
                blackHoleMonster = true;
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
        contact.setEnabled(false);
        if (blackHoleMonster){
            ((BlackHoleMonster) monster).movePlayerCloser();
        }

    }


    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    private void jumpOffTrampoline(){
        trampoline.playerJump();
        player.jumpTrampoline();
        Sound.playJumpSound();
        Player.incScore();
    }
}

