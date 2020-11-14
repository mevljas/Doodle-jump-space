package tk.sebastjanmevlja.doodlejump.MyGame;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import tk.sebastjanmevlja.doodlejump.Gameplay.AssetDescriptors;
import tk.sebastjanmevlja.doodlejump.Gameplay.AssetStorage;
import tk.sebastjanmevlja.doodlejump.Level.Level1Screen;
import tk.sebastjanmevlja.doodlejump.Level.LoadingScreen;
import tk.sebastjanmevlja.doodlejump.Level.Screens;


public class Game extends com.badlogic.gdx.Game {

    public static AssetManager assetManager;
    public static Game main;


    private SpriteBatch batch;
    private LoadingScreen loadingScreen;
    private Level1Screen level1Screen;


    @Override
    public void create() {
        batch = new SpriteBatch();
        main = this;
        assetManager = new AssetManager();
        loadAssets();

        AssetStorage.loadingBackgroundTexture = Game.assetManager.get(AssetDescriptors.backgroundImage);
        AssetStorage.skin = Game.assetManager.get(AssetDescriptors.skin);


        changeScreen(Screens.LOADINGSCREEN);

    }


    public void changeScreen(Screens screen) {
        switch (screen) {

            case LOADINGSCREEN:
                if (loadingScreen == null)
                    loadingScreen = new LoadingScreen(this);
                setScreen(loadingScreen);
                break;
            case LEVEL1SCREEN:
                if (level1Screen == null)
                    level1Screen = new Level1Screen(this);
                setScreen(level1Screen);
                break;

        }
    }

    @Override
    public void render() {
        super.render();
    }


    public void loadAssets()
    {
        assetManager.load(AssetDescriptors.skin);
        assetManager.load(AssetDescriptors.backgroundImage);

        assetManager.finishLoading();



    }


    @Override
    public void dispose() {

        batch.dispose();
        assetManager.dispose();
        AssetStorage.loadingBackgroundTexture.dispose();
        AssetStorage.skin.dispose();
    }


    public SpriteBatch getBatch() {
        return this.batch;
    }

}
