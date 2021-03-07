package tk.sebastjanmevlja.doodlejumpspace.Gameplay.Enemies;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import tk.sebastjanmevlja.doodlejumpspace.Helpers.Assets;
import tk.sebastjanmevlja.doodlejumpspace.Helpers.Constants;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Player;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Sound;
import tk.sebastjanmevlja.doodlejumpspace.Helpers.HorizontalDirection;


public class MagnetoEnemy extends Enemy {


    public static final TextureAtlas.AtlasRegion zoneTexture = Assets.atlas.findRegion("magneto_zone");
    private static final float playerMovingScale = Constants.HEIGHT * 0.003f;
    private static final float monsterMovingScale = Constants.HEIGHT * 0.002f;
    private static final float playerDetectingRange = Constants.HEIGHT * 0.0048f;
    private static final TextureAtlas.AtlasRegion texture = Assets.atlas.findRegion("magneto");
    private boolean zone;


    public MagnetoEnemy(World world, float x, float y) {
        super(texture, world, x, y);
        HEIGHT = Constants.HEIGHT * 0.1f;
        //noinspection SuspiciousNameCombination
        WIDTH = HEIGHT;
        init(world, x, y);
        zone = false;
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        if (this.alive) {
            checkPlayerDistance();
            checkWallColision();
        }

    }


    private void checkPlayerDistance() {
        if (body.getLinearVelocity().y == 0 && getPlayerDistance() < playerDetectingRange) {
            moveTowardsPlayer();
        }
    }

    private void moveTowardsPlayer() {
        Vector2 vectDirection = Player.player.body.getPosition().sub(body.getPosition()).nor().scl(monsterMovingScale);
        body.setLinearVelocity(vectDirection);
        if (vectDirection.x < 0) {
            direction = HorizontalDirection.LEFT;
        } else {
            direction = HorizontalDirection.RIGHT;
        }
    }


    public void movePlayerCloser() {
        Vector2 vectDirection = body.getPosition().sub(Player.player.body.getPosition()).nor().scl(playerMovingScale);
        Player.player.body.setLinearVelocity(vectDirection);
    }

    double getPlayerDistance() {
        return Math.sqrt(Math.pow((body.getPosition().x - Player.player.body.getPosition().x), 2) + Math.pow((body.getPosition().y - Player.player.body.getPosition().y), 2));
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (this.alive) {
            sprite.draw(batch);
        }


    }

    public void enableZone() {
        Sound.playMagnetoSound();
        if (!zone) {
            sprite.setRegion(zoneTexture);
            zone = true;
            sprite.setSize(sprite.getWidth() * 2, sprite.getHeight() * 2);
        }

    }


}
