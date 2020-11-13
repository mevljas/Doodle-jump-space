package tk.sebastjanmevlja.doodlejump.Level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import tk.sebastjanmevlja.doodlejump.Gameplay.AssetDescriptors;
import tk.sebastjanmevlja.doodlejump.Gameplay.GameInfo;
import tk.sebastjanmevlja.doodlejump.MyGame.Main;

public class LoadingScreen implements Screen {
    private final Main main;

    private final Stage stage;
    private final ProgressBar progressBar;
    private final Texture loadingBackgroundTexture;


    public LoadingScreen(Main main) {
        this.main = main;

        stage = new Stage(new ScreenViewport());

        loadingBackgroundTexture = Main.assetManager.get(AssetDescriptors.backgroundImage);
        Skin skin = Main.assetManager.get(AssetDescriptors.skin);


        progressBar = new ProgressBar(0, 5, 1, false, skin);
        progressBar.setValue(0);
        progressBar.setWidth(GameInfo.WIDTH * 0.7f);
        progressBar.getStyle().background.setMinHeight(GameInfo.HEIGHT * 0.06f);
        progressBar.getStyle().knobBefore.setMinHeight(GameInfo.HEIGHT * 0.05f);

        progressBar.setX(GameInfo.WIDTH / 2 - progressBar.getWidth() / 2);
        progressBar.setY(GameInfo.HEIGHT * 0.2f);
        stage.addActor(progressBar);
        addAssets();
    }

    private void addAssets() {
        Main.assetManager.load(AssetDescriptors.atlas);


    }

    private void getAssets(){
        TextureAtlas atlas = Main.assetManager.get(AssetDescriptors.atlas);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {

        Batch gameBatch = main.getBatch();

        gameBatch.begin();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameBatch.draw(loadingBackgroundTexture, 0, 0, GameInfo.WIDTH, GameInfo.HEIGHT);
        gameBatch.end();


        while(!Main.assetManager.update()) {

            float progress = Main.assetManager.getProgress();
            progressBar.setValue(progress);

            stage.draw();

        }

        getAssets();
        main.changeScreen(Screens.LEVEL1SCREEN);




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
