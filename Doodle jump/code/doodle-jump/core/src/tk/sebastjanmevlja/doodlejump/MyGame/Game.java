package tk.sebastjanmevlja.doodlejump.MyGame;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import tk.sebastjanmevlja.doodlejump.Gameplay.Asset;
import tk.sebastjanmevlja.doodlejump.Level.Level1Screen;
import tk.sebastjanmevlja.doodlejump.Level.LoadingScreen;
import tk.sebastjanmevlja.doodlejump.Level.Screens;


public class Game extends com.badlogic.gdx.Game {


    public static Game main;


    private SpriteBatch batch;
    private LoadingScreen loadingScreen;
    private Level1Screen level1Screen;
    public Asset assets;


    @Override
    public void create() {
        batch = new SpriteBatch();
        main = this;
        assets = new Asset();
        assets.loadGame();




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
