package com.mock.ecs.components;

import com.badlogic.ashley.core.Component;

/**
 * The position component of an Entity.
 * @author Trever Mock
 */
public class PositionComponent implements Component {

	public float x;
	public float y;
	
	public PositionComponent(float x, float y) {
		this.x = x;
		this.y = y;
	}
}
