package com.mock.states;

import static com.mock.main.Game.PPM;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mock.handlers.TiledMapHandler;
import com.mock.input.GameKeys;
import com.mock.main.Game;
import com.mock.managers.EntityManager;
import com.mock.managers.GameStateManager;
import com.mock.managers.WorldManager;
import com.mock.utility.ZoneBounds;

/**
 * Represents the typical platformer game state.
 * This class should be inherited by any zone in
 * the game.
 * @author Trever Mock
 */
public class PlatformerState extends GameState {
	
	private TiledMapHandler tmh;
	
	private Box2DDebugRenderer b2dr;
	private OrthographicCamera b2dCam;
	
	private ZoneBounds zoneBounds;
	
	private EntityManager entityManager;

	public PlatformerState(GameStateManager gsm, String tiledPath, ZoneBounds zoneBounds) {
		super(gsm);
		this.zoneBounds = zoneBounds;
		b2dr = new Box2DDebugRenderer();
		b2dCam = new OrthographicCamera();
		b2dCam.setToOrtho(false, (Game.V_WIDTH / PPM) / Game.ZOOM, (Game.V_HEIGHT / PPM) / Game.ZOOM);
        WorldManager.registerWorld(new World(new Vector2(0, Game.GRAVITY), true));
		tmh = new TiledMapHandler(tiledPath);
		entityManager = new EntityManager(new Engine());
	}
	
	public void update() {
		
		WorldManager.step(Gdx.graphics.getDeltaTime(), 4, 4);
        
        entityManager.update();
        Vector2 playerPos = entityManager.getPlayerPosition();
		
		cam.zoom = Game.ZOOM;
		
		// Default cam position
		cam.position.set(
				playerPos.x, 
				playerPos.y, 
				0);
		// adjust for min x bounds
		if (zoneBounds.getMinX() != null && (playerPos.x - Game.V_WIDTH / 2) < zoneBounds.getMinX()) {
			cam.position.x = zoneBounds.getMinX() + Game.V_WIDTH / 2;
		}
		// adjust for max x bounds
		if (zoneBounds.getMaxX() != null && (playerPos.x + Game.V_WIDTH / 2) > zoneBounds.getMaxX()) {
			cam.position.x = zoneBounds.getMaxX() - (Game.V_WIDTH / 2);
		}
		// adjust for min y bounds
		if (zoneBounds.getMinY() != null && (playerPos.y - Game.V_HEIGHT / 2) < zoneBounds.getMinY()) {
			cam.position.y = zoneBounds.getMinY() + Game.V_HEIGHT / 2;
		}
		// adjust for max y bounds
		if (zoneBounds.getMaxY() != null && (playerPos.y + Game.V_HEIGHT / 2) > zoneBounds.getMaxY()) {
			cam.position.y = zoneBounds.getMaxY() - (Game.V_HEIGHT / 2);
		}
		// update camera
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
		
		tmh.renderBackgroundTerrainLayer(sb, game.getCamera());
		tmh.renderParallaxTerrainLayer(sb, game.getCamera(), entityManager.getPlayerPosition().x);
		tmh.renderCollisionTerrainLayer(sb, game.getCamera());
		tmh.renderNonCollisionTerrainLayer(sb, game.getCamera());
		entityManager.render(sb);

		// Box2D Debugging stuff
        if (Game.DEBUG) {
            b2dr.render(WorldManager.getWorldInstance(), b2dCam.combined);
        }
	}
	
	public void dispose() {
		WorldManager.dispose();
		tmh.dispose();
		b2dr.dispose();
	}
	
}
