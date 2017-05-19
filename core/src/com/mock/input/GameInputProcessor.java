package com.mock.input;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

/**
 * The event listener utilized by GameKeys.java to
 * keep track of when keys are pressed.
 * @author Trever
 */
public class GameInputProcessor implements InputProcessor {

	@Override
    public boolean keyDown(int keycode) { 
        if (keycode == Keys.LEFT) {
            GameKeys.setKey(GameKeys.LEFT, true);
        }
        if (keycode == Keys.RIGHT) {
            GameKeys.setKey(GameKeys.RIGHT, true);
        }
        if (keycode == Keys.SPACE) {
            GameKeys.setKey(GameKeys.SPACE, true);
        }
        if (keycode == Keys.UP) {
            GameKeys.setKey(GameKeys.UP, true);
        }
        if (keycode == Keys.DOWN) {
            GameKeys.setKey(GameKeys.DOWN, true);
        }
        return true;
    }
    
    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Keys.LEFT) {
            GameKeys.setKey(GameKeys.LEFT, false);
        }
        if (keycode == Keys.RIGHT) {
            GameKeys.setKey(GameKeys.RIGHT, false);
        }
        if (keycode == Keys.SPACE) {
            GameKeys.setKey(GameKeys.SPACE, false);
        }
        if (keycode == Keys.UP) {
            GameKeys.setKey(GameKeys.UP, false);
        }
        if (keycode == Keys.DOWN) {
            GameKeys.setKey(GameKeys.DOWN, false);
        }
        return true;
    }
    
    //----------------- Unneeded input below

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		System.out.println("(" + screenX + ", " + screenY + ")");
		return false;
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
	public boolean scrolled(int amount) {
		return false;
	}

}
