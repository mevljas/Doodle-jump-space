package tk.sebastjanmevlja.doodlejumpspace.Level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Asset;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Constants;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Sound;
import tk.sebastjanmevlja.doodlejumpspace.MyGame.Game;

public class LoadingScreen implements Screen {
    private final Game main;

    private final Stage stage;
    private final ProgressBar progressBar;



    public LoadingScreen(Game main) {
        this.main = main;

        stage = new Stage(new ScreenViewport());




        progressBar = new ProgressBar(0, 5, 1, false, Asset.skin);
        progressBar.setValue(0);
        progressBar.setWidth(Constants.WIDTH * 0.7f);
        progressBar.getStyle().background.setMinHeight(Constants.HEIGHT * 0.06f);
        progressBar.getStyle().knobBefore.setMinHeight(Constants.HEIGHT * 0.05f);

        progressBar.setX(Constants.WIDTH / 2f - progressBar.getWidth() / 2);
        progressBar.setY(Constants.HEIGHT * 0.2f);
        stage.addActor(progressBar);
        addAssets();

        Sound.setMusicEnabled(Game.localStorage.getMusicEnabled());
        Sound.setSoundEnabled(Game.localStorage.getSoundEnabled());
        Sound.changeMusicVolume(Game.localStorage.getMusicVolume());
        Sound.changeSoundVolume(Game.localStorage.getSoundVolume());
        Sound.changeMusicState();
    }

    private void addAssets() {
        main.assets.loadAtlas();


    }

    private void getAssets(){
        main.assets.getAtlas();
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {

        Batch gameBatch = main.getBatch();

        gameBatch.begin();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameBatch.draw(Asset.background, 0, 0, Constants.WIDTH, Constants.HEIGHT);
        gameBatch.end();


        while(!main.assets.update()) {

            float progress = main.assets.getProgress();
            progressBar.setValue(progress);

            stage.draw();

        }

        getAssets();
        main.changeScreen(Screens.MENUSCREEN);




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
        Asset.atlas.dispose();
    }

}
