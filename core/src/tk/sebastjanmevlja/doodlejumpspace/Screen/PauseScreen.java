package tk.sebastjanmevlja.doodlejumpspace.Screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import tk.sebastjanmevlja.doodlejumpspace.Helpers.Assets;
import tk.sebastjanmevlja.doodlejumpspace.Helpers.Constants;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Player;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Sound;
import tk.sebastjanmevlja.doodlejumpspace.MyGame.Game;

public class PauseScreen implements Screen {

    private final Game game;
    private final Stage stage;
    private final Label scoreLabel;
    private final Label highScoreLabel;


    public PauseScreen(final Game game) {
        this.game = game;

        /// create stage and set it as input processor
        stage = new Stage(new ScreenViewport());
        Skin skin = Assets.skin;

        // Create a table that fills the screen. Everything else will go inside
        // this table.
        Table table = new Table();
        table.setFillParent(true);
        table.align(Align.center);
        stage.addActor(table);

        Label titleLabel = new Label("Game over", skin, "title");
        scoreLabel = new Label("Score: " + Player.getScore(), skin, "default");
        highScoreLabel = new Label("High score: " + Game.localStorage.getHighScore(), skin, "default");
        Label.LabelStyle labelStyleBig = titleLabel.getStyle();
        labelStyleBig.font = Assets.fontBig;
        titleLabel.setStyle(labelStyleBig);
        titleLabel.setAlignment(Align.center);

        Label.LabelStyle labelStyleSmall = scoreLabel.getStyle();
        labelStyleSmall.font = Assets.fontSmall;
        scoreLabel.setStyle(labelStyleSmall);
        highScoreLabel.setStyle(labelStyleSmall);

        // return to main screen button
        final TextButton retryButton = new TextButton("Continue", skin);
        retryButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
//                Sound.changeMusicState();
                if (Player.player.getJetpack() != null) {
                    Sound.playJetpackSound();
                }
                PauseScreen.this.game.changeScreen(Screens.LEVEL1SCREEN);

            }
        });

        final TextButton backButton = new TextButton("Menu", skin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
//                Sound.changeMusicState();
                PauseScreen.this.game.changeScreen(Screens.MENUSCREEN);

            }
        });

        TextButton.TextButtonStyle textButtonStyle = retryButton.getStyle();
        textButtonStyle.font = Assets.fontMedium;
        retryButton.setStyle(textButtonStyle);
        backButton.setStyle(textButtonStyle);


        table.defaults().width(Value.percentWidth(.100F, table));
        table.defaults().height(Value.percentHeight(.10F, table));

        table.add(titleLabel).center().width(Value.percentWidth(.50F, table));
        table.row().padTop(Value.percentHeight(.1F, table));
        table.add(scoreLabel).center().width(Value.percentWidth(.80F, table));
        table.row().padTop(Value.percentHeight(.02F, table));
        table.add(highScoreLabel).center().width(Value.percentWidth(.80F, table));
        table.row().padTop(Value.percentHeight(.1F, table));
        table.add(retryButton).center().width(Value.percentWidth(.50F, table));
        table.row().padTop(Value.percentHeight(.05F, table));
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

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            Sound.changeMusicState();
            game.changeScreen(Screens.MENUSCREEN);
        }

        Batch gameBatch = game.getBatch();

        gameBatch.begin(); //kdr zacenmo rendirat klicemo begin
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameBatch.draw(Assets.background, 0, 0, Constants.WIDTH, Constants.HEIGHT);
        gameBatch.end();

        // tell our stage to do actions and draw itself
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
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
