package tk.sebastjanmevlja.doodlejump.Level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Batch;
import tk.sebastjanmevlja.doodlejump.Gameplay.AssetManager;
import tk.sebastjanmevlja.doodlejump.Gameplay.GameInfo;
import tk.sebastjanmevlja.doodlejump.Gameplay.Platform;
import tk.sebastjanmevlja.doodlejump.MyGame.Main;
import tk.sebastjanmevlja.doodlejump.Gameplay.Player;


public class Level1Screen implements Screen {

    private Main main;
    private Player player;
    private Platform platform;


    public Level1Screen(Main main) {
        this.main = main;
    }

    @Override
    public void show() { //create, setup method

        player = new Player();
        platform = new Platform();

    }

    private void update(float dt) {
        player.update(dt);
        platform.update(dt);

    }


    @Override
    public void render(float delta) {   //draw, loop called every frame


        Batch gameBatch = main.getBatch();

        gameBatch.begin(); //kdr zacenmo rendirat klicemo begin
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //clear the screen
        gameBatch.draw(AssetManager.mainAtlas.findRegion("background"), 0, 0, GameInfo.WIDTH, GameInfo.HEIGHT);
        player.draw(main);
        platform.draw(main);





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
