package tk.sebastjanmevlja.doodlejump.Level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import tk.sebastjanmevlja.doodlejump.Gameplay.AssetStorage;
import tk.sebastjanmevlja.doodlejump.Gameplay.GameInfo;
import tk.sebastjanmevlja.doodlejump.Gameplay.Player;
import tk.sebastjanmevlja.doodlejump.MyGame.Game;


public class Level1Screen implements Screen {

    private final Game game;
    private Stage stage;
    private Viewport viewport;
    private Player player;
    private TextureAtlas.AtlasRegion background;


    public Level1Screen(Game game) {
        this.game = game;
        background = AssetStorage.atlas.findRegion("background");
    }




    @Override
    public void show() { //create, setup method
        viewport = new FitViewport(GameInfo.WIDTH, GameInfo.HEIGHT);
        stage = new Stage(viewport);

        Gdx.input.setInputProcessor(stage);


        player = new Player(AssetStorage.atlas.findRegion("player_right"));


        stage.addActor(player);



    }




    @Override
    public void render(float delta) {   //draw, loop called every frame


        stage.act(Gdx.graphics.getDeltaTime());

        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, GameInfo.WIDTH, GameInfo.HEIGHT);
        stage.getBatch().end();


        stage.draw();

    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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
