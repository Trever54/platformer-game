package com.mock.ecs.components;

import com.badlogic.ashley.core.Component;

public class MovingPlatformComponent implements Component {

	public float currentDistance;
	public float maxDistance;
	
	public MovingPlatformComponent(float currentDistance, float maxDistance) {
		this.currentDistance = currentDistance;
		this.maxDistance = maxDistance;
	}
	
}
