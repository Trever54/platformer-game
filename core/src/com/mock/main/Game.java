package com.mock.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mock.input.GameInputProcessor;
import com.mock.managers.GameStateManager;

/**
 * Main class called by the DesktopLauncher to start
 * the program. Also contains final static variables that
 * have a wide scope over the entire game.
 * @author Trever Mock
 */
public class Game extends ApplicationAdapter {
	
	/** Title read on the programs window pane */
	public final static String TITLE = "Platformer Drafting";
	
	/** The standard bit size of the game */
	public final static int BIT_SIZE = 32;
	
	/** Gravity used in the Platformer State */
	public final static int GRAVITY = -20;
	
	/** Turns on box2d debugging such that collision boundaries can be seen */
	public final static boolean DEBUG = true;
	
	/** Pixels Per Meter variable used in box2d conversions */
	public static final float PPM = 100;
	
	public static int V_WIDTH;
	public static int V_HEIGHT;
	public static float ZOOM = 1f;
	
	private SpriteBatch sb;
	private OrthographicCamera cam;
	private GameStateManager gsm;
	
	@Override
	public void create () {
		V_WIDTH = Gdx.graphics.getWidth();
		V_HEIGHT = Gdx.graphics.getHeight();
		Gdx.input.setInputProcessor(new GameInputProcessor());
		sb = new SpriteBatch();
		cam = new OrthographicCamera(V_WIDTH / ZOOM, V_HEIGHT / ZOOM);
		gsm = new GameStateManager(this);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.5f, 0.8f, 0.9f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		gsm.update();
		gsm.render();
	}
	
	@Override
	public void dispose () {
		sb.dispose();
	}
	
	public SpriteBatch getSpriteBatch() { return sb; }
	public OrthographicCamera getCamera() { return cam; }
}
