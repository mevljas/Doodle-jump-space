package tk.sebastjanmevlja.doodlejump.Gameplay;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.World;

import java.util.LinkedList;
import java.util.Random;

import static tk.sebastjanmevlja.doodlejump.Gameplay.Platform.PLATFORM_WIDTH;

public class PlatformFactory {

    public static LinkedList<Platform> platforms;

    static Random random = new Random();

    private  static final TextureAtlas.AtlasRegion plaformTextureRegionGreen = Asset.atlas.findRegion("platform_green");
    private  static final TextureAtlas.AtlasRegion plaformTextureRegionBrown = Asset.atlas.findRegion("brown_platform");
    private  static final TextureAtlas.AtlasRegion plaformTextureRegionWhite = Asset.atlas.findRegion("platform_softblue");
    private  static final TextureAtlas.AtlasRegion plaformTextureRegionDarkBlue = Asset.atlas.findRegion("platform_white");
    private  static final TextureAtlas.AtlasRegion plaformTextureRegionLightBlue = Asset.atlas.findRegion("platform__blue");


    public static int InitiaPlatformSize;

    private static float maxSpacingHeight;
    private static float minSpacingHeight;
    private static float maxSpacingWidth;
    private static float minSpacingWidth;

    private static Random r;

    private static float y;


    public PlatformFactory() {
        platforms = new LinkedList<>();
        maxSpacingHeight = Constants.HEIGHT * 0.35f;
        minSpacingHeight = Constants.HEIGHT * 0.21f;
        maxSpacingWidth = PLATFORM_WIDTH * 2.2f;
        minSpacingWidth = PLATFORM_WIDTH * 0.1f;
        r = new Random();
    }

    public static void generatePlatforms(World world){

        generatePlatform(PlatformType.STATIC, PlatformColor.GREEN, world, Constants.WIDTH / 2f, 0);
        y = 0;



        while (y < Constants.HEIGHT * 5) {
//            float x = Platform.PLATFORM_WIDTH / 2 + random.nextFloat() * (Constants.WIDTH - Platform.PLATFORM_WIDTH * 1.3f);
            float x = minSpacingWidth + random.nextFloat() * (maxSpacingWidth - minSpacingWidth);
            y += minSpacingHeight + r.nextFloat()  * (maxSpacingHeight - minSpacingHeight);

            generatePlatform(randomType(), randomColor(), world, x, y);


        }


        InitiaPlatformSize = platforms.size();
    }

    
    

    public static void generatePlatform(PlatformType type, PlatformColor color, World world, float x, float y) {
        TextureAtlas.AtlasRegion atlasRegion;
        switch (color){
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
        platforms.add(new Platform(type, color, atlasRegion,world, x, y));
    }

    public static void recyclePlatform(PlatformType type, PlatformColor color, Platform platform, float x, float y) {
        TextureAtlas.AtlasRegion atlasRegion;
        switch (color){
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
        platform.changeType(type);
        platform.changeColor(color);
        platform.changeTexture(atlasRegion);
        platform.changePosition(x,y);
        platform.resetItems();
        platforms.remove(platform);
        platforms.addLast(platform);
    }

    private static PlatformColor randomColor(){
        return PlatformColor.values()[new Random().nextInt(PlatformColor.values().length - 1)];
    }

    private static PlatformType randomType(){
        return r.nextInt(10) > 7 ? PlatformType.MOVING : PlatformType.STATIC;
    }


    public static void moveWorld(float velocity){

        for (Platform p: platforms) {
            p.body.setLinearVelocity(p.body.getLinearVelocity().x,-velocity );
        }
    }


    public static void stopWorld(){
        for (Platform p: platforms) {
            p.body.setLinearVelocity(p.body.getLinearVelocity().x,0f);
        }
    }




    public static void removePlatform(Platform p){
        platforms.remove(p);
    }

    public static void recyclePlatform(Platform p){
        float y = platforms.getLast().sprite.getY() + minSpacingHeight + r.nextFloat()  * (maxSpacingHeight - minSpacingHeight);
        float x = minSpacingWidth + random.nextFloat() * (maxSpacingWidth - minSpacingWidth);
        recyclePlatform(randomType(), randomColor(), p, x, y );
    }

    public static float getY() {
        return y;
    }
}
