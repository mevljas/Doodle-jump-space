package tk.sebastjanmevlja.doodlejump.Gameplay;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

public class Input implements InputProcessor {

    private Player player;
    public Input(Player player) {
        this.player = player;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
//            case com.badlogic.gdx.Input.Keys.UP:
//                player.moveUp();
//                break;

            case com.badlogic.gdx.Input.Keys.LEFT:
                player.body.applyForceToCenter(new Vector2(-5, 0), true);
                break;

            case com.badlogic.gdx.Input.Keys.RIGHT:
                player.body.applyForceToCenter(new Vector2(5, 0), true);
                break;

        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        player.moveUp();
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
