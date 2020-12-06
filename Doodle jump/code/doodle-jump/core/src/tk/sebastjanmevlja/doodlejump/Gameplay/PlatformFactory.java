package tk.sebastjanmevlja.doodlejump.Gameplay;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class PlatformFactory {

    public static LinkedList<Platform> platforms = new LinkedList<>();

    static Random random = new Random();

    private  static final TextureAtlas.AtlasRegion plaformTextureRegionGreen = AssetStorage.atlas.findRegion("platform_green");
    private  static final TextureAtlas.AtlasRegion plaformTextureRegionBrown = AssetStorage.atlas.findRegion("brown_platform");
    private  static final TextureAtlas.AtlasRegion plaformTextureRegionWhite = AssetStorage.atlas.findRegion("platform_softblue");
    private  static final TextureAtlas.AtlasRegion plaformTextureRegionDarkBlue = AssetStorage.atlas.findRegion("platform_white");
    private  static final TextureAtlas.AtlasRegion plaformTextureRegionLightBlue = AssetStorage.atlas.findRegion("platform__blue");

    private static final float maxJumpHeight = Player.JUMP_VELOCITY * 70;
    public static int InitiaPlatformSize;





    public static void generatePlatforms( World world){
        generatePlatform(PlatformType.GREEN, world, Constants.WIDTH / 2f, 0);

        float y = Platform.PLATFORM_HEIGHT * 4;

        while (y < Constants.HEIGHT * 5) {
            float x = random.nextFloat() * (Constants.WIDTH - Platform.PLATFORM_WIDTH) + Platform.PLATFORM_WIDTH / 2;

            generatePlatform(randomType(), world, x, y);

            y += (maxJumpHeight - 0.5f);
            y -= random.nextFloat() * (maxJumpHeight / 3);
        }


        InitiaPlatformSize = platforms.size();
    }

    public static ArrayList<Platform> generateMorePlatforms(World world){

        ArrayList<Platform> list = new ArrayList<>();

        float y = platforms.getLast().sprite.getY() + Platform.PLATFORM_HEIGHT ;
        while (y < Constants.HEIGHT * 5) {
            float x = random.nextFloat() * (Constants.WIDTH - Platform.PLATFORM_WIDTH) + Platform.PLATFORM_WIDTH / 2;

            generatePlatform(randomType(), world, x, y);
            list.add(platforms.getLast());

            y += (maxJumpHeight - 0.5f);
            y -= random.nextFloat() * (maxJumpHeight / 3);
        }

        return list;



    }
    
    

    public static void generatePlatform(PlatformType type, World world, float x, float y) {
        TextureAtlas.AtlasRegion atlasRegion;
        switch (type){
            case BROWN:
                atlasRegion = plaformTextureRegionBrown;
                break;

            case WHITE:
                atlasRegion = plaformTextureRegionWhite;
                break;

            case DARK_BLUE:
                atlasRegion = plaformTextureRegionDarkBlue;
                break;

            case LIGHT_BLUE:
                atlasRegion = plaformTextureRegionLightBlue;
                break;

            default:
                atlasRegion = plaformTextureRegionGreen;
                break;
        }
        platforms.add(new Platform(type, atlasRegion,world, x, y));
    }

    private static PlatformType randomType(){
        return PlatformType.values()[new Random().nextInt(PlatformType.values().length)];
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
