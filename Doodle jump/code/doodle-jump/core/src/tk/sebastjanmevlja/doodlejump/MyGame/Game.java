package tk.sebastjanmevlja.doodlejump.MyGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import tk.sebastjanmevlja.doodlejump.Gameplay.Asset;
import tk.sebastjanmevlja.doodlejump.Level.*;


public class Game extends com.badlogic.gdx.Game {


    public static Game main;


    private SpriteBatch batch;
    private LoadingScreen loadingScreen;
    private PreferencesScreen preferencesScreen;
    private MenuScreen menuScreen;
    private Level1Screen level1Screen;
    private AboutScreen aboutScreen;
    public Asset assets;


    @Override
    public void create() {
        batch = new SpriteBatch();
        main = this;
        assets = new Asset();
        assets.loadGame();
//        Gdx.input.setCatchBackKey(true); //back key doesnt the app close - deprecated
        Gdx.input.setCatchKey(Input.Keys.BACK, true);



        changeScreen(Screens.LOADINGSCREEN);

    }


    public void changeScreen(Screens screen) {
        switch (screen) {

            case LOADINGSCREEN:
                if (loadingScreen == null)
                    loadingScreen = new LoadingScreen(this);
                setScreen(loadingScreen);
                break;
            case MENUSCREEN:
                if (menuScreen == null)
                    menuScreen = new MenuScreen(this);
                setScreen(menuScreen);
                break;
            case PREFERENCESSCREEN:
                if (preferencesScreen == null)
                    preferencesScreen = new PreferencesScreen(this);
                setScreen(preferencesScreen);
                break;
            case LEVEL1SCREEN:
                if (level1Screen == null)
                    level1Screen = new Level1Screen(this);
                setScreen(level1Screen);
                break;
            case ABOUTSCREEN:
                if (aboutScreen == null)
                    aboutScreen = new AboutScreen(this);
                setScreen(aboutScreen);
                break;

        }
    }

    @Override
    public void render() {
        super.render();
    }




    @Override
    public void dispose() {

        batch.dispose();
        assets.dispose();
        Asset.loadingBackgroundTexture.dispose();
        Asset.skin.dispose();
    }


    public SpriteBatch getBatch() {
        return this.batch;
    }

}
