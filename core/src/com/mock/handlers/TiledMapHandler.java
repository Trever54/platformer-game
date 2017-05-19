package com.mock.handlers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mock.main.Game;
import com.mock.utility.CollisionAlgorithm;

/**
 * Takes in a path to a Tiled Map and creates a map by layers
 * which is stored in the world for some PlatformerState.
 * Also handles rendering of each layer in separate methods.
 * @author Trever Mock
 */
public class TiledMapHandler {

    private TiledMap tileMap;
    private OrthogonalTiledMapRenderer tmr;
    private TiledMapTileLayer nonCollisionTerrainLayer;
    private TiledMapTileLayer parallaxTerrainLayer;
    private TiledMapTileLayer collisionTerrainLayer;
    private World world;
    
    public TiledMapHandler(String path) {
        tileMap = new TmxMapLoader().load(path);
        tmr = new OrthogonalTiledMapRenderer(tileMap);
        nonCollisionTerrainLayer = (TiledMapTileLayer) tileMap.getLayers().get("Non-Collision Terrain");
        parallaxTerrainLayer = (TiledMapTileLayer) tileMap.getLayers().get("Parallax Terrain");
        collisionTerrainLayer = (TiledMapTileLayer) tileMap.getLayers().get("Collision Terrain");
        world = new World(new Vector2(0, Game.GRAVITY), true);
        // Box2D Collision Algorithm
        CollisionAlgorithm ca = new CollisionAlgorithm(collisionTerrainLayer);
        ArrayList<Vector2[]> vList = ca.optimizeBodies();
        for (Vector2[] v : vList) {
            createBody(v);
        }
    }
    
    public void renderCollisionTerrainLayer(SpriteBatch sb, OrthographicCamera cam) {
        tmr.setView(cam);
        tmr.getBatch().begin();
        tmr.renderTileLayer(collisionTerrainLayer);
        tmr.getBatch().end();
    }
    
    public void renderParallaxTerrainLayer(SpriteBatch sb, OrthographicCamera cam) {
        tmr.setView(cam);
        tmr.getBatch().begin();
        tmr.renderTileLayer(parallaxTerrainLayer);
        tmr.getBatch().end();
    }
    
    public void renderNonCollisionTerrainLayer(SpriteBatch sb, OrthographicCamera cam) {
        tmr.setView(cam);
        tmr.getBatch().begin();
        tmr.renderTileLayer(nonCollisionTerrainLayer);
        tmr.getBatch().end();
    }
    
    public void dispose() {
        tmr.dispose();
        tileMap.dispose();
    }
    
    private void createBody(Vector2[] vertices) {
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape(); 
        bdef.type = BodyType.StaticBody;
        Body body = world.createBody(bdef);
        shape.set(vertices);
        fdef.shape = shape;
        body.createFixture(fdef);
        body.setUserData("COLLISION_TILE");
    }
    
    public World getWorld() {
        return this.world;
    }
}