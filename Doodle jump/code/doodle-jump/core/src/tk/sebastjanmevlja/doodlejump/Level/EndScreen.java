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
    private Label titleLabel;
    private Label textLabel;
    private Label scoreLabel;
    private Label highScoreLabel;


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

        titleLabel = new Label("Game over", skin, "title");
        textLabel = new Label("Better luck next time!", skin, "default");
        scoreLabel = new Label("Score: " + Player.getScore(), skin, "default");
        highScoreLabel = new Label("High score: " + Game.localStorage.getHighScore(), skin, "default");
        Label.LabelStyle labelStyleBig =  titleLabel.getStyle();
        labelStyleBig.font = Asset.fontBig;
        titleLabel.setStyle(labelStyleBig);

        Label.LabelStyle labelStyleSmall =  textLabel.getStyle();
        labelStyleSmall.font = Asset.fontSmall;
        textLabel.setStyle(labelStyleSmall);
        scoreLabel.setStyle(labelStyleSmall);
        highScoreLabel.setStyle(labelStyleSmall);

        // return to main screen button
        final TextButton retryButton = new TextButton("Retry", skin);
        TextButton.TextButtonStyle buttonStyle =  retryButton.getStyle();
        buttonStyle.font = Asset.fontMedium;
        retryButton.setStyle(buttonStyle);
        retryButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
//                Sound.changeMusicState();
                EndScreen.this.game.changeScreen(Screens.LEVEL1SCREEN);

            }
        });

        final TextButton backButton = new TextButton("Menu", skin);
        backButton.setStyle(buttonStyle);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
//                Sound.changeMusicState();
                EndScreen.this.game.changeScreen(Screens.MENUSCREEN);

            }
        });



        table.defaults().width(Value.percentWidth(.100F, table));
        table.defaults().height(Value.percentHeight(.10F, table));

        table.add(titleLabel).center().width(Value.percentWidth(.60F, table));
        table.row().padTop(Value.percentWidth(.3F, table));
        table.add(textLabel).center().width(Value.percentWidth(.80F, table));
        table.row().padTop(Value.percentWidth(.05F, table));
        table.add(scoreLabel).center().width(Value.percentWidth(.80F, table));
        table.row().padTop(Value.percentWidth(.025F, table));
        table.add(highScoreLabel).center().width(Value.percentWidth(.80F, table));
        table.row().padTop(Value.percentWidth(.15F, table));
        table.add(retryButton).center().width(Value.percentWidth(.50F, table));
        table.row().padTop(Value.percentWidth(.1F, table));
        table.add(backButton).center().width(Value.percentWidth(.50F, table));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        scoreLabel.setText("Score: " + Player.getScore());
        highScoreLabel.setText("High score: " + Game.localStorage.getHighScore());
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
