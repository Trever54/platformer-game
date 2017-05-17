package com.mock.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class VelocityComponent implements Component {

	public Vector2 velocity;
	
	
	public VelocityComponent() {
		this.velocity = new Vector2(0, 0);
	}
	
	public VelocityComponent(Vector2 velocity) {
		this.velocity = velocity;
	}
	
	public VelocityComponent(float velX, float velY) {
		this.velocity = new Vector2(velX, velY);
	}
	
}
