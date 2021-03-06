package com.mock.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mock.main.Game;
import com.mock.managers.GameStateManager;

/**
 * Abstract class of a game state that should be
 * inherited for any state of the game. Ensures that
 * game states will have the appropriate methods and
 * objects.
 * @author Trever Mock
 */
public abstract class GameState {
    
    protected GameStateManager gsm;
    protected Game game;
    protected SpriteBatch sb;
    protected OrthographicCamera cam;
    
    protected GameState(GameStateManager gsm) {
        this.gsm = gsm;
        game = gsm.game();
        sb = game.getSpriteBatch();
        cam = game.getCamera();
    }
    
    public abstract void update();
    public abstract void render();
    public abstract void dispose();
}