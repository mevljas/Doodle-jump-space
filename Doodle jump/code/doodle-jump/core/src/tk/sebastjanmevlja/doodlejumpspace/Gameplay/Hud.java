package tk.sebastjanmevlja.doodlejumpspace.Gameplay;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class Hud extends Actor {

    private static TextureAtlas.AtlasRegion playerTexture = Asset.atlas.findRegion("player_up");

    private Label score;
    private Label lives;
    public static Sprite pauseIcon;


    public Hud() {
        this.score = new Label("Score: ", Asset.skin);
        this.lives = new Label("Lives: ", Asset.skin);
        this.score.setBounds(Constants.WIDTH * 0.03f, Constants.HEIGHT * 0.82f, Constants.WIDTH * 0.2f, Constants.HEIGHT * 0.2f);
        this.lives.setBounds(Constants.WIDTH * 0.03f, Constants.HEIGHT * 0.86f, Constants.WIDTH * 0.8f, Constants.HEIGHT * 0.2f);
        score.setColor(Color.BLACK);
        lives.setColor(Color.BLACK);
        Label.LabelStyle labelStyle =  this.score.getStyle();
        labelStyle.font = Asset.fontHud;
        this.score.setStyle(labelStyle);
        this.lives.setStyle(labelStyle);
        pauseIcon = new Sprite(Asset.pauseTexture);
        pauseIcon.setSize(Constants.HEIGHT * 0.05f, Constants.HEIGHT * 0.05f);
        pauseIcon.setPosition(Constants.WIDTH - pauseIcon.getWidth() * 1.5f, Constants.HEIGHT - pauseIcon.getHeight() * 1.5f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        score.draw(batch, parentAlpha);
        lives.draw(batch, parentAlpha);
        for (int i = 1; i <= Player.lives; i++) {
            batch.draw(playerTexture, Constants.WIDTH * 0.12f + i * Constants.HEIGHT * 0.025f, Constants.HEIGHT * 0.945f, Constants.HEIGHT * 0.02f, Constants.HEIGHT * 0.02f);
        }
        pauseIcon.draw(batch);

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        score.setText("Score: " +  Player.score);
    }


}
