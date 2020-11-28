package tk.sebastjanmevlja.doodlejump.Level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import tk.sebastjanmevlja.doodlejump.Gameplay.*;
import tk.sebastjanmevlja.doodlejump.MyGame.Game;


public class Level1Screen implements Screen {

    private final Game game;
    private Stage stage;
    private Viewport viewport;
    private Player player;
    private Platform platform;
    private TextureAtlas.AtlasRegion background;
    World world;
    private Input Input;
    Box2DDebugRenderer debugRenderer;
    Matrix4 debugMatrix;


    public Level1Screen(Game game) {
        this.game = game;
        background = AssetStorage.atlas.findRegion("background");

        // Create a physics world, the heart of the simulation.  The Vector passed in is gravity
        world = new World(new Vector2(0, -30f), true);

        player = new Player(AssetStorage.atlas.findRegion("player_right"), world);
        platform = new Platform(AssetStorage.atlas.findRegion("platform_green"), world);

        Input = new Input(player);

//       Create a Box2DDebugRenderer, this allows us to see the physics  simulation controlling the scene
        debugRenderer = new Box2DDebugRenderer();
    }




    @Override
    public void show() { //create, setup method
        viewport = new FitViewport(GameInfo.WIDTH, GameInfo.HEIGHT);
        stage = new Stage(viewport);

        Gdx.input.setInputProcessor(Input);





        stage.addActor(player);
        stage.addActor(platform);



    }




    @Override
    public void render(float delta) {   //draw, loop called every frame
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Advance the world, by the amount of time that has elapsed since the  last frame
        // Generally in a real game, dont do this in the render loop, as you are  tying the physics
        // update rate to the frame rate, and vice versa
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);



        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Scale down the sprite batches projection matrix to box2D size
        debugMatrix = stage.getBatch().getProjectionMatrix().cpy().scale(1,
                1, 0);

        stage.act(Gdx.graphics.getDeltaTime());

        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, GameInfo.WIDTH, GameInfo.HEIGHT);
        stage.getBatch().end();


        stage.draw();


        // Now render the physics world using our scaled down matrix
        // Note, this is strictly optional and is, as the name suggests, just  for debugging purposes
        debugRenderer.render(world, debugMatrix);



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
        world.dispose();

    }


}
