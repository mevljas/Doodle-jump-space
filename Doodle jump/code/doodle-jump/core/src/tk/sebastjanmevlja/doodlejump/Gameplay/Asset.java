package tk.sebastjanmevlja.doodlejump.Gameplay;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Asset {


//    Asset descriptors
    private final AssetDescriptor<TextureAtlas> atlasDescriptor = new AssetDescriptor<>("images/Doodle_jump.atlas", TextureAtlas.class);
    private final AssetDescriptor<Texture> backgroundImageDescriptor = new AssetDescriptor<>("images/loadingBackground.jpg", Texture.class);
    private final AssetDescriptor<Texture> logoTextureDescriptor = new AssetDescriptor<>("images/logo.png", Texture.class);
    private final AssetDescriptor<Skin> skinDescriptor = new AssetDescriptor<>("skin/glassy-ui.json", Skin.class);

    private final AssetDescriptor<Music> backgroundMusicDescriptor = new AssetDescriptor<>("sounds/backgroundMusic.mp3", Music.class);
    private final AssetDescriptor<Music> fallingSoundDescriptor = new AssetDescriptor<>("sounds/fallingSound.mp3", Music.class);
    private final AssetDescriptor<Music> jumpSoundDescriptor = new AssetDescriptor<>("sounds/jumpSound.wav", Music.class);
    private final AssetDescriptor<Music> monsterSoundDescriptor = new AssetDescriptor<>("sounds/monsterSound.mp3", Music.class);
    private final AssetDescriptor<Music> platformBreakingSoundDescriptor = new AssetDescriptor<>("sounds/platformBreakingSound.mp3", Music.class);
    private final AssetDescriptor<Music> startSoundDescriptor = new AssetDescriptor<>("sounds/startSound.wav", Music.class);


//    Assets
    public static Texture loadingBackgroundTexture;
    public static Texture logoTexture;
    public static TextureAtlas atlas;
    public static Skin skin;


    public static Music backgroundMusic;
    public static Music fallingSound;
    public static Music jumpSound;
    public static Music monsterSound;
    public static Music platformBreakingSound;
    public static Music startSound;



    private final AssetManager assetManager = new AssetManager();

    public Asset() {
    }

    public void loadGame()
    {
        assetManager.load(skinDescriptor);
        assetManager.load(backgroundImageDescriptor);
        assetManager.load(logoTextureDescriptor);
        assetManager.load(backgroundMusicDescriptor);
        assetManager.load(fallingSoundDescriptor);
        assetManager.load(jumpSoundDescriptor);
        assetManager.load(monsterSoundDescriptor);
        assetManager.load(platformBreakingSoundDescriptor);
        assetManager.load(startSoundDescriptor);

        assetManager.finishLoading();

        loadingBackgroundTexture = assetManager.get(backgroundImageDescriptor);
        logoTexture = assetManager.get(logoTextureDescriptor);
        skin = assetManager.get(skinDescriptor);
        backgroundMusic = assetManager.get(backgroundMusicDescriptor);
        fallingSound = assetManager.get(fallingSoundDescriptor);
        jumpSound = assetManager.get(jumpSoundDescriptor);
        monsterSound = assetManager.get(monsterSoundDescriptor);
        platformBreakingSound = assetManager.get(platformBreakingSoundDescriptor);
        startSound = assetManager.get(startSoundDescriptor);
    }

    public void loadAtlas(){
        assetManager.load(atlasDescriptor);
    }
    public void getAtlas(){
        atlas = assetManager.get(atlasDescriptor);
    }

    public void dispose()
    {
        assetManager.dispose();
    }

    public boolean update(){
        return assetManager.update();
    }

    public float getProgress(){
        return assetManager.getProgress();
    }



}
