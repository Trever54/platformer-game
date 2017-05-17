package com.mock.managers;

import java.util.Stack;

import com.mock.main.Game;
import com.mock.states.GameState;
import com.mock.zones.ZoneOne;


public class GameStateManager {
    
    public static final int PLATFORMER_STATE = 0;
    public static final int ZONE_ONE = 1;
    
    private Game game;
    private Stack<GameState> gameStates;
    
    public GameStateManager(Game game) {
        this.game = game;
        gameStates = new Stack<GameState>();
        pushState(ZONE_ONE);
    }
    
    public void update() {
        gameStates.peek().update();
    }
    
    public void render() {
        gameStates.peek().render();
    }
    
    private GameState getState(int state) {
        if (state == ZONE_ONE) return new ZoneOne(this);
        return null;
    }
    
    public void setState(int state) {
        popState();
        pushState(state);
    }
    
    public void pushState(int state) {
        gameStates.push(getState(state));
    }
    
    public void popState() {
        GameState g = gameStates.pop();
        g.dispose();
    }
    
    public void dispose() {}
    
    public Game game() {
        return this.game;
    }
}
