package tk.sebastjanmevlja.doodlejump.Level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import tk.sebastjanmevlja.doodlejump.Gameplay.Asset;
import tk.sebastjanmevlja.doodlejump.Gameplay.Constants;
import tk.sebastjanmevlja.doodlejump.MyGame.Game;

public class MenuScreen implements Screen {

//    TODO: font scaling

    private final Game game;
    private Stage stage;
    private Skin skin;

    public MenuScreen(Game game) {
        this.game = game;
        /// create stage and set it as input processor
        stage = new Stage(new ScreenViewport());
        skin = Asset.skin;

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        // Create a table that fills the screen. Everything else will go inside this table.
        Table table = new Table();
        table.setFillParent(true);
        //table.setDebug(true);
        stage.addActor(table);

        table.defaults().width(Value.percentWidth(.70F, table));
        table.defaults().height(Value.percentHeight(.1F, table));


        //create buttons
        TextButton newGame = new TextButton("New Game", skin);
//        newGame.getLabel().setFontScale(Constants.WIDTH / 600f, Constants.HEIGHT/ 900f);

        TextButton preferences = new TextButton("Preferences", skin);
//        preferences.getLabel().setFontScale(Constants.WIDTH * 0.005f);
        TextButton exit = new TextButton("Exit", skin);
//        exit.getLabel().setFontScale(Constants.WIDTH / 600f, Constants.HEIGHT/ 900f);
        TextButton about = new TextButton("About", skin);
//        about.getLabel().setFontScale(Constants.WIDTH / 600f, Constants.HEIGHT/ 900f);

        //add buttons to table
        table.add(newGame);
        table.row().pad(80, 0, 0, 0);
        table.add(preferences);
        table.row().pad(80, 0, 0, 0);
        table.add(about);
        table.row().pad(80, 0, 0, 0);
        table.add(exit);

        // create button listeners
        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.exit(0);
            }
        });

        newGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(Screens.LEVEL1SCREEN);
            }
        });

        preferences.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(Screens.PREFERENCESSCREEN);
            }
        });

        about.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(Screens.ABOUTSCREEN);
            }
        });

    }

    @Override
    public void render(float delta) {
        Batch gameBatch = game.getBatch();

        gameBatch.begin(); //kdr zacenmo rendirat klicemo begin
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameBatch.draw(Asset.loadingBackgroundTexture, 0, 0, Constants.WIDTH, Constants.HEIGHT);
        gameBatch.end();

        // tell our stage to do actions and draw itself
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // change the stage's viewport when teh screen size is changed
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        // dispose of assets when not needed anymore
        stage.dispose();
    }

}