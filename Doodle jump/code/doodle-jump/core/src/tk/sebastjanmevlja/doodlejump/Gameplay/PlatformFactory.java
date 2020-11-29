package tk.sebastjanmevlja.doodlejump.Gameplay;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.World;

import java.util.LinkedList;
import java.util.Random;

public class PlatformFactory {

    public static LinkedList<Platform> platforms = new LinkedList<>();

    static Random random = new Random();

    private  static final TextureAtlas.AtlasRegion plaformTextureRegion = AssetStorage.atlas.findRegion("platform_green");

    private static float maxJumpHeight = Player.JUMP_VELOCITY * 50;
    private static int InitiaPlatformSize;



    public static final float VELOCITY = 2;

    public static void generatePlatforms( World world){
        generatePlatform(world, Constants.WIDTH / 2f, 0);

        float y = Platform.PLATFORM_HEIGHT * 6;

        while (y < Constants.HEIGHT * 2) {
            float x = random.nextFloat() * (Constants.WIDTH - Platform.PLATFORM_WIDTH) + Platform.PLATFORM_WIDTH / 2;

            generatePlatform(world, x, y);

            y += (maxJumpHeight - 0.5f);
            y -= random.nextFloat() * (maxJumpHeight / 3);
        }


        InitiaPlatformSize = platforms.size();
    }
    
    

    public static void generatePlatform(World world, float x, float y) {
        platforms.add(new Platform(plaformTextureRegion,world, x, y));
    }


    public static void moveWorld(){
        for (Platform p: platforms) {
            p.body.setLinearVelocity(0f,-VELOCITY);
        }
    }


    public static void stopWorld(){
        for (Platform p: platforms) {
            p.body.setLinearVelocity(0f,0f);
        }
    }


    public static void generateMorePlatforms( World world){

        float y = platforms.getLast().body.getPosition().y;
        while (y < Constants.HEIGHT * 2) {
            float x = random.nextFloat() * (Constants.WIDTH - Platform.PLATFORM_WIDTH) + Platform.PLATFORM_WIDTH / 2;

            generatePlatform(world, x, y);

            y += (maxJumpHeight - 0.5f);
            y -= random.nextFloat() * (maxJumpHeight / 3);
        }



    }
}
