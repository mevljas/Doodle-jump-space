package tk.sebastjanmevlja.doodlejumpspace.Screen;


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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import tk.sebastjanmevlja.doodlejumpspace.Helpers.Assets;
import tk.sebastjanmevlja.doodlejumpspace.Helpers.Constants;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Sound;
import tk.sebastjanmevlja.doodlejumpspace.MyGame.Game;

public class PreferencesScreen implements Screen {

    private final Game game;
    private final Stage stage;


    public PreferencesScreen(final Game game) {
        this.game = game;
        /// create stage and set it as input processor
        stage = new Stage(new ScreenViewport());
        Skin skin = Assets.skin;

        // Create a table that fills the screen. Everything else will go inside
        // this table.
        Table table = new Table();
        table.setFillParent(true);
        table.align(Align.center);
        stage.addActor(table);


        // music volume
        final Slider volumeMusicSlider = new Slider(0f, 1f, 0.1f, false, skin);
        volumeMusicSlider.setValue(Sound.musicVolume);
//        volumeMusicSlider.getStyle().background.setMinHeight(Constants.HEIGHT * 0.01f);
        volumeMusicSlider.getStyle().knob.setMinHeight(Constants.HEIGHT * 0.01f);
        volumeMusicSlider.getStyle().knob.setMinWidth(Constants.HEIGHT * 0.01f);
        volumeMusicSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                Sound.changeMusicVolume(volumeMusicSlider.getValue());
                return false;
            }
        });

        // sound volume
        final Slider soundMusicSlider = new Slider(0f, 1f, 0.1f, false, skin);
        volumeMusicSlider.setValue(Sound.musicVolume);
//        volumeMusicSlider.getStyle().background.setMinHeight(Constants.HEIGHT * 0.03f);
        volumeMusicSlider.getStyle().knob.setMinHeight(Constants.HEIGHT * 0.04f);
        volumeMusicSlider.getStyle().knob.setMinWidth(Constants.HEIGHT * 0.04f);
        volumeMusicSlider.getStyle().knobDown.setMinHeight(Constants.HEIGHT * 0.04f);
        volumeMusicSlider.getStyle().knobDown.setMinWidth(Constants.HEIGHT * 0.04f);
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
        musicCheckbox.getStyle().checkboxOn.setMinWidth(Constants.HEIGHT * 0.04f);
        musicCheckbox.getStyle().checkboxOn.setMinHeight(Constants.HEIGHT * 0.04f);
        musicCheckbox.getStyle().checkboxOff.setMinWidth(Constants.HEIGHT * 0.04f);
        musicCheckbox.getStyle().checkboxOff.setMinHeight(Constants.HEIGHT * 0.04f);
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
        TextButton.TextButtonStyle textButtonStyle = backButton.getStyle();
        textButtonStyle.font = Assets.fontMedium;
        backButton.setStyle(textButtonStyle);

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                PreferencesScreen.this.game.changeScreen(Screens.MENUSCREEN);

            }
        });

        Label titleLabel = new Label("Preferences", skin, "title");
        Label volumeMusicLabel = new Label(" Music Volume", skin, "default");
        Label volumeSoundLabel = new Label(" Sound Volume", skin, "default");
        Label musicOnOffLabel = new Label(" Music", skin, "default");
        Label soundOnOffLabel = new Label(" Sound Effect", skin, "default");

        Label.LabelStyle labelStyleTitle = titleLabel.getStyle();
        labelStyleTitle.font = Assets.fontBig;
        titleLabel.setStyle(labelStyleTitle);
        titleLabel.setAlignment(Align.center);

        Label.LabelStyle labelStyleText = titleLabel.getStyle();
        labelStyleText.font = Assets.fontSmall;
        volumeMusicLabel.setStyle(labelStyleText);
        volumeSoundLabel.setStyle(labelStyleText);
        musicOnOffLabel.setStyle(labelStyleText);
        soundOnOffLabel.setStyle(labelStyleText);

        table.defaults().width(Value.percentWidth(.50F, table));
        table.defaults().height(Value.percentHeight(.10F, table));

        table.add(titleLabel).colspan(2).center();
        table.row().padTop(Value.percentHeight(.1F, table));
        table.add(volumeMusicLabel).left();
        table.add(volumeMusicSlider).width(Value.percentWidth(.45F, table)).left();
        table.row().padTop(Value.percentHeight(.01F, table));
        table.add(musicOnOffLabel).left();
        table.add(musicCheckbox).right();
        table.row().padTop(Value.percentHeight(.01F, table));
        table.add(volumeSoundLabel).left();
        table.add(soundMusicSlider).width(Value.percentWidth(.45F, table)).left();
        table.row().padTop(Value.percentHeight(.01F, table));
        table.add(soundOnOffLabel).left();
        table.add(soundEffectsCheckbox).right();
        table.row().padTop(Value.percentHeight(.1F, table));

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
        gameBatch.draw(Assets.background, 0, 0, Constants.WIDTH, Constants.HEIGHT);
        gameBatch.end();

        // tell our stage to do actions and draw itself
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

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
        stage.dispose();

    }

}
