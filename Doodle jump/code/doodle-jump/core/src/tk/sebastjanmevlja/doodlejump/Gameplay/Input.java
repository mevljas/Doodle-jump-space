package tk.sebastjanmevlja.doodlejump.Gameplay;

import com.badlogic.gdx.InputProcessor;
import tk.sebastjanmevlja.doodlejump.Level.Level1Screen;
import tk.sebastjanmevlja.doodlejump.Level.Screens;
import tk.sebastjanmevlja.doodlejump.MyGame.Game;

import static tk.sebastjanmevlja.doodlejump.Level.Level1Screen.pauseIcon;

public class Input implements InputProcessor {

    private Player player;
    public Input(Player player) {
        this.player = player;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case com.badlogic.gdx.Input.Keys.UP:
                player.jump();
                break;

            case com.badlogic.gdx.Input.Keys.LEFT:
                player.moveLeft();
                break;

            case com.badlogic.gdx.Input.Keys.RIGHT:
                player.moveRight();
                break;

        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case com.badlogic.gdx.Input.Keys.LEFT:
                player.moveLeft();
                break;

            case com.badlogic.gdx.Input.Keys.RIGHT:
                player.moveRight();
                break;

        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//        player.jump();
        if(pauseIcon.getBoundingRectangle().contains(screenX, Constants.HEIGHT - screenY)){
            Level1Screen.paused = true;
            Game.game.changeScreen(Screens.PAUSESCREEN);
        }
        else {
            player.createBullet(screenX, screenY);
        }


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
