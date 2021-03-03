package tk.sebastjanmevlja.doodlejumpspace.Gameplay;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

import tk.sebastjanmevlja.doodlejumpspace.Level.Level1Screen;
import tk.sebastjanmevlja.doodlejumpspace.Level.Screens;
import tk.sebastjanmevlja.doodlejumpspace.MyGame.Game;


public class Input implements InputProcessor, GestureDetector.GestureListener {

    private Player player;
    public static InputMultiplexer im;
    public Input(Player player) {
        this.player = player;
        im = new InputMultiplexer();
        GestureDetector gd = new GestureDetector(this);
        im.addProcessor(gd);
        im.addProcessor(this);



    }

    @Override
    public boolean keyDown(int keycode) {
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
        if(Hud.pauseIcon.getBoundingRectangle().contains(screenX, Constants.HEIGHT - screenY)){
            Level1Screen.paused = true;
            Game.game.changeScreen(Screens.PAUSESCREEN);
            if (player.getJetpack() != null){
                Sound.stopJetpackSound();
            }
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

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        if (count == 2) {
            Sound.playStartSound();
            return true;
        }
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }
}
