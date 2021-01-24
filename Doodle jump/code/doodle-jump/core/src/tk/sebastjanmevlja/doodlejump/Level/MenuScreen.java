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
        stage.clear();
        // Create a table that fills the screen. Everything else will go inside this table.
        Table table = new Table();
        table.setFillParent(true);
        //table.setDebug(true);
        stage.addActor(table);

        table.defaults().width(Value.percentWidth(.75F, table));
        table.defaults().height(Value.percentHeight(.1F, table));


        //create buttons
        TextButton continueGame = new TextButton("Continue Game", skin);
        TextButton newGame = new TextButton("New Game", skin);
        TextButton preferences = new TextButton("Preferences", skin);
        TextButton exit = new TextButton("Exit", skin);
        TextButton about = new TextButton("About", skin);
        TextButton.TextButtonStyle textButtonStyle =  newGame.getStyle();
        textButtonStyle.font = Asset.fontMedium;
        continueGame.setStyle(textButtonStyle);
        newGame.setStyle(textButtonStyle);


        preferences.setStyle(textButtonStyle);


        exit.setStyle(textButtonStyle);


        about.setStyle(textButtonStyle);

        //add buttons to table
        if ((Level1Screen.paused != null && Level1Screen.paused) || Game.localStorage.getSavedData()){
            table.add(continueGame);
            table.row().padTop(Value.percentWidth(.10F, table));
        }
        table.add(newGame);
        table.row().padTop(Value.percentWidth(.10F, table));
        table.add(preferences);
        table.row().padTop(Value.percentWidth(.10F, table));
        table.add(about);
        table.row().padTop(Value.percentWidth(.10F, table));
        table.add(exit);

        // create button listeners
        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Game.localStorage.setSavedData(false);
                System.exit(0);
            }
        });

        continueGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(Screens.LEVEL1SCREEN);
            }
        });

        newGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Game.localStorage.setSavedData(false);
                if (game.level1Screen != null)
                    game.level1Screen.dispose();
                    game.level1Screen = null;
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