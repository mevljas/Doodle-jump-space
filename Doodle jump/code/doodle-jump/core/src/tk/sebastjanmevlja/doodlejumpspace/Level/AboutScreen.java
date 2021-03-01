package tk.sebastjanmevlja.doodlejumpspace.Level;


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
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Asset;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Constants;
import tk.sebastjanmevlja.doodlejumpspace.MyGame.Game;


public class AboutScreen implements Screen {

    private Game game;
    private Stage stage;


    public AboutScreen(final Game game) {
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


        // return to main screen button
        final TextButton backButton = new TextButton("Back", skin);
        final TextButton retryButton = new TextButton("Continue", skin);
        TextButton.TextButtonStyle textButtonStyle =  retryButton.getStyle();
        textButtonStyle.font = Asset.fontMedium;
        retryButton.setStyle(textButtonStyle);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                AboutScreen.this.game.changeScreen(Screens.MENUSCREEN);

            }
        });

        Label titleLabel = new Label("About", skin, "title");
        Label.LabelStyle LabelStyleBig =  titleLabel.getStyle();
        LabelStyleBig.font = Asset.fontBig;
        titleLabel.setStyle(LabelStyleBig);


        Label versionLable = new Label("Version: 0.5.0", skin, "default");
        Label authorLable = new Label("Author: Sebastjan Mevlja", skin, "default");
        Label.LabelStyle LabelStyleSmall =  titleLabel.getStyle();
        LabelStyleSmall.font = Asset.fontSmall;
        versionLable.setStyle(LabelStyleSmall);
        authorLable.setStyle(LabelStyleSmall);


        table.defaults().width(Value.percentWidth(.90F, table));
        table.defaults().height(Value.percentHeight(.10F, table));

        table.add(titleLabel).center().width(Value.percentWidth(.30F, table));
        table.row().padTop(Value.percentHeight(.1F, table));
        table.add(new Image(Asset.atlas.findRegion("logo"))).width(Value.percentWidth(.40F, table)).height(Value.percentWidth(.40F, table)).center();
        table.row().padTop(Value.percentHeight(.1F, table));
        table.add(versionLable).width(Value.percentWidth(.90F, table));
        table.row();
        table.add(authorLable).width(Value.percentWidth(.90F, table));
        table.row().padTop(Value.percentHeight(.1F, table));
        table.add(backButton).center().width(Value.percentWidth(.50F, table));
    }

    @Override
    public void show() {
        //stage.clear();
        Gdx.input.setInputProcessor(stage);


    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            game.changeScreen(Screens.MENUSCREEN);
        }

        Batch gameBatch = game.getBatch();

        gameBatch.begin(); //kdr zacenmo rendirat klicemo begin
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameBatch.draw(Asset.background, 0, 0, Constants.WIDTH, Constants.HEIGHT);
        gameBatch.end();

        // tell our stage to do actions and draw itself
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
    }

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
