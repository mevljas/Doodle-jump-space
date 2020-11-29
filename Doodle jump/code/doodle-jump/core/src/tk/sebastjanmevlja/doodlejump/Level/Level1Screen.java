package tk.sebastjanmevlja.doodlejump.Level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import tk.sebastjanmevlja.doodlejump.Gameplay.*;
import tk.sebastjanmevlja.doodlejump.MyGame.Game;

import java.util.ArrayList;

import static tk.sebastjanmevlja.doodlejump.Gameplay.Constants.PPM;
import static tk.sebastjanmevlja.doodlejump.Gameplay.PlatformFactory.platforms;
import static tk.sebastjanmevlja.doodlejump.Gameplay.PlatformFactory.removePlatform;


public class Level1Screen implements Screen {

    private final Game game;
    private Stage stage;
    private Viewport viewport;
    private Player player;
    private TextureAtlas.AtlasRegion background;
    World world;
    private Input Input;
    Box2DDebugRenderer debugRenderer;
    Matrix4 debugMatrix;
    OrthographicCamera camera;


    public Level1Screen(Game game) {
        this.game = game;
        background = AssetStorage.atlas.findRegion("background");

        // Create a physics world, the heart of the simulation.  The Vector passed in is gravity
        world = new World(new Vector2(0, -5), true);

        PlatformFactory.generatePlatforms(world);
        player = new Player(AssetStorage.atlas.findRegion("player_right"), world, Constants.WIDTH / 2f,  platforms.get(0).spriteHeight() * 1.1f);

//        Generate walls
        new Walls(world);

        Input = new Input(player);

//        Set contact listener
        world.setContactListener(new WorldContactListener(player));



//       Create a Box2DDebugRenderer, this allows us to see the physics  simulation controlling the scene
        debugRenderer = new Box2DDebugRenderer();


    }




    @Override
    public void show() { //create, setup method
        camera = new OrthographicCamera(Constants.WIDTH, Constants.HEIGHT);
        viewport = new FitViewport(Constants.WIDTH, Constants.HEIGHT,camera);
        stage = new Stage(viewport);

        Gdx.input.setInputProcessor(Input);





        for (Platform platform : platforms) {
            stage.addActor(platform);
        }
        stage.addActor(player);

    }



    private void updatePlatforms(){
        ArrayList<Platform> removePlatforms = new ArrayList<>();
        for(Platform platform : platforms)
        {
            Vector3 windowCoordinates = new Vector3(0, platform.getSprite().getY(), 0);
            camera.project(windowCoordinates);
            if(windowCoordinates.y + platform.getSprite().getHeight() < 0){
                removePlatforms.add(platform);
            }

        }
        for (Platform p: removePlatforms) {
            removePlatform(p);
            p.addAction(Actions.removeActor());
            world.destroyBody(p.getBody());
            System.out.println("platform removed");
        }

        if (platforms.size() < PlatformFactory.InitiaPlatformSize / 2){
            ArrayList<Platform> list = PlatformFactory.generateMorePlatforms(world);
            for (Platform p: list) {
                stage.addActor(p);
            }
        }
    }



    @Override
    public void render(float delta) {   //draw, loop called every frame
        camera.update();
        updatePlatforms();

        // Advance the world, by the amount of time that has elapsed since the  last frame
        // Generally in a real game, dont do this in the render loop, as you are  tying the physics
        // update rate to the frame rate, and vice versa
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);



        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.getBatch().setProjectionMatrix(camera.combined);

        // Scale down the sprite batches projection matrix to box2D size
        debugMatrix = stage.getBatch().getProjectionMatrix().cpy().scale(PPM,
                PPM, 0);

        stage.act(Gdx.graphics.getDeltaTime());

        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, Constants.WIDTH, Constants.HEIGHT);
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
