package com.mock.ecs.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mock.ecs.components.BodyComponent;
import com.mock.ecs.components.MovingPlatformComponent;
import com.mock.ecs.components.PositionComponent;
import com.mock.ecs.components.VelocityComponent;

public class MovingPlatformSystem extends IteratingSystem {

	private ComponentMapper<PositionComponent> positionMap = ComponentMapper.getFor(PositionComponent.class);
	private ComponentMapper<BodyComponent> bodyMap = ComponentMapper.getFor(BodyComponent.class);
	private ComponentMapper<VelocityComponent> velocityMap = ComponentMapper.getFor(VelocityComponent.class);
	private ComponentMapper<MovingPlatformComponent> mpMap = ComponentMapper.getFor(MovingPlatformComponent.class);
	
	public MovingPlatformSystem(int priority) {
		super(Family.all(MovingPlatformComponent.class).get(), priority);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		PositionComponent positionCom = positionMap.get(entity);
		BodyComponent bodyCom = bodyMap.get(entity);
		VelocityComponent velCom = velocityMap.get(entity);
		MovingPlatformComponent mpCom = mpMap.get(entity);
		
		mpCom.currentDistance += velCom.velocity.len() * deltaTime;
		if (mpCom.currentDistance > mpCom.maxDistance) {
			velCom.velocity.scl(-1);
			mpCom.currentDistance = 0;
		}
		bodyCom.body.setLinearVelocity(velCom.velocity);
	}
	
}
