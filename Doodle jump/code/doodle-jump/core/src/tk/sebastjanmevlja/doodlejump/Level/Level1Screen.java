package tk.sebastjanmevlja.doodlejump.Level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import tk.sebastjanmevlja.doodlejump.Gameplay.*;
import tk.sebastjanmevlja.doodlejump.Gameplay.Monster.Monster;
import tk.sebastjanmevlja.doodlejump.Gameplay.Monster.MonsterFactory;
import tk.sebastjanmevlja.doodlejump.Gameplay.Platform.Platform;
import tk.sebastjanmevlja.doodlejump.Gameplay.Platform.PlatformFactory;
import tk.sebastjanmevlja.doodlejump.MyGame.Game;

import java.util.ArrayList;

import static tk.sebastjanmevlja.doodlejump.Gameplay.Constants.PPM;
import static tk.sebastjanmevlja.doodlejump.Gameplay.Monster.MonsterFactory.monsters;
import static tk.sebastjanmevlja.doodlejump.Gameplay.Monster.MonsterFactory.removeMonster;
import static tk.sebastjanmevlja.doodlejump.Gameplay.Platform.PlatformFactory.*;


public class Level1Screen implements Screen {

    private static Stage stage;
    private final Game game;
    public static Group backgroundGroup;        // Group to be draw first
    public static Group middleGroup;
    Group foregroundGroup;
    Group hudGroup;        // group to be draw last
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
    public static Sprite pauseIcon;
    public static Boolean paused;



    public Level1Screen(Game game) {
        this.game = game;

         backgroundGroup = new Group();        // Group to be draw first
         middleGroup = new Group();
         foregroundGroup = new Group();
         hudGroup = new Group();


        camera = new OrthographicCamera(Constants.WIDTH, Constants.HEIGHT);
        viewport = new FitViewport(Constants.WIDTH, Constants.HEIGHT,camera);
        stage = new Stage(viewport);

        background = Asset.atlas.findRegion("background");

        // Create a physics world, the heart of the simulation.  The Vector passed in is gravity
        world = new World(new Vector2(0, - Constants.HEIGHT * 0.0036f), true);
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

        pauseIcon = new Sprite(Asset.pauseTexture);
        pauseIcon.setPosition(Constants.WIDTH * 0.02f, Constants.HEIGHT * 0.91f);
        pauseIcon.setSize(Constants.WIDTH * 0.1f, Constants.WIDTH * 0.1f);






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

        loadObjects();

    }




    @Override
    public void show() { //create, setup method

        Gdx.input.setInputProcessor(tk.sebastjanmevlja.doodlejump.Gameplay.Input.im);

        Sound.playStartSound();

        paused = false;
    }



    private void updatePlatforms(){
        ArrayList<Platform> removePlatforms = new ArrayList<>();
        for(Platform platform : platforms)
        {
            Vector3 windowCoordinates = new Vector3(0, platform.getSprite().getY(), 0);
            camera.project(windowCoordinates);
            if(windowCoordinates.y + platform.getSprite().getHeight() < 0 || !platform.isAlive()){
                removePlatforms.add(platform);
            }

        }
        for (Platform p: removePlatforms) {
            recyclePlatform(p);
//            p.addAction(Actions.removeActor());
//            world.destroyBody(p.getBody());

//            Trampoline t = p.getTrampoline();
//            if (t != null) {
//                t.addAction(Actions.removeActor());
//                world.destroyBody(t.getBody());
//            }
//
//            Shield s = p.getShield();
//            if (s != null) {
//                s.addAction(Actions.removeActor());
//                world.destroyBody(s.getBody());
//            }

        }

//
//        if (platforms.size() < PlatformFactory.InitiaPlatformSize / 2){
//            PlatformFactory.generatePlatforms(world);
//            for (Platform p: platforms) {
//                if (p.getStage() == null){
//                    backgroundGroup.addActor(p);
//
//                }
//
//            }
//
//        }

    }


    private void updateMonsters(){
        ArrayList<Monster> removeMonsters = new ArrayList<>();
        for(Monster monster : monsters)
        {
            Vector3 windowCoordinates = new Vector3(0, monster.getSprite().getY(), 0);
            camera.project(windowCoordinates);
            if(windowCoordinates.y + monster.getSprite().getHeight() < 0 || !monster.isAlive()){
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

    private void updateBullets(){
        ArrayList<Bullet> removedBullets = new ArrayList<>();
        for (Bullet b: Player.bullets) {
            if (!b.alive){
                removedBullets.add(b);
            }
        }

        for (Bullet b: removedBullets) {
            player.removeBullet(b);
            b.addAction(Actions.removeActor());
            world.destroyBody(b.getBody());
        }
    }

    private void removeShields(){
        for (Shield s: Player.removedShields) {
            s.addAction(Actions.removeActor());
            Body b = s.getBody();
            world.destroyBody(b);
        }
        Player.removedShields.clear();
    }



    @Override
    public void render(float delta) {   //draw, loop called every frame
        if (!paused){
//            Culling.countObjects();
            checkGameState();
            camera.update();
            updatePlatforms();
            updateMonsters();
            updateBullets();
            removeShields();


            // Advance the world, by the amount of time that has elapsed since the  last frame
            // Generally in a real game, dont do this in the render loop, as you are  tying the physics
            // update rate to the frame rate, and vice versa
            world.step(Gdx.graphics.getDeltaTime(), 6, 2);
            stage.act(Gdx.graphics.getDeltaTime());
        }




        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.getBatch().setProjectionMatrix(camera.combined);

        // Scale down the sprite batches projection matrix to box2D size
        debugMatrix = stage.getBatch().getProjectionMatrix().cpy().scale(PPM,
                PPM, 0);



        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, Constants.WIDTH, Constants.HEIGHT);
        stage.getBatch().end();


        stage.draw();


        stage.getBatch().begin();
        pauseIcon.draw(stage.getBatch());
        stage.getBatch().end();

//        Display number of drawn objects.
//        printCullingStatus();


        // Now render the physics world using our scaled down matrix
        // Note, this is strictly optional and is, as the name suggests, just  for debugging purposes
        debugRenderer.render(world, debugMatrix);



    }


    private void printCullingStatus(){
        System.out.println("Objects drawn: " + Culling.getDrawnObjectsCounter() + "/" + Culling.getAllObjectsCounter());
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {
        saveObjects();
        paused = !paused;

    }


    @Override
    public void resume() {
        paused = false;
        loadObjects();
    }

    @Override
    public void hide() {
        paused = true;
        saveObjects();
    }

    @Override
    public void dispose() {
//        saveObjects();
        stage.dispose();
        world.dispose();

    }

    private void checkGameState(){
        if (Player.getLives() <= 0){
            if (Player.getScore() > Game.localStorage.getHighScore())
                Game.localStorage.setHighScore(Player.getScore());
            Game.localStorage.setSavedData(false);
            game.changeScreen(Screens.ENDSCREEN);
        }
    }

    public static Stage getStage() {
        return stage;
    }


    private void saveObjects(){
        if (Player.getScore() == 0 || Player.getLives() <= 0){
            return;
        }
        Game.localStorage.setScore(Player.getScore());
        Game.localStorage.setLives(Player.getLives());
        Game.localStorage.setSavedData(true);
    }

    private void loadObjects(){
        if ( Game.localStorage.getSavedData()){
            Player.score = Game.localStorage.getScore();
            Player.lives = Game.localStorage.getLives();
            Game.localStorage.setScore(0);
            Game.localStorage.setLives(5);
            Game.localStorage.setSavedData(false);
        }

    }



}
