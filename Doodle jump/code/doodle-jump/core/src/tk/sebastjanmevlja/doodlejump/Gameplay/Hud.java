package tk.sebastjanmevlja.doodlejump.Gameplay;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class Hud extends Actor {

    private static TextureAtlas.AtlasRegion playerTexture = Asset.atlas.findRegion("player_left");

    private Label score;
    private Label lives;


    public Hud() {
        this.score = new Label("Score: ", Asset.skin);
        this.lives = new Label("Lives: ", Asset.skin);
        this.score.setBounds(Constants.WIDTH * 0.7f, Constants.HEIGHT * 0.88f, Constants.WIDTH * 0.2f, Constants.HEIGHT * 0.2f);
        this.lives.setBounds(Constants.WIDTH * 0.03f, Constants.HEIGHT * 0.88f, Constants.WIDTH * 0.8f, Constants.HEIGHT * 0.2f);
        score.setColor(Color.BLACK);
        lives.setColor(Color.BLACK);
        Label.LabelStyle labelStyle =  this.score.getStyle();
        labelStyle.font = Asset.fontHud;
        this.score.setStyle(labelStyle);
        this.lives.setStyle(labelStyle);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        score.draw(batch, parentAlpha);
        lives.draw(batch, parentAlpha);
        for (int i = 1; i <= Player.lives; i++) {
            batch.draw(playerTexture, Constants.WIDTH * 0.14f + i * Constants.WIDTH * 0.06f, Constants.HEIGHT * 0.965f, Constants.WIDTH * 0.06f, Constants.HEIGHT * 0.04f);
        }

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        score.setText("Score: " +  Player.score);
    }


}
