package com.mock.managers;

import static com.mock.utility.B2DVars.PPM;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mock.ecs.components.BodyComponent;
import com.mock.ecs.components.MovingPlatformComponent;
import com.mock.ecs.components.PlayerPhysicsComponent;
import com.mock.ecs.components.PositionComponent;
import com.mock.ecs.components.RenderableComponent;
import com.mock.ecs.components.SpriteComponent;
import com.mock.ecs.components.VelocityComponent;
import com.mock.ecs.systems.MovingPlatformSystem;
import com.mock.ecs.systems.PlayerMovementSystem;
import com.mock.ecs.systems.PositionSystem;
import com.mock.ecs.systems.RenderSystem;
import com.mock.main.Game;

public class EntityManager {
	
	private Engine engine;
	private PlayerMovementSystem pms;
	private PositionSystem ps;
	private RenderSystem rs;
	private MovingPlatformSystem mps;
	
	private Entity player;
	
	public EntityManager(Engine engine) {
		this.engine = engine;
		
		mps = new MovingPlatformSystem(0);
		engine.addSystem(mps);
		pms = new PlayerMovementSystem(1);
		engine.addSystem(pms);	
		ps = new PositionSystem(2);
		engine.addSystem(ps);
		rs = new RenderSystem(3);
		engine.addSystem(rs);
		
		player = new Entity();
		
		// Create Player Body
		BodyDef bdef = new BodyDef();
		bdef.type = BodyType.DynamicBody;
		Body body = WorldManager.world.createBody(bdef);
		// Create Player Box Fixture
		PolygonShape poly = new PolygonShape();
		poly.setAsBox(16 / PPM, 24 / PPM, new Vector2(0, 8 / PPM), 0);
		Fixture playerPhysicsFixture = body.createFixture(poly, 1);
		poly.dispose();	
		// Create Player circle sensor
		CircleShape circle = new CircleShape();
		circle.setRadius(16 / PPM);
		circle.setPosition(new Vector2(0, (-(Game.BIT_SIZE / 1.5f) + 6) / PPM));
		Fixture playerSensorFixture = body.createFixture(circle, 0);
		circle.dispose();
		body.setBullet(true);
		body.setFixedRotation(true);
		// create and add to engine
		PositionComponent pcom = new PositionComponent(150, 150);	
		player.add(pcom)
			.add(new BodyComponent(pcom, body))
			.add(new VelocityComponent())
			.add(new SpriteComponent(new Texture("player.png")))
			.add(new RenderableComponent())
			.add(new PlayerPhysicsComponent(playerPhysicsFixture, playerSensorFixture));
		engine.addEntity(player);
		
		
		// create and add box to engine
		Entity box = new Entity();
		bdef = new BodyDef();
		bdef.type = BodyType.DynamicBody;
		body = WorldManager.world.createBody(bdef);
		poly = new PolygonShape();
		poly.setAsBox(16 / PPM, 16 /PPM);
		Fixture boxFixture = body.createFixture(poly, 1);
		poly.dispose();
		pcom = new PositionComponent(200, 150);
		box.add(pcom)
			.add(new BodyComponent(pcom, body))
			.add(new SpriteComponent(new Texture("box.png")))
			.add(new RenderableComponent());
		engine.addEntity(box);
		
		// create and add moving platform to engine (Horizontal)
		Entity movingPlatform = new Entity();
		bdef = new BodyDef();
		bdef.type = BodyType.KinematicBody;
		body = WorldManager.world.createBody(bdef);
		poly = new PolygonShape();
		poly.setAsBox(32 / PPM, 16 /PPM);
		Fixture platformFixture = body.createFixture(poly, 1);
		platformFixture.setUserData("MOVING_PLATFORM");
		poly.dispose();
		pcom = new PositionComponent(400, 200);
		movingPlatform.add(pcom)
			.add(new BodyComponent(pcom, body))
			.add(new VelocityComponent(new Vector2(1, 0)))
			.add(new SpriteComponent(new Texture("platform.png")))
			.add(new RenderableComponent())
			.add(new MovingPlatformComponent(0, 3f));
		engine.addEntity(movingPlatform);
		
		// create and add moving platform to engine (Vertical)
		Entity movingPlatformV = new Entity();
		bdef = new BodyDef();
		bdef.type = BodyType.KinematicBody;
		body = WorldManager.world.createBody(bdef);
		poly = new PolygonShape();
		poly.setAsBox(32 / PPM, 16 /PPM);
		Fixture platformFixtureV = body.createFixture(poly, 1);
		platformFixtureV.setUserData("MOVING_PLATFORM");
		poly.dispose();
		pcom = new PositionComponent(300, 150);
		movingPlatformV.add(pcom)
			.add(new BodyComponent(pcom, body))
			.add(new VelocityComponent(new Vector2(0, 1)))
			.add(new SpriteComponent(new Texture("platform.png")))
			.add(new RenderableComponent())
			.add(new MovingPlatformComponent(0f, 3f));
		engine.addEntity(movingPlatformV);
		
	}
	
	public void update() {
		engine.update(Gdx.graphics.getDeltaTime());
	}
	
	public void render(SpriteBatch sb) {
		rs.render(sb);
	}
	
	public Vector2 getPlayerPosition() {
		float x = player.getComponent(PositionComponent.class).x;
		float y = player.getComponent(PositionComponent.class).y;
		return new Vector2(x, y);
	}
	
}
