package com.mock.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Manages the world for some PlatformerState such that
 * other classes outside of the PlatformerState can
 * edit the World.
 * @author Trever Mock
 */
public class WorldManager {

	private static World world;
	
	public static void registerWorld(World world) {
		WorldManager.world = world;
		Gdx.app.debug("WorldManager", "new world registered");
	}
	
	public static World getWorldInstance() {
		return world;
	}

	public static void step(float deltaTime, int velocityIterations, int positionIterations) {
		WorldManager.world.step(deltaTime, velocityIterations, positionIterations);
	}
	
	public static void dispose() {
		world.dispose();
	}

	
}
