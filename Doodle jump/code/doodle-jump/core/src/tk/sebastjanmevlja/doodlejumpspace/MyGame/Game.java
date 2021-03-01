package tk.sebastjanmevlja.doodlejumpspace.MyGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.golfgl.gdxgamesvcs.IGameServiceClient;
import de.golfgl.gdxgamesvcs.IGameServiceListener;
import de.golfgl.gdxgamesvcs.NoGameServiceClient;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Asset;
import tk.sebastjanmevlja.doodlejumpspace.Helpers.LocalStorage;
import tk.sebastjanmevlja.doodlejumpspace.Level.AboutScreen;
import tk.sebastjanmevlja.doodlejumpspace.Level.EndScreen;
import tk.sebastjanmevlja.doodlejumpspace.Level.Level1Screen;
import tk.sebastjanmevlja.doodlejumpspace.Level.LoadingScreen;
import tk.sebastjanmevlja.doodlejumpspace.Level.MenuScreen;
import tk.sebastjanmevlja.doodlejumpspace.Level.PauseScreen;
import tk.sebastjanmevlja.doodlejumpspace.Level.PreferencesScreen;
import tk.sebastjanmevlja.doodlejumpspace.Level.Screens;


public class Game extends com.badlogic.gdx.Game implements IGameServiceListener {


    public static Game game;
    public IGameServiceClient gsClient;


    private SpriteBatch batch;
    private LoadingScreen loadingScreen;
    private PreferencesScreen preferencesScreen;
    private MenuScreen menuScreen;
    public Level1Screen level1Screen;
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

        // ...awesome initialization code...

        if (gsClient == null)
            gsClient = new NoGameServiceClient();

        // for getting callbacks from the client
        gsClient.setListener((IGameServiceListener) this);

        // establish a connection to the game service without error messages or login screens
        gsClient.resumeSession();



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
        Asset.background.dispose();
        Asset.skin.dispose();
    }


    public SpriteBatch getBatch() {
        return this.batch;
    }


    @Override
    public void pause() {
        super.pause();

        gsClient.pauseSession();
    }

    @Override
    public void resume() {
        super.resume();

        gsClient.resumeSession();
    }


    @Override
    public void gsOnSessionActive() {
        System.out.println("SESSION ACTIVE");
        if (getScreen() == menuScreen){
            menuScreen.show();
        }
    }

    @Override
    public void gsOnSessionInactive() {
        System.out.println("SESSION INACTIVE");
        if (getScreen() == menuScreen){
            menuScreen.show();
        }
    }

    @Override
    public void gsShowErrorToUser(GsErrorType et, String msg, Throwable t) {
        System.out.println(msg);
    }
}
