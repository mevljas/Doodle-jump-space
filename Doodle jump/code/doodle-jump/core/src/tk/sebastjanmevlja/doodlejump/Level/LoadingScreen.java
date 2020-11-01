package tk.sebastjanmevlja.doodlejump.Level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import tk.sebastjanmevlja.doodlejump.Gameplay.AssetManager;
import tk.sebastjanmevlja.doodlejump.Gameplay.GameInfo;
import tk.sebastjanmevlja.doodlejump.Level.Screens;
import tk.sebastjanmevlja.doodlejump.MyGame.Main;

public class LoadingScreen implements Screen {
    private Main main;

    private final int FONT = 1;        // loading fonts
    private final int FONT2 = 2;        // loading particle effects
    private final int SOUND = 3;        // loading sounds
    private final int MUSIC = 4;        // loading music

    private int currentLoadingStage = 0;

    // timer for exiting loading screen
    private float countDown = 2f;

    private Stage stage;
    private ProgressBar progressBar;


    public LoadingScreen(Main main) {
        this.main = main;

        stage = new Stage(new ScreenViewport());

        Main.assetManager.queueAddSkin();
        Main.assetManager.manager.finishLoading();
        Skin skin = Main.assetManager.manager.get("skin/glassy-ui.json");
        progressBar = new ProgressBar(0, 5, 1, false, skin);
        progressBar.setValue(0);
        progressBar.setWidth(GameInfo.WIDTH * 0.7f);
        progressBar.getStyle().background.setMinHeight(GameInfo.HEIGHT * 0.06f);
        progressBar.getStyle().knobBefore.setMinHeight(GameInfo.HEIGHT * 0.05f);

        progressBar.setX(GameInfo.WIDTH / 2 - progressBar.getWidth() / 2);
        progressBar.setY(GameInfo.HEIGHT * 0.2f);
        stage.addActor(progressBar);
        loadAssets();
        // initiate queueing of images but don't start loading
        Main.assetManager.queueAddImages();
        System.out.println("Loading images....");
    }

    private void loadAssets() {
        // load loading images and wait until finished
        Main.assetManager.queueAddLoadingImages();
        Main.assetManager.manager.finishLoading();

        // get images used to display loading progress
        AssetManager.loadingBackgroundTexture = Main.assetManager.manager.get(Main.assetManager.loadingBackgroundImage);

    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {

        Batch gameBatch = main.getBatch();

        gameBatch.begin(); //kdr zacenmo rendirat klicemo begin
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameBatch.draw(AssetManager.loadingBackgroundTexture, 0, 0, GameInfo.WIDTH, GameInfo.HEIGHT);
        gameBatch.end();

        if (Main.assetManager.manager.update()) { // Load some, will return true if done loading
            currentLoadingStage += 1;
            switch (currentLoadingStage) {
                case FONT:
                    progressBar.setValue(1);
                    Main.assetManager.inicializeImages();
                    System.out.println("Loading fonts....");
                    Main.assetManager.queueAddFonts();
                    break;
                case FONT2:
                    progressBar.setValue(4);
                    System.out.println("Inicializing fonts....");
                    Main.assetManager.inicializeFonts();
                    break;
            }
            if (currentLoadingStage > 5) {
                countDown -= delta;
                currentLoadingStage = 5;
                if (countDown < 0) {
                    main.changeScreen(Screens.LEVEL1SCREEN);
                }
            }
        }

        stage.draw();


    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}
