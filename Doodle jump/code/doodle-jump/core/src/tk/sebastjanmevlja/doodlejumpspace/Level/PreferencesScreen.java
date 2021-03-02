package tk.sebastjanmevlja.doodlejumpspace.Level;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Asset;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Constants;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Sound;
import tk.sebastjanmevlja.doodlejumpspace.MyGame.Game;

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
        volumeMusicSlider.setValue(Sound.musicVolume);
        volumeMusicSlider.getStyle().background.setMinHeight(Constants.HEIGHT * 0.03f);
        volumeMusicSlider.getStyle().knob.setMinHeight(Constants.HEIGHT * 0.04f);
        volumeMusicSlider.getStyle().knob.setMinWidth(Constants.HEIGHT * 0.04f);
        volumeMusicSlider.setWidth(1);
        volumeMusicSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                Sound.changeMusicVolume(volumeMusicSlider.getValue());
                return false;
            }
        });

        // sound volume
        final Slider soundMusicSlider = new Slider(0f, 1f, 0.1f, false, skin);
        soundMusicSlider.setValue(Sound.soundVolume);
        soundMusicSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                Sound.changeSoundVolume(soundMusicSlider.getValue());
                return false;
            }
        });


        // music on/off
        final CheckBox musicCheckbox = new CheckBox(null, skin);
        musicCheckbox.setChecked(Sound.musicEnabled);
        musicCheckbox.getStyle().checkboxOn.setMinWidth(Constants.WIDTH * 0.08f);
        musicCheckbox.getStyle().checkboxOn.setMinHeight(Constants.WIDTH * 0.08f);
        musicCheckbox.getStyle().checkboxOff.setMinWidth(Constants.WIDTH * 0.08f);
        musicCheckbox.getStyle().checkboxOff.setMinHeight(Constants.WIDTH * 0.08f);
        musicCheckbox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                Sound.setMusicEnabled(musicCheckbox.isChecked());
                return false;
            }
        });


        // sound on/off
        final CheckBox soundEffectsCheckbox = new CheckBox(null, skin);
        soundEffectsCheckbox.setChecked(Sound.soundEnabled);
        soundEffectsCheckbox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                Sound.setSoundEnabled(soundEffectsCheckbox.isChecked());
                return false;
            }
        });

        // return to main screen button
        final TextButton backButton = new TextButton("Back", skin);
        TextButton.TextButtonStyle textButtonStyle =  backButton.getStyle();
        textButtonStyle.font = Asset.fontMedium;
        backButton.setStyle(textButtonStyle);

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                PreferencesScreen.this.game.changeScreen(Screens.MENUSCREEN);

            }
        });

        titleLabel = new Label("Preferences", skin, "title");
        volumeMusicLabel = new Label(" Music Volume", skin, "default");
        volumeSoundLabel = new Label(" Sound Volume", skin, "default");
        musicOnOffLabel = new Label(" Music", skin, "default");
        soundOnOffLabel = new Label(" Sound Effect", skin, "default");

        Label.LabelStyle labelStyleTitle =  titleLabel.getStyle();
        labelStyleTitle.font = Asset.fontBig;
        titleLabel.setStyle(labelStyleTitle);

        Label.LabelStyle labelStyleText =  titleLabel.getStyle();
        labelStyleText.font = Asset.fontSmall;
        volumeMusicLabel.setStyle(labelStyleText);
        volumeSoundLabel.setStyle(labelStyleText);
        musicOnOffLabel.setStyle(labelStyleText);
        soundOnOffLabel.setStyle(labelStyleText);

        table.defaults().width(Value.percentWidth(.5F, table));
        table.defaults().height(Value.percentHeight(.1F, table));

        table.add(titleLabel).colspan(2).center().padRight(Value.percentWidth(.1F, table));
        table.row().padTop(Value.percentWidth(.1F, table));
        table.add(volumeMusicLabel).left();
        table.add(volumeMusicSlider).right();
        table.row().padTop(Value.percentWidth(.01F, table));
        table.add(musicOnOffLabel).left();
        table.add(musicCheckbox).right();
        table.row().padTop(Value.percentWidth(.01F, table));
        table.add(volumeSoundLabel).left();
        table.add(soundMusicSlider).right();
        table.row().padTop(Value.percentWidth(.01F, table));
        table.add(soundOnOffLabel).left();
        table.add(soundEffectsCheckbox).right();
        table.row().padTop(Value.percentWidth(.1F, table));

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
