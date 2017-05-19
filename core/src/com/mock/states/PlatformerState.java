package com.mock.states;

import static com.mock.utility.B2DVars.PPM;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.mock.handlers.TiledMapHandler;
import com.mock.input.GameKeys;
import com.mock.main.Game;
import com.mock.managers.EntityManager;
import com.mock.managers.GameStateManager;
import com.mock.managers.WorldManager;

public class PlatformerState extends GameState {
	
	private TiledMapHandler tmh;
	
	private Box2DDebugRenderer b2dr;
	private OrthographicCamera b2dCam;
	
	private EntityManager entityManager;

	public PlatformerState(GameStateManager gsm, String tiledPath) {
		super(gsm);
		b2dr = new Box2DDebugRenderer();
		b2dCam = new OrthographicCamera();
		b2dCam.setToOrtho(false, (Game.V_WIDTH / PPM) / Game.ZOOM, (Game.V_HEIGHT / PPM) / Game.ZOOM);
		tmh = new TiledMapHandler(tiledPath);
		WorldManager.registerWorld(tmh.getWorld());
		entityManager = new EntityManager(new Engine());
	}
	
	public void update() {
		
		WorldManager.step(Gdx.graphics.getDeltaTime(), 4, 4);
        
        entityManager.update();
        Vector2 playerPos = entityManager.getPlayerPosition();
		
		cam.zoom = Game.ZOOM;
		cam.position.set(
				playerPos.x, 
				playerPos.y, 
				0);
		cam.update();
		
		if (Game.DEBUG) { 
            b2dCam.position.set(
            	game.getCamera().position.x / PPM,
            	game.getCamera().position.y / PPM,
                0);
            b2dCam.zoom = cam.zoom;
            b2dCam.update(); 
        }
		
		GameKeys.update();
	}
	
	public void render() {
		sb.setProjectionMatrix(cam.combined);
		
		tmh.renderParallaxTerrainLayer(sb, game.getCamera());
		tmh.renderCollisionTerrainLayer(sb, game.getCamera());
		tmh.renderNonCollisionTerrainLayer(sb, game.getCamera());
		entityManager.render(sb);

		// Box2D Debugging stuff
        if (Game.DEBUG) {
            b2dr.render(WorldManager.world, b2dCam.combined);
        }
	}
	
	public void dispose() {
		WorldManager.dispose();
		tmh.dispose();
		b2dr.dispose();
	}
	
}
