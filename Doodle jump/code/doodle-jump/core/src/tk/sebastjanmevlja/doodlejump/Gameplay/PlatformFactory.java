package tk.sebastjanmevlja.doodlejump.Gameplay;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.Random;

public class PlatformFactory {

    static Random random = new Random();

    private  static final TextureAtlas.AtlasRegion plaformTextureRegion = AssetStorage.atlas.findRegion("platform_green");

    public static void generatePlatforms(ArrayList<Platform> platforms, World world){
        Platform firstPlatform = generatePlatform(world, GameInfo.WIDTH / 2f, 0);
        platforms.add(firstPlatform);

        float y = Platform.PLATFORM_HEIGHT * 7;
        float maxJumpHeight = Player.JUMP_VELOCITY * 8;
        while (y < GameInfo.HEIGHT - GameInfo.WIDTH / 2f) {
            float x = random.nextFloat() * (GameInfo.WIDTH - Platform.PLATFORM_WIDTH) + Platform.PLATFORM_WIDTH / 2;

            Platform platform = generatePlatform(world, x, y);
            platforms.add(platform);

            y += (maxJumpHeight - 0.5f);
            y -= random.nextFloat() * (maxJumpHeight / 3);
        }


//        for (float y = (firstPlatform.getWidth() * 20); y < GameInfo.HEIGHT; y += firstPlatform.spriteHeight() * 3) {
//            for (float x = 0; x < GameInfo.WIDTH; x += firstPlatform.spriteWidth()) {
//                if (random.nextInt() % 3 == 0){
//                    platforms.add(generatePlatform(world, x, y));
//                }
//            }
//        }
    }
    
    

    public static Platform generatePlatform(World world, float x, float y) {
        return new Platform(plaformTextureRegion,world, x, y);
    }
}
