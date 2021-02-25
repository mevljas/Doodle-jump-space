package tk.sebastjanmevlja.doodlejumpspace.Gameplay.Platform;

import com.badlogic.gdx.physics.box2d.World;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Constants;

import java.util.LinkedList;
import java.util.Random;

import static tk.sebastjanmevlja.doodlejumpspace.Gameplay.Platform.Platform.PLATFORM_HEIGHT;
import static tk.sebastjanmevlja.doodlejumpspace.Gameplay.Platform.Platform.PLATFORM_WIDTH;

public class PlatformFactory {

    public static LinkedList<Platform> platforms;

    static Random random = new Random();


    public static int InitiaPlatformSize;

    private static float maxSpacingHeight;
    private static float minSpacingHeight;
    private static float maxSpacingWidth;
    private static float minSpacingWidth;

    private static Random r;

    private static float y;


    public PlatformFactory() {
        platforms = new LinkedList<>();
        maxSpacingHeight = PLATFORM_HEIGHT * 3.4f;
        minSpacingHeight = PLATFORM_HEIGHT * 1.9f;
        maxSpacingWidth = Constants.WIDTH - PLATFORM_WIDTH;
        minSpacingWidth = PLATFORM_WIDTH * 0.1f;
        r = new Random();
    }

    public static void generatePlatforms(World world){
        y = 0;
        platforms.add(new GrayPlatform(world, Constants.WIDTH / 2f, y));



        while (y < Constants.HEIGHT * 5) {
            float x = minSpacingWidth + random.nextFloat() * (maxSpacingWidth - minSpacingWidth);
            y += minSpacingHeight + r.nextFloat()  * (maxSpacingHeight - minSpacingHeight);

            generateRandomPlatform(world, x, y);


        }


        InitiaPlatformSize = platforms.size();
    }

    
    


    public static void recyclePlatform( Platform platform, float x, float y) {
        platform.changePosition(x,y);
        platform.reEnable();
        if (platform instanceof GrayPlatform)
            platform.resetItems();
        platforms.remove(platform);
        platforms.addLast(platform);
    }

    private static void generateRandomPlatform(World world, float x, float y){
        int value = r.nextInt(10);
        if (value < 2){

            if (platforms.getLast() instanceof BrokenPlatform){
                platforms.add(new GrayPlatform(world, x, y));
            }
            else {
                platforms.add(new BrokenPlatform(world, x, y));
            }
        }
        else if (value < 4){
            platforms.add(new WhitePlatform(world, x, y));

        }
        else{
            platforms.add(new GrayPlatform(world, x, y));
        }
    }


    public static void moveWorld(float velocity){

        for (Platform p: platforms) {
            if (!p.broken)
                p.body.setLinearVelocity(p.body.getLinearVelocity().x,velocity );
        }
    }


    public static void applayForce(float x, float y){

        for (Platform p: platforms) {
            if (!p.broken)
                p.body.applyForceToCenter(x, y, true);
        }
    }

    public static float getYVelocity(){
        return platforms.getFirst().body.getLinearVelocity().y;
    }


    public static void stopWorld(){
        for (Platform p: platforms) {
            if (!p.broken)
                p.body.setLinearVelocity(p.body.getLinearVelocity().x,0f);
        }
    }




    public static void removePlatform(Platform p){
        platforms.remove(p);
    }

    public static void recyclePlatform(Platform p){
        float y = platforms.getLast().sprite.getY() + minSpacingHeight + r.nextFloat()  * (maxSpacingHeight - minSpacingHeight);
        float x = minSpacingWidth + random.nextFloat() * (maxSpacingWidth - minSpacingWidth);
        recyclePlatform(p, x, y );
    }

    public static float getY() {
        return y;
    }
}
