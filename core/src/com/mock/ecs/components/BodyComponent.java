package com.mock.ecs.components;

import static com.mock.main.Game.PPM;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * The box2d body component of an Entity
 * 
 * Taken from:
 * https://github.com/LetsMakeAnIndieGame/PhysicsShmup/blob/master/core/src/com/mygdx/game/components/physics/BodyComponent.java
 */
public class BodyComponent implements Component {

	public Body body;
	
	public BodyComponent() {}
	
	public BodyComponent(PositionComponent positionCom, Body body) {
		setBodyAndPosition(positionCom, body);
	}
	
	public void setBodyAndPosition(PositionComponent positionCom, Body body) {
		this.body = body;
		this.body.setTransform(positionCom.x / PPM, positionCom.y / PPM, 0);
	}
	
}
