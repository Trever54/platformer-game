package com.mock.ecs.systems;

import static com.mock.utility.B2DVars.PPM;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mock.ecs.components.BodyComponent;
import com.mock.ecs.components.PositionComponent;
import com.mock.ecs.components.SpriteComponent;

/**
 * Code taken from: 
 * https://github.com/LetsMakeAnIndieGame/PhysicsShmup/blob/master/core/src/com/mygdx/game/systems/PositionSystem.java
 */
public class PositionSystem extends IteratingSystem {

	private ComponentMapper<PositionComponent> positionMap = ComponentMapper.getFor(PositionComponent.class);
	private ComponentMapper<SpriteComponent> spriteMap = ComponentMapper.getFor(SpriteComponent.class);
	private ComponentMapper<BodyComponent> bodyMap = ComponentMapper.getFor(BodyComponent.class);
	
	public PositionSystem(int priority) {
		super(Family.all(PositionComponent.class).get(), priority);
	}
	
	public void processEntity(Entity entity, float deltaTime) {
		PositionComponent positionCom = positionMap.get(entity);
		SpriteComponent spriteCom = spriteMap.get(entity);
		BodyComponent bodyCom = bodyMap.get(entity);
		
		// Position priority: Body => Position => Sprite (highest to lowest)
		if (bodyCom != null) {
			positionCom.x = bodyCom.body.getPosition().x * PPM - spriteCom.sprite.getWidth() / 2;
			positionCom.y = bodyCom.body.getPosition().y * PPM - spriteCom.sprite.getHeight() / 2;
		}
		if (spriteCom != null) {
			spriteCom.sprite.setX(positionCom.x);
			spriteCom.sprite.setY(positionCom.y);
		}
		
	}
	
}
