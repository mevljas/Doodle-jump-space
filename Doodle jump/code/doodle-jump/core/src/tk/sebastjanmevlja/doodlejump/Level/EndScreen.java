package tk.sebastjanmevlja.doodlejump.Level;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import tk.sebastjanmevlja.doodlejump.Gameplay.Asset;
import tk.sebastjanmevlja.doodlejump.Gameplay.Constants;
import tk.sebastjanmevlja.doodlejump.Gameplay.Player;
import tk.sebastjanmevlja.doodlejump.Gameplay.Sound;
import tk.sebastjanmevlja.doodlejump.MyGame.Game;

public class EndScreen implements Screen{

    private Game game;
    private Stage stage;


    public EndScreen(final Game game){
        this.game = game;

        /// create stage and set it as input processor
        stage = new Stage(new ScreenViewport());
        Skin skin = Asset.skin;

        // Create a table that fills the screen. Everything else will go inside
        // this table.
        Table table = new Table();
        table.setFillParent(true);
        //table.setDebug(true);
        stage.addActor(table);

        Label titleLabel = new Label("Game over", skin, "title");
        Label textLabel = new Label("Better luck next time!", skin, "default");
//        titleLabel.setFontScale(Constants.WIDTH / 500f);
        Label scoreLabel = new Label("Score: " + Player.getScore(), skin, "default");

        // return to main screen button
        final TextButton retryButton = new TextButton("Retry", skin);
        retryButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
//                Sound.changeMusicState();
                EndScreen.this.game.changeScreen(Screens.LEVEL1SCREEN);

            }
        });

        final TextButton backButton = new TextButton("Menu", skin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
//                Sound.changeMusicState();
                EndScreen.this.game.changeScreen(Screens.MENUSCREEN);

            }
        });



        table.defaults().width(Value.percentWidth(.100F, table));
        table.defaults().height(Value.percentHeight(.10F, table));

        table.add(titleLabel).center().width(Value.percentWidth(.55F, table));
        table.row().padTop(Value.percentWidth(.1F, table));
        table.add(textLabel).center().width(Value.percentWidth(.60F, table));
        table.row().padTop(Value.percentWidth(.1F, table));
        table.add(scoreLabel).center().width(Value.percentWidth(.60F, table));
        table.row().padTop(Value.percentWidth(.2F, table));
        table.add(retryButton).center().width(Value.percentWidth(.50F, table));
        table.row().padTop(Value.percentWidth(.1F, table));
        table.add(backButton).center().width(Value.percentWidth(.50F, table));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
            Sound.changeMusicState();
            game.changeScreen(Screens.MENUSCREEN);
        }

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
    public void resize(int width, int height) {}

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        stage.dispose();
    }

}
