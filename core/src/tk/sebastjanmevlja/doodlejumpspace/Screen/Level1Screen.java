package tk.sebastjanmevlja.doodlejumpspace.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Bullet;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Enemies.Enemy;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Enemies.EnemyFactory;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Hud;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.InputListener;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Jetpack;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Planets.Planet;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Planets.PlanetFactory;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Platforms.Platform;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Platforms.PlatformFactory;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Player;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Shield;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Sound;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.WorldContactListener;
import tk.sebastjanmevlja.doodlejumpspace.Helpers.Assets;
import tk.sebastjanmevlja.doodlejumpspace.Helpers.Constants;
import tk.sebastjanmevlja.doodlejumpspace.MyGame.Game;

import static tk.sebastjanmevlja.doodlejumpspace.Gameplay.Enemies.EnemyFactory.enemies;
import static tk.sebastjanmevlja.doodlejumpspace.Gameplay.Enemies.EnemyFactory.removeMonster;
import static tk.sebastjanmevlja.doodlejumpspace.Gameplay.Planets.PlanetFactory.planets;
import static tk.sebastjanmevlja.doodlejumpspace.Gameplay.Planets.PlanetFactory.recyclePlanet;
import static tk.sebastjanmevlja.doodlejumpspace.Gameplay.Platforms.PlatformFactory.platforms;
import static tk.sebastjanmevlja.doodlejumpspace.Gameplay.Platforms.PlatformFactory.recyclePlatform;
import static tk.sebastjanmevlja.doodlejumpspace.Helpers.Constants.PPM;


public class Level1Screen implements Screen {

    public static Group backgroundGroup;        // Group to be draw first
    public static Group middleGroup;
    public static Boolean paused;
    private static Stage stage;
    private final Viewport viewport;
    private final Player player;
    Group foregroundGroup;
    Group hudGroup;        // group to be draw last
    World world;
    Matrix4 debugMatrix;
    OrthographicCamera camera;


    @SuppressWarnings("InstantiationOfUtilityClass")
    public Level1Screen() {

        backgroundGroup = new Group();        // Group to be draw first
        middleGroup = new Group();
        foregroundGroup = new Group();
        hudGroup = new Group();


        camera = new OrthographicCamera(Constants.WIDTH, Constants.HEIGHT);
        viewport = new FitViewport(Constants.WIDTH, Constants.HEIGHT, camera);
        stage = new Stage(viewport);

        // Create a physics world, the heart of the simulation.  The Vector passed in is gravity
        world = new World(new Vector2(0, -Constants.HEIGHT * 0.0036f), true);
        new PlatformFactory();
        PlatformFactory.generatePlatforms(world);
        new EnemyFactory();
        EnemyFactory.generateMonsters(world);
        new PlanetFactory();
        PlanetFactory.generatePlanets(world);
        player = new Player(world, Constants.WIDTH / 2f, platforms.get(0).spriteHeight() * 1.1f);
        Hud hud = new Hud();


        new InputListener(player);


//        Set contact listener
        world.setContactListener(new WorldContactListener(player));



        stage.addActor(backgroundGroup);
        stage.addActor(middleGroup);
        stage.addActor(foregroundGroup);
        stage.addActor(hudGroup);


        for (Planet planet : planets) {
            backgroundGroup.addActor(planet);
        }


        for (Platform platform : platforms) {
            backgroundGroup.addActor(platform);
        }

        for (Enemy enemy : enemies) {
            middleGroup.addActor(enemy);
        }


        foregroundGroup.addActor(player);
        hudGroup.addActor(hud);

        loadObjects();

    }

    public static void gameOver() {
        if (Player.getScore() > Game.localStorage.getHighScore())
            Game.localStorage.setHighScore(Player.getScore());
        Game.localStorage.setSavedData(false);
        Game.game.changeScreen(Screens.ENDSCREEN);
    }

    @Override
    public void show() { //create, setup method

        Gdx.input.setInputProcessor(InputListener.im);

        Sound.playStartSound();

        paused = false;
    }

    private void updatePlatforms() {
        ArrayList<Platform> recyclePlatforms = new ArrayList<>();
        for (Platform platform : platforms) {
            Vector3 windowCoordinates = new Vector3(0, platform.getSprite().getY(), 0);
            camera.project(windowCoordinates);
            if (windowCoordinates.y + platform.getSprite().getHeight() < 0 || !platform.isAlive()) {
                recyclePlatforms.add(platform);
            }

        }
        for (Platform p : recyclePlatforms) {
            recyclePlatform(p);

        }


    }

    private void updatePlanets() {
        ArrayList<Planet> recyclePlanets = new ArrayList<>();
        for (Planet planet : planets) {
            Vector3 windowCoordinates = new Vector3(0, planet.getSprite().getY(), 0);
            camera.project(windowCoordinates);
            if (windowCoordinates.y + planet.getSprite().getHeight() < 0) {
                recyclePlanets.add(planet);
            }

        }
        for (Planet p : recyclePlanets) {
            recyclePlanet(p);

        }


    }

    private void updateEnemies() {
        ArrayList<Enemy> recycleEnemies = new ArrayList<>();
        for (Enemy enemy : enemies) {
            Vector3 windowCoordinates = new Vector3(0, enemy.getSprite().getY(), 0);
            camera.project(windowCoordinates);
            if (windowCoordinates.y + enemy.getSprite().getHeight() < 0 || !enemy.isAlive()) {
                recycleEnemies.add(enemy);
            }

        }
        for (Enemy m : recycleEnemies) {
            removeMonster();
            m.addAction(Actions.removeActor());
            world.destroyBody(m.getBody());
        }

        if (enemies.size() < EnemyFactory.numberOfEnemies / 2) {
            EnemyFactory.generateMonsters(world);
            for (Enemy m : enemies) {
                if (m.getStage() == null) {
                    {
                        middleGroup.addActor(m);

                    }
                }

            }

        }
    }

    private void updateBullets() {
        ArrayList<Bullet> removedBullets = new ArrayList<>();
        for (Bullet b : Player.bullets) {
            if (!b.alive) {
                removedBullets.add(b);
            }
        }

        for (Bullet b : removedBullets) {
            player.removeBullet(b);
            b.addAction(Actions.removeActor());
            world.destroyBody(b.getBody());
        }
    }

    private void removeShields() {
        for (Shield s : Player.removedShields) {
            try {
                s.addAction(Actions.removeActor());
                Body b = s.getBody();
                world.destroyBody(b);
            } catch (NullPointerException ignored){}

        }
        Player.removedShields.clear();
    }

    private void removeJetpacks() {
        for (Jetpack j : Player.removedJetpacks) {
            try {
                j.addAction(Actions.removeActor());
                Body b = j.getBody();
                world.destroyBody(b);
            } catch (NullPointerException ignored){}

        }
        Player.removedJetpacks.clear();
    }

    @Override
    public void render(float delta) {   //draw, loop called every frame
        if (!paused) {
            checkGameState();
            camera.update();
            updatePlatforms();
            updateEnemies();
            updateBullets();
            updatePlanets();
            removeShields();
            removeJetpacks();


            // Advance the world, by the amount of time that has elapsed since the  last frame
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
        stage.getBatch().draw(Assets.background, 0, 0, Constants.WIDTH, Constants.HEIGHT);
        stage.getBatch().end();


        stage.draw();


        stage.getBatch().begin();
        stage.getBatch().end();

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
        stage.dispose();
        world.dispose();

    }

    private void checkGameState() {
        if (Player.getLives() <= 0) {
            gameOver();
        }
    }

    private void saveObjects() {
        if (Player.getScore() == 0 || Player.getLives() <= 0) {
            return;
        }
        Game.localStorage.setScore(Player.getScore());
        Game.localStorage.setLives(Player.getLives());
        Game.localStorage.setSavedData(true);
    }

    private void loadObjects() {
        if (Game.localStorage.getSavedData()) {
            Player.score = Game.localStorage.getScore();
            Player.lives = Game.localStorage.getLives();
            Game.localStorage.setScore(0);
            Game.localStorage.setLives(5);
            Game.localStorage.setSavedData(false);
        }

    }


}
