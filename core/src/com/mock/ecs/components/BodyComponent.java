package com.mock.ecs.components;

import static com.mock.utility.B2DVars.PPM;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;

/**
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
