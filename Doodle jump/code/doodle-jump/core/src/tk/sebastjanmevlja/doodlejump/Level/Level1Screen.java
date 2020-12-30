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
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import tk.sebastjanmevlja.doodlejump.Gameplay.*;
import tk.sebastjanmevlja.doodlejump.MyGame.Game;

import java.util.ArrayList;

import static tk.sebastjanmevlja.doodlejump.Gameplay.Constants.PPM;
import static tk.sebastjanmevlja.doodlejump.Gameplay.MonsterFactory.monsters;
import static tk.sebastjanmevlja.doodlejump.Gameplay.MonsterFactory.removeMonster;
import static tk.sebastjanmevlja.doodlejump.Gameplay.PlatformFactory.platforms;
import static tk.sebastjanmevlja.doodlejump.Gameplay.PlatformFactory.removePlatform;


public class Level1Screen implements Screen {

    private final Game game;
    private Stage stage;
    Group backgroundGroup = new Group();        // Group to be draw first
    Group middleGroup = new Group();
    Group foregroundGroup = new Group();
    Group hudGroup = new Group();        // group to be draw last
    private Viewport viewport;
    private PlatformFactory platformFactory;
    private MonsterFactory monsterFactory;
    private Player player;
    private Hud hud;
    private TextureAtlas.AtlasRegion background;
    World world;
    private Input Input;
    Box2DDebugRenderer debugRenderer;
    Matrix4 debugMatrix;
    OrthographicCamera camera;


    public Level1Screen(Game game) {
        this.game = game;
        background = Asset.atlas.findRegion("background");

        // Create a physics world, the heart of the simulation.  The Vector passed in is gravity
        world = new World(new Vector2(0, -5), true);
        platformFactory = new PlatformFactory();
        PlatformFactory.generatePlatforms(world);
        monsterFactory = new MonsterFactory();
        MonsterFactory.generateMonsters(world);
        player = new Player( world, Constants.WIDTH / 2f,  platforms.get(0).spriteHeight() * 1.1f);
        hud = new Hud();

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

        stage.addActor(backgroundGroup);
        stage.addActor(middleGroup);
        stage.addActor(foregroundGroup);
        stage.addActor(hudGroup);



        for (Platform platform : platforms) {
            backgroundGroup.addActor(platform);
        }

        for (Monster monster : monsters) {
            middleGroup.addActor(monster);
        }


        foregroundGroup.addActor(player);
        hudGroup.addActor(hud);

        Sound.playStartSound();


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
        }

        if (platforms.size() < PlatformFactory.InitiaPlatformSize / 2){
            PlatformFactory.generatePlatforms(world);
            for (Platform p: platforms) {
                if (p.getStage() == null){{
                    backgroundGroup.addActor(p);

                }}

            }

        }
    }


    private void updateMonsters(){
        ArrayList<Monster> removeMonsters = new ArrayList<>();
        for(Monster monster : monsters)
        {
            Vector3 windowCoordinates = new Vector3(0, monster.getSprite().getY(), 0);
            camera.project(windowCoordinates);
            if(windowCoordinates.y + monster.getSprite().getHeight() < 0){
                removeMonsters.add(monster);
            }

        }
        for (Monster m: removeMonsters) {
            removeMonster(m);
            m.addAction(Actions.removeActor());
            world.destroyBody(m.getBody());
        }

        if (monsters.size() < MonsterFactory.InitiaMonsterSize / 2){
            MonsterFactory.generateMonsters(world);
            for (Monster m: monsters) {
                if (m.getStage() == null){{
                    middleGroup.addActor(m);

                }}

            }

        }
    }



    @Override
    public void render(float delta) {   //draw, loop called every frame
        checkGameState();
        camera.update();
        updatePlatforms();
        updateMonsters();


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
//        debugRenderer.render(world, debugMatrix);



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

    private void checkGameState(){
        if (Player.getLives() <= 0){
            game.changeScreen(Screens.ENDSCREEN);
        }
    }



}
