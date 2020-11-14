package tk.sebastjanmevlja.doodlejump.Level;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import tk.sebastjanmevlja.doodlejump.Gameplay.*;
import tk.sebastjanmevlja.doodlejump.MyGame.Game;


public class Level1Screen implements Screen {

    private final Game game;
    private Player player;
    private TextureAtlas.AtlasRegion background;


    public Level1Screen(Game game) {
        this.game = game;


        player = new Player();
        background = AssetStorage.atlas.findRegion("background");
    }




    @Override
    public void show() { //create, setup method



    }

    private void update(float dt) {
        player.update(dt);

    }


    @Override
    public void render(float delta) {   //draw, loop called every frame


        Batch gameBatch = game.getBatch();

        gameBatch.begin(); //kdr zacenmo rendirat klicemo begin
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //clear the screen
        gameBatch.draw(background, 0, 0, GameInfo.WIDTH, GameInfo.HEIGHT);
        player.draw(game);


        gameBatch.end();

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
        //when we terminate our app or if we change menu, actvity
        //dispose resources

    }


}
