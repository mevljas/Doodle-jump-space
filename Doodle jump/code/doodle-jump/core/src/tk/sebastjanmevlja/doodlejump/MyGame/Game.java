package tk.sebastjanmevlja.doodlejump.MyGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import tk.sebastjanmevlja.doodlejump.Gameplay.Asset;
import tk.sebastjanmevlja.doodlejump.Helpers.LocalStorage;
import tk.sebastjanmevlja.doodlejump.Level.*;


public class Game extends com.badlogic.gdx.Game {


    public static Game game;


    private SpriteBatch batch;
    private LoadingScreen loadingScreen;
    private PreferencesScreen preferencesScreen;
    private MenuScreen menuScreen;
    private Level1Screen level1Screen;
    private AboutScreen aboutScreen;
    private EndScreen endScreen;
    private PauseScreen pauseScreen;
    public Asset assets;
    public static LocalStorage localStorage;

    @Override
    public void create() {
        batch = new SpriteBatch();
        game = this;
        assets = new Asset();
        assets.loadGame();
//        Gdx.input.setCatchBackKey(true); //back key doesnt the app close - deprecated
        Gdx.input.setCatchKey(Input.Keys.BACK, true);
        localStorage = new LocalStorage();



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
                if (level1Screen != null && !Level1Screen.paused) {
                    level1Screen.dispose();
                    level1Screen = null;
                }
                if (level1Screen == null){
                    level1Screen = new Level1Screen(this);
                }


                setScreen(level1Screen);
                break;
            case ABOUTSCREEN:
                if (aboutScreen == null)
                    aboutScreen = new AboutScreen(this);
                setScreen(aboutScreen);
                break;
            case ENDSCREEN:
                if (endScreen == null)
                    endScreen = new EndScreen(this);
                setScreen(endScreen);
                break;
            case PAUSESCREEN:
                if (pauseScreen == null)
                    pauseScreen = new PauseScreen(this);
                setScreen(pauseScreen);
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
