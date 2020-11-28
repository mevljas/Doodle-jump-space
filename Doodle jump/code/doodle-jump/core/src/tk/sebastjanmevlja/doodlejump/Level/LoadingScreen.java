package tk.sebastjanmevlja.doodlejump.Level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import tk.sebastjanmevlja.doodlejump.Gameplay.AssetDescriptors;
import tk.sebastjanmevlja.doodlejump.Gameplay.AssetStorage;
import tk.sebastjanmevlja.doodlejump.Gameplay.Constants;
import tk.sebastjanmevlja.doodlejump.MyGame.Game;

public class LoadingScreen implements Screen {
    private final Game main;

    private final Stage stage;
    private final ProgressBar progressBar;



    public LoadingScreen(Game main) {
        this.main = main;

        stage = new Stage(new ScreenViewport());




        progressBar = new ProgressBar(0, 5, 1, false, AssetStorage.skin);
        progressBar.setValue(0);
        progressBar.setWidth(Constants.WIDTH * 0.7f);
        progressBar.getStyle().background.setMinHeight(Constants.HEIGHT * 0.06f);
        progressBar.getStyle().knobBefore.setMinHeight(Constants.HEIGHT * 0.05f);

        progressBar.setX(Constants.WIDTH / 2 - progressBar.getWidth() / 2);
        progressBar.setY(Constants.HEIGHT * 0.2f);
        stage.addActor(progressBar);
        addAssets();
    }

    private void addAssets() {
        Game.assetManager.load(AssetDescriptors.atlas);


    }

    private void getAssets(){
        AssetStorage.atlas = Game.assetManager.get(AssetDescriptors.atlas);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {

        Batch gameBatch = main.getBatch();

        gameBatch.begin();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameBatch.draw(AssetStorage.loadingBackgroundTexture, 0, 0, Constants.WIDTH, Constants.HEIGHT);
        gameBatch.end();


        while(!Game.assetManager.update()) {

            float progress = Game.assetManager.getProgress();
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
        AssetStorage.atlas.dispose();
    }

}
