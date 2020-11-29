package tk.sebastjanmevlja.doodlejump.Gameplay;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class PlatformFactory {

    public static LinkedList<Platform> platforms = new LinkedList<>();

    static Random random = new Random();

    private  static final TextureAtlas.AtlasRegion plaformTextureRegion = AssetStorage.atlas.findRegion("platform_green");

    private static final float maxJumpHeight = Player.JUMP_VELOCITY * 70;
    public static int InitiaPlatformSize;



    public static void generatePlatforms( World world){
        generatePlatform(world, Constants.WIDTH / 2f, 0);

        float y = Platform.PLATFORM_HEIGHT * 4;

        while (y < Constants.HEIGHT * 5) {
            float x = random.nextFloat() * (Constants.WIDTH - Platform.PLATFORM_WIDTH) + Platform.PLATFORM_WIDTH / 2;

            generatePlatform(world, x, y);

            y += (maxJumpHeight - 0.5f);
            y -= random.nextFloat() * (maxJumpHeight / 33);
        }


        InitiaPlatformSize = platforms.size();
    }

    public static ArrayList<Platform> generateMorePlatforms(World world){

        ArrayList<Platform> list = new ArrayList<>();

        float y = platforms.getLast().sprite.getY() + Platform.PLATFORM_HEIGHT * 3;
        while (y < Constants.HEIGHT * 5) {
            float x = random.nextFloat() * (Constants.WIDTH - Platform.PLATFORM_WIDTH) + Platform.PLATFORM_WIDTH / 2;

            generatePlatform(world, x, y);
            list.add(platforms.getLast());

            y += (maxJumpHeight - 0.5f);
            y -= random.nextFloat() * (maxJumpHeight / 3);
        }

        return list;



    }
    
    

    public static void generatePlatform(World world, float x, float y) {
        platforms.add(new Platform(plaformTextureRegion,world, x, y));
    }


    public static void moveWorld(float velocity){

        for (Platform p: platforms) {
            p.body.setLinearVelocity(0f,-velocity * 1.8f);
        }
    }


    public static void stopWorld(){
        for (Platform p: platforms) {
            p.body.setLinearVelocity(0f,0f);
        }
    }




    public static void removePlatform(Platform p){
        platforms.removeFirst();
    }





}
