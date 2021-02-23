package tk.sebastjanmevlja.doodlejump.Gameplay.Monster;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import tk.sebastjanmevlja.doodlejump.Gameplay.Asset;
import tk.sebastjanmevlja.doodlejump.Gameplay.Constants;
import tk.sebastjanmevlja.doodlejump.Gameplay.Player;


public class UfoMonster extends Monster {



    private static float playerMovingScale = Constants.HEIGHT * 0.002f;
    private static float monsterMovingScale = Constants.HEIGHT * 0.002f;
    private static float playerDetectingRange = Constants.HEIGHT * 0.0048f;

    private  static final TextureAtlas.AtlasRegion texture = Asset.atlas.findRegion("ufo");
    public  static final TextureAtlas.AtlasRegion alienUfoLight = Asset.atlas.findRegion("ufo_light");



    public UfoMonster( World world, float x, float y) {
        super(texture, world, x, y);
        WIDTH = Constants.WIDTH / 3f;
        HEIGHT = Constants.HEIGHT / 4f;
        init( world, x, y);
    }






    @Override
    public void act(float delta) {
        super.act(delta);
        if (this.alive){
            checkPlayerDistance();
            checkWallColision();
        }

    }


    private void checkPlayerDistance(){
        if (body.getLinearVelocity().y == 0 && getPlayerDistance() < playerDetectingRange){
            moveTowardsPlayer();
        }
    }

    private void moveTowardsPlayer(){
        Vector2 vectDirection = Player.player.body.getPosition().sub(body.getPosition()).nor().scl(monsterMovingScale);
        body.setLinearVelocity(vectDirection);
    }


    public void movePlayerCloser(){
        Vector2 vectDirection = body.getPosition().sub(Player.player.body.getPosition()).nor().scl(playerMovingScale);
        Player.player.body.setLinearVelocity(vectDirection);
    }

    double getPlayerDistance(){
        return Math.sqrt(Math.pow((body.getPosition().x - Player.player.body.getPosition().x), 2) + Math.pow((body.getPosition().y - Player.player.body.getPosition().y), 2));
    }




    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (this.alive){
            sprite.draw(batch);
        }


    }

    public void showLight(){
        sprite.setRegion(alienUfoLight);
    }


}
