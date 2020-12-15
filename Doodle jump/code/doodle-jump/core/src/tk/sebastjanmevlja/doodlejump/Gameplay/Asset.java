package tk.sebastjanmevlja.doodlejump.Gameplay;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Asset {


//    Asset descriptors
    private final AssetDescriptor<TextureAtlas> atlasDescriptor = new AssetDescriptor<>("images/Doodle_jump.atlas", TextureAtlas.class);
    private final AssetDescriptor<Texture> backgroundImageDescriptor = new AssetDescriptor<>("images/loadingBackground.jpg", Texture.class);
    private final AssetDescriptor<Texture> logoTextureDescriptor = new AssetDescriptor<>("images/logo.png", Texture.class);
    private final AssetDescriptor<Skin> skinDescriptor = new AssetDescriptor<>("skin/glassy-ui.json", Skin.class);


//    Assets
    public static Texture loadingBackgroundTexture;
    public static Texture logoTexture;
    public static TextureAtlas atlas;
    public static Skin skin;



    private final AssetManager assetManager = new AssetManager();

    public Asset() {
    }

    public void loadGame()
    {
        assetManager.load(skinDescriptor);
        assetManager.load(backgroundImageDescriptor);
        assetManager.load(logoTextureDescriptor);

        assetManager.finishLoading();

        loadingBackgroundTexture = assetManager.get(backgroundImageDescriptor);
        logoTexture = assetManager.get(logoTextureDescriptor);
        skin = assetManager.get(skinDescriptor);
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
