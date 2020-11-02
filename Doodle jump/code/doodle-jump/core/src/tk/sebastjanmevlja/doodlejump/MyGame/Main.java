package tk.sebastjanmevlja.doodlejump.MyGame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import tk.sebastjanmevlja.doodlejump.Gameplay.AssetManager;
import tk.sebastjanmevlja.doodlejump.Level.Level1Screen;
import tk.sebastjanmevlja.doodlejump.Level.LoadingScreen;
import tk.sebastjanmevlja.doodlejump.Level.Screens;


public class Main extends Game {

    public static AssetManager assetManager = new AssetManager();
    public static Main main;


    private SpriteBatch batch;
    private LoadingScreen loadingScreen;
    private Level1Screen level1Screen;


    @Override
    public void create() {
        batch = new SpriteBatch();
        main = this;
        load();
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


    public void load()
    {
        Main.assetManager.queueAddSkin();
        Main.assetManager.manager.finishLoading();
        // load loading images and wait until finished
        Main.assetManager.queueAddLoadingImages();
        Main.assetManager.manager.finishLoading();
        Main.assetManager.queueAddImages();

    }


    @Override
    public void dispose() {

        batch.dispose();
        assetManager.dispose();
    }


    public SpriteBatch getBatch() {
        return this.batch;
    }

}
