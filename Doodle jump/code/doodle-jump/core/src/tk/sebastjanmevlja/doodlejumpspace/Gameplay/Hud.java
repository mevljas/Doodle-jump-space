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

public class Hud extends Actor {

    public static Sprite pauseIcon;
    private static final TextureAtlas.AtlasRegion playerTexture = Asset.atlas.findRegion("player_up");
    private final Label score;
    private final Label lives;
    private final ShapeRenderer shapeRenderer;


    public Hud() {
        this.score = new Label("Score: ", Asset.skin);
        this.lives = new Label("Lives: ", Asset.skin);
        this.score.setBounds(Constants.WIDTH * 0.03f, Constants.HEIGHT * 0.84f, Constants.WIDTH * 0.2f, Constants.HEIGHT * 0.2f);
        this.lives.setBounds(Constants.WIDTH * 0.03f, Constants.HEIGHT * 0.88f, Constants.WIDTH * 0.8f, Constants.HEIGHT * 0.2f);
        score.setColor(Color.BLACK);
        lives.setColor(Color.BLACK);
        Label.LabelStyle labelStyle = this.score.getStyle();
        labelStyle.font = Asset.fontHud;
        this.score.setStyle(labelStyle);
        this.lives.setStyle(labelStyle);
        pauseIcon = new Sprite(Asset.atlas.findRegion("pause"));
        pauseIcon.setSize(Constants.HEIGHT * 0.05f, Constants.HEIGHT * 0.05f);
        pauseIcon.setPosition(Constants.WIDTH - pauseIcon.getWidth() * 1.5f, Constants.HEIGHT - pauseIcon.getHeight() * 1.45f);
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
        shapeRenderer.rect(0, Constants.HEIGHT * 0.9f, Constants.WIDTH, Constants.HEIGHT * 0.1f);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
        batch.begin();
        score.draw(batch, parentAlpha);
        lives.draw(batch, parentAlpha);
        for (int i = 1; i <= Player.lives; i++) {
            batch.draw(playerTexture, Constants.WIDTH * 0.13f + i * Constants.HEIGHT * 0.025f, Constants.HEIGHT * 0.97f, Constants.HEIGHT * 0.02f, Constants.HEIGHT * 0.02f);
        }
        pauseIcon.draw(batch);

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        score.setText("Score: " + Player.score);
    }


}
