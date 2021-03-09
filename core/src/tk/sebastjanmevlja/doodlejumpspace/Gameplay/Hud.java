package tk.sebastjanmevlja.doodlejumpspace.Gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import tk.sebastjanmevlja.doodlejumpspace.Helpers.Assets;
import tk.sebastjanmevlja.doodlejumpspace.Helpers.Constants;

public class Hud extends Actor {

    public static Sprite pauseIcon;
    private static final TextureAtlas.AtlasRegion playerTexture = Assets.atlas.findRegion("player_up");
    private final Label score;
    private final Label lives;
    private final ShapeRenderer shapeRenderer;


    public Hud() {
        this.score = new Label("Score: ", Assets.skin);
        this.lives = new Label("Lives: ", Assets.skin);
        this.score.setBounds(tk.sebastjanmevlja.doodlejumpspace.Helpers.Constants.WIDTH * 0.03f, tk.sebastjanmevlja.doodlejumpspace.Helpers.Constants.HEIGHT * 0.84f, tk.sebastjanmevlja.doodlejumpspace.Helpers.Constants.WIDTH * 0.2f, tk.sebastjanmevlja.doodlejumpspace.Helpers.Constants.HEIGHT * 0.2f);
        this.lives.setBounds(tk.sebastjanmevlja.doodlejumpspace.Helpers.Constants.WIDTH * 0.03f, tk.sebastjanmevlja.doodlejumpspace.Helpers.Constants.HEIGHT * 0.88f, tk.sebastjanmevlja.doodlejumpspace.Helpers.Constants.WIDTH * 0.8f, tk.sebastjanmevlja.doodlejumpspace.Helpers.Constants.HEIGHT * 0.2f);
        score.setColor(Color.BLACK);
        lives.setColor(Color.BLACK);
        Label.LabelStyle labelStyle = this.score.getStyle();
        labelStyle.font = Assets.fontHud;
        this.score.setStyle(labelStyle);
        this.lives.setStyle(labelStyle);
        pauseIcon = new Sprite(Assets.atlas.findRegion("pause"));
        pauseIcon.setSize(tk.sebastjanmevlja.doodlejumpspace.Helpers.Constants.HEIGHT * 0.05f, tk.sebastjanmevlja.doodlejumpspace.Helpers.Constants.HEIGHT * 0.05f);
        pauseIcon.setPosition(tk.sebastjanmevlja.doodlejumpspace.Helpers.Constants.WIDTH - pauseIcon.getWidth() * 1.5f, tk.sebastjanmevlja.doodlejumpspace.Helpers.Constants.HEIGHT - pauseIcon.getHeight() * 1.45f);
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.end();
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(54, 53, 52, 0.6f));
        shapeRenderer.rect(0, tk.sebastjanmevlja.doodlejumpspace.Helpers.Constants.HEIGHT * 0.9f, tk.sebastjanmevlja.doodlejumpspace.Helpers.Constants.WIDTH, tk.sebastjanmevlja.doodlejumpspace.Helpers.Constants.HEIGHT * 0.1f);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
        batch.begin();
        score.draw(batch, parentAlpha);
        lives.draw(batch, parentAlpha);
        for (int i = 1; i <= Player.lives; i++) {
            batch.draw(playerTexture, tk.sebastjanmevlja.doodlejumpspace.Helpers.Constants.WIDTH * 0.16f + i * tk.sebastjanmevlja.doodlejumpspace.Helpers.Constants.HEIGHT * 0.025f, tk.sebastjanmevlja.doodlejumpspace.Helpers.Constants.HEIGHT * 0.97f, tk.sebastjanmevlja.doodlejumpspace.Helpers.Constants.HEIGHT * 0.02f, Constants.HEIGHT * 0.02f);
        }
        pauseIcon.draw(batch);

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        score.setText("Score: " + Player.score);
    }


}
