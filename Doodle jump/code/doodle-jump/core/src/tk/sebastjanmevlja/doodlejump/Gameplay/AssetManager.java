package tk.sebastjanmevlja.doodlejump.Gameplay;

import com.badlogic.gdx.assets.loaders.SkinLoader.SkinParameter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import static tk.sebastjanmevlja.doodlejump.MyGame.Main.assetManager;

public class AssetManager {

    public static Texture backgroundTexture;
    public static Texture playerTExture;
    public static TextureAtlas mainAtlas;
    public static Texture loadingBackgroundTexture;
    public static BitmapFont font;


    public final com.badlogic.gdx.assets.AssetManager manager = new com.badlogic.gdx.assets.AssetManager();


    // Textures

    private final String mainImage = "images/Doodle_jump.atlas";
    public final String loadingBackgroundImage = "images/loadingBackground.jpg";

    //font
    private final String fontFile = "fonts/OpenSans-Bold.fnt";


    public void queueAddFonts() {
        manager.load(fontFile, BitmapFont.class);
    }

    public void inicializeFonts() {
        font = assetManager.manager.get(assetManager.fontFile);
        font.setColor(255, 249, 129, 1);
    }


    public void queueAddImages() {
        manager.load(mainImage, TextureAtlas.class);
    }

    public void inicializeImages() {
        mainAtlas = assetManager.manager.get(assetManager.mainImage);
    }

    // a small set of images used by the loading screen
    public void queueAddLoadingImages() {
        manager.load(loadingBackgroundImage, Texture.class);
    }

    public void queueAddSkin() {
        SkinParameter params = new SkinParameter("skin/glassy-ui.atlas");
        String skin = "skin/glassy-ui.json";
        manager.load(skin, Skin.class, params);
    }





    public void dispose() {
        backgroundTexture.dispose();
        playerTExture.dispose();
        loadingBackgroundTexture.dispose();
        font.dispose();

    }
}
