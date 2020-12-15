package tk.sebastjanmevlja.doodlejump.Level;


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
import tk.sebastjanmevlja.doodlejump.Gameplay.Asset;
import tk.sebastjanmevlja.doodlejump.Gameplay.Constants;
import tk.sebastjanmevlja.doodlejump.MyGame.Game;

public class PreferencesScreen implements Screen {

    private Game game;
    private Stage stage;
    private Label titleLabel;
    private Label volumeMusicLabel;
    private Label volumeSoundLabel;
    private Label musicOnOffLabel;
    private Label soundOnOffLabel;
    private Skin skin;


    public PreferencesScreen(final Game game) {
        this.game = game;
        /// create stage and set it as input processor
        stage = new Stage(new ScreenViewport());
        skin = Asset.skin;

        // Create a table that fills the screen. Everything else will go inside
        // this table.
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);


        // music volume
        final Slider volumeMusicSlider = new Slider(0f, 1f, 0.1f, false, skin);
//        volumeMusicSlider.setValue(Sound.musicVolume);
//        volumeMusicSlider.getStyle().background.setMinHeight(GameInfo.HEIGHT * 0.03f);
//        volumeMusicSlider.getStyle().knob.setMinHeight(GameInfo.HEIGHT * 0.04f);
//        volumeMusicSlider.getStyle().knob.setMinWidth(GameInfo.HEIGHT * 0.04f);
//        volumeMusicSlider.addListener(new EventListener() {
//            @Override
//            public boolean handle(Event event) {
//                Main.sound.changeMusicVolume(volumeMusicSlider.getValue());
//                return false;
//            }
//        });

        // sound volume
        final Slider soundMusicSlider = new Slider(0f, 1f, 0.1f, false, skin);
//        soundMusicSlider.setValue(Sound.soundVolume);
//        soundMusicSlider.addListener(new EventListener() {
//            @Override
//            public boolean handle(Event event) {
//                Main.sound.changeSoundVolume(soundMusicSlider.getValue());
//                return false;
//            }
//        });


        // music on/off
        final CheckBox musicCheckbox = new CheckBox(null, skin);
//        musicCheckbox.setChecked(Sound.musicEnabled);
//        musicCheckbox.getStyle().checkboxOn.setMinWidth(GameInfo.WIDTH * 0.08f);
//        musicCheckbox.getStyle().checkboxOn.setMinHeight(GameInfo.WIDTH * 0.08f);
//        musicCheckbox.getStyle().checkboxOff.setMinWidth(GameInfo.WIDTH * 0.08f);
//        musicCheckbox.getStyle().checkboxOff.setMinHeight(GameInfo.WIDTH * 0.08f);
//        musicCheckbox.addListener(new EventListener() {
//            @Override
//            public boolean handle(Event event) {
//                Main.sound.setMusicEnabled(musicCheckbox.isChecked());
//                return false;
//            }
//        });


        // sound on/off
        final CheckBox soundEffectsCheckbox = new CheckBox(null, skin);
//        soundEffectsCheckbox.setChecked(Sound.soundEnabled);
//        soundEffectsCheckbox.addListener(new EventListener() {
//            @Override
//            public boolean handle(Event event) {
//                Main.sound.setSoundEnabled(soundEffectsCheckbox.isChecked());
//                return false;
//            }
//        });

        // return to main screen button
        final TextButton backButton = new TextButton("Back", skin);
        backButton.getLabel().setFontScale(Constants.WIDTH / 800f, Constants.HEIGHT/ 900f);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                PreferencesScreen.this.game.changeScreen(Screens.MENUSCREEN);

            }
        });

        titleLabel = new Label("Preferences", skin, "big");
        titleLabel.setFontScale(Constants.WIDTH / 800f, Constants.HEIGHT/ 900f);
        volumeMusicLabel = new Label(" Music Volume", skin, "big");
        volumeMusicLabel.setFontScale(Constants.WIDTH / 800f, Constants.HEIGHT/ 900f);
        volumeSoundLabel = new Label(" Sound Volume", skin, "big");
        volumeSoundLabel.setFontScale(Constants.WIDTH / 800f, Constants.HEIGHT/ 900f);
        musicOnOffLabel = new Label(" Music", skin, "big");
        musicOnOffLabel.setFontScale(Constants.WIDTH / 800f, Constants.HEIGHT/ 900f);
        soundOnOffLabel = new Label(" Sound Effect", skin, "big");
        soundOnOffLabel.setFontScale(Constants.WIDTH / 800f, Constants.HEIGHT/ 900f);


        table.defaults().width(Value.percentWidth(.60F, table));
        table.defaults().height(Value.percentHeight(.10F, table));

        table.add(titleLabel).colspan(2).center();
        table.row().pad(30, 0, 0, 10);
        table.add(volumeMusicLabel).padLeft(Value.percentWidth(.05F, table));
        table.add(volumeMusicSlider).width(Value.percentWidth(.35F, table)).right().padRight(Value.percentWidth(.05F, table));
        table.row().pad(10, 0, 0, 10);
        table.add(musicOnOffLabel).left().padLeft(Value.percentWidth(.05F, table));
        table.add(musicCheckbox).left().width(50);
        table.row().pad(10, 0, 0, 10);
        table.add(volumeSoundLabel).left().padLeft(Value.percentWidth(.05F, table));
        table.add(soundMusicSlider).width(Value.percentWidth(.35F, table)).right().padRight(Value.percentWidth(.05F, table));
        table.row().pad(10, 0, 0, 10);
        table.add(soundOnOffLabel).left().padLeft(Value.percentWidth(.05F, table));
        table.add(soundEffectsCheckbox).left().width(50);
        table.row().pad(50, 0, 0, 10);

        table.add(backButton).colspan(2);
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
        gameBatch.draw(Asset.loadingBackgroundTexture, 0, 0, Constants.WIDTH, Constants.HEIGHT);
        gameBatch.end();

        // tell our stage to do actions and draw itself
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();

    }

}
