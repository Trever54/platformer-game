package com.mock.ecs.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mock.ecs.components.PositionComponent;
import com.mock.ecs.components.RenderableComponent;
import com.mock.ecs.components.SpriteComponent;

/**
 * Part of the Ashley ECS. This system renders Entity
 * sprites at their corresponding position component.
 * @author Trever Mock
 */
public class RenderSystem extends EntitySystem {

	private ImmutableArray<Entity> entities;
	
	public RenderSystem(int priority) {
		super(priority);
	}
	
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(RenderableComponent.class, SpriteComponent.class, PositionComponent.class).get());
	}
	
	public void update(float deltaTime) {}
	
	public void render(SpriteBatch sb) {
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			
			SpriteComponent sc = entity.getComponent(SpriteComponent.class);
			PositionComponent pc = entity.getComponent(PositionComponent.class);
			sb.begin();
			sb.draw(sc.sprite.getTexture(), pc.x, pc.y);
			sb.end();
		}
	}
	
}
