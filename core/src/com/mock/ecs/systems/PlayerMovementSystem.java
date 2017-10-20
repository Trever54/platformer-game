package com.mock.ecs.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import com.badlogic.gdx.utils.Array;
import com.mock.ecs.components.BodyComponent;
import com.mock.ecs.components.PlayerPhysicsComponent;
import com.mock.ecs.components.PositionComponent;
import com.mock.ecs.components.VelocityComponent;
import com.mock.input.GameKeys;
import com.mock.managers.WorldManager;

/**
 * Part of the Ashley ECS. This system handles player physics and
 * player movement.
 * @author Trever Mock
 */
public class PlayerMovementSystem extends IteratingSystem {
	
	private Body groundedPlatform = null;
	
	private ComponentMapper<PositionComponent> pom = ComponentMapper.getFor(PositionComponent.class);
	private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
	private ComponentMapper<BodyComponent> bm = ComponentMapper.getFor(BodyComponent.class);
	private ComponentMapper<PlayerPhysicsComponent> plm = ComponentMapper.getFor(PlayerPhysicsComponent.class);
	
	public PlayerMovementSystem(int priority) {
		super(Family.all(PlayerPhysicsComponent.class).get(), priority);
	}
	
	@Override
	public void processEntity(Entity entity, float deltaTime) {
		PositionComponent pc = pom.get(entity);
		VelocityComponent vc = vm.get(entity);
		BodyComponent bc = bm.get(entity);
		PlayerPhysicsComponent ppc = plm.get(entity);
		
		// Player Physics
		bc.body.setAwake(true);
		vc.velocity = bc.body.getLinearVelocity();
		pc.x = bc.body.getPosition().x;
		pc.y = bc.body.getPosition().y;
		ppc.grounded = isGrounded(Gdx.graphics.getDeltaTime(), ppc.playerSensorFixture, pc.y);
		if (ppc.grounded) {
			ppc.lastGroundTime = System.nanoTime();
		}
		else {
			if (System.nanoTime() - ppc.lastGroundTime < 100000000) {
				ppc.grounded = true;
			}
		}
		// cap velocity on x movement
		if (Math.abs(vc.velocity.x) > ppc.MAX_VELOCITY) {
			vc.velocity.x = Math.signum(vc.velocity.x) * ppc.MAX_VELOCITY;
			bc.body.setLinearVelocity(vc.velocity.x, vc.velocity.y);
		}
		// calculate stilltime & damp
		if (!Gdx.input.isKeyPressed(Keys.LEFT) && !Gdx.input.isKeyPressed(Keys.RIGHT)) {
			ppc.stillTime += Gdx.graphics.getDeltaTime();
			bc.body.setLinearVelocity(vc.velocity.x * 0.9f, vc.velocity.y);
		}
		else {
			ppc.stillTime = 0;
		}
		// disable friction while jumping
		if (!ppc.grounded) {
			ppc.playerPhysicsFixture.setFriction(0f);
			ppc.playerSensorFixture.setFriction(0f);
		}
		else {
			if (!Gdx.input.isKeyPressed(Keys.LEFT) && !Gdx.input.isKeyPressed(Keys.RIGHT) && ppc.stillTime > 2.0) {
				ppc.playerPhysicsFixture.setFriction(100f);
				ppc.playerSensorFixture.setFriction(100f);
			}
			else {
				ppc.playerPhysicsFixture.setFriction(0.2f);
				ppc.playerSensorFixture.setFriction(0.2f);
			}
			// moving platform stuff
			if (groundedPlatform != null) {
				if (!GameKeys.isDown(GameKeys.LEFT) 
						&& !GameKeys.isDown(GameKeys.RIGHT)) {
					bc.body.setLinearVelocity(groundedPlatform.getLinearVelocity());
				} else {
					bc.body.setLinearVelocity(bc.body.getLinearVelocity().x, groundedPlatform.getLinearVelocity().y);
				}
			}
		}
		// handle key input
		if (GameKeys.isDown(GameKeys.LEFT) && vc.velocity.x > -ppc.MAX_VELOCITY) {
    		bc.body.applyLinearImpulse(-ppc.movementPower, 0, pc.x, pc.y, true);
    	}
    	if (GameKeys.isDown(GameKeys.RIGHT) && vc.velocity.x < ppc.MAX_VELOCITY) {
    		bc.body.applyLinearImpulse(ppc.movementPower, 0, pc.x, pc.y, true);
    	}
    	if (GameKeys.isPressed(GameKeys.SPACE)) {
    		ppc.jump = true;
    	}
    	if (GameKeys.isUp(GameKeys.SPACE)) {
    		ppc.jump = false;
    	}
		// jump, but only when grounded
		if(ppc.jump) {			
			ppc.jump = false;
			if(ppc.grounded) {
				groundedPlatform = null;
				bc.body.setLinearVelocity(vc.velocity.x, 0);			
				bc.body.setTransform(pc.x, pc.y + 0.01f, 0);
				bc.body.applyLinearImpulse(0, ppc.jumpPower, pc.x, pc.y, true);	
			}
		}	
	}
	
	private boolean isGrounded(float deltaTime, Fixture playerSensorFixture, float posY) {
		Array<Contact> contactList = WorldManager.getWorldInstance().getContactList();
    	for (int i = 0; i < contactList.size; i++) {
			Contact contact = contactList.get(i);
			if (contact.isTouching() 
				&& (contact.getFixtureA() == playerSensorFixture
				|| contact.getFixtureB() == playerSensorFixture)) {
				
				WorldManifold manifold = contact.getWorldManifold();
				boolean below = true;
				for (int j = 0; j < manifold.getNumberOfContactPoints(); j++) {
					below &= (manifold.getPoints()[j].y < posY -  0.3f); // 1.5f originally, TODO: Problem here?
				}
				if (below) {
					if (contact.getFixtureA().getUserData() != null && contact.getFixtureA().getUserData().equals("MOVING_PLATFORM")) {
						groundedPlatform = contact.getFixtureA().getBody();
					}
					else if (contact.getFixtureB().getUserData() != null && contact.getFixtureB().getUserData().equals("MOVING_PLATFORM")) {
						groundedPlatform = contact.getFixtureB().getBody();
					}
					else {
						groundedPlatform = null;
					}
					return true;
				}
				return false;
			}
    	}
    	return false;
	}
	
}
