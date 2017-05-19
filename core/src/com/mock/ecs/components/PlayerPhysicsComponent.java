package com.mock.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Fixture;

/**
 * Information regarding physics relating to the player
 * @author Trever Mock
 */
public class PlayerPhysicsComponent implements Component {
	
	public float MAX_VELOCITY;
	public boolean jump;
	public boolean grounded;
	public float stillTime;
	public float lastGroundTime;
	public float jumpPower;
	public float movementPower;
	
	public Fixture playerPhysicsFixture;
	public Fixture playerSensorFixture;
	
	public PlayerPhysicsComponent(Fixture playerPhysicsFixture, Fixture playerSensorFixture) {
		this.MAX_VELOCITY = 2.5f;	// bigger means higher max speed (2.5)
		this.jumpPower = 1.3f;		// bigger means higher jump (1.3)
		this.movementPower = 0.02f;	// bigger means a faster increase to maximum speed (0.02
		
		this.jump = false;
		this.grounded = true;
		this.stillTime = 0;
		this.lastGroundTime = 0;
		
		this.playerPhysicsFixture = playerPhysicsFixture;
		this.playerSensorFixture = playerSensorFixture;
	}
	
	
	
}
