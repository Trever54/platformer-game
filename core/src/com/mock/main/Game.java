package com.mock.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mock.input.GameInputProcessor;
import com.mock.managers.GameStateManager;

public class Game extends ApplicationAdapter {
	
	public final static String TITLE = "Platformer Drafting";
	public final static int BIT_SIZE = 32;
	public final static int GRAVITY = -20;
	public final static boolean DEBUG = false;
	
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
