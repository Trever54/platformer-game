package com.mock.handlers;

import static com.mock.main.Game.PPM;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mock.ecs.components.BodyComponent;
import com.mock.ecs.components.MovingPlatformComponent;
import com.mock.ecs.components.PositionComponent;
import com.mock.ecs.components.RenderableComponent;
import com.mock.ecs.components.SpriteComponent;
import com.mock.ecs.components.VelocityComponent;
import com.mock.managers.EntityManager;
import com.mock.managers.WorldManager;
import com.mock.types.EntityType;
import com.mock.types.MovingPlatformType;
import com.mock.types.TextActionType;
import com.mock.types.ZoneChangeActionType;

public class EntityHandler {
	
	// lists based on each EntityType
	private ArrayList<MapObject> movingPlatforms;
	private ArrayList<MapObject> textActions;
	private ArrayList<MapObject> zoneChangeActions;

	public EntityHandler(MapLayer entityLayer) {
		movingPlatforms = new ArrayList<MapObject>();
		textActions = new ArrayList<MapObject>();
		zoneChangeActions = new ArrayList<MapObject>();
		
		Iterator<MapObject> iterator = entityLayer.getObjects().iterator();
		
		while (iterator.hasNext()) {
			MapObject currentObject = iterator.next();
			String entityType = (String) currentObject.getProperties().get("EntityType");
			// Sort into the appropriate list
			switch (entityType) {
				case EntityType.MOVING_PLATFORM:
					movingPlatforms.add(currentObject);
					break;
				case EntityType.TEXT_ACTION:
					textActions.add(currentObject);
					break;
				case EntityType.ZONE_CHANGE_ACTION:
					zoneChangeActions.add(currentObject);
					break;
				default:
					Gdx.app.error("EntityHandler", "Entity Type defined in Tiled not handled: " + entityType);
			}
		}
	}
	
	public void createEntities() {
		createMovingPlatforms();
		createTextActions();
		createZoneChangeActions();
	}
	
	public void createMovingPlatforms() {	
		for (MapObject movingPlatformMapObject : movingPlatforms) {
			Gdx.app.debug("EntityHandler", "Parsing Moving Platform...");
			MapProperties properties = movingPlatformMapObject.getProperties();
			int collision = (int) properties.get(MovingPlatformType.COLLISION);
			Gdx.app.debug("EntityHandler", "Parsed collision as " + collision);
			float width = (float) properties.get(MovingPlatformType.WIDTH);
			Gdx.app.debug("EntityHandler", "Parsed width as " + width);
			float height = (float) properties.get(MovingPlatformType.HEIGHT);
			Gdx.app.debug("EntityHandler", "Parsed height as " + height);
			float velX = (float) properties.get(MovingPlatformType.VEL_X);
			Gdx.app.debug("EntityHandler", "Parsed velX as " + velX);
			float velY = (float) properties.get(MovingPlatformType.VEL_Y);
			Gdx.app.debug("EntityHandler", "Parsed velY as " + velY);
			float density = (float) properties.get(MovingPlatformType.DENSITY);
			Gdx.app.debug("EntityHandler", "Parsed density as " + density);
			float currentDistance = (float) properties.get(MovingPlatformType.CURRENT_DISTANCE);
			Gdx.app.debug("EntityHandler", "Parsed currentDistance as " + currentDistance);
			float maxDistance = (float) properties.get(MovingPlatformType.MAX_DISTANCE);
			Gdx.app.debug("EntityHandler", "Parsed maxDistance as " + maxDistance);
			String imagePath = (String) properties.get(MovingPlatformType.IMAGE_PATH);
			Gdx.app.debug("EntityHandler", "Parsed imagePath as " + imagePath);
			float posX = (float) properties.get(MovingPlatformType.POS_X);
			Gdx.app.debug("EntityHandler", "Parsed posX as " + posX);
			posX = posX + (width / 2);
			Gdx.app.debug("EntityHandler", "Adjusted posX to " + posX);
			// TODO: Change this to be the TILED Y Height rather than subtract 3200 for the TILED input
			float posY = (float) properties.get(MovingPlatformType.POS_Y);
			Gdx.app.debug("EntityHandler", "Parsed posY as " + posY);
			posY = posY - (height / 2);
			Gdx.app.debug("EntityHandler", "Adjusted posY to " + posY);

			Entity movingPlatform = new Entity();
			BodyDef bdef = new BodyDef();
			bdef.type = BodyType.KinematicBody;
			Body body = WorldManager.getWorldInstance().createBody(bdef);
			PolygonShape poly = new PolygonShape();
			poly.setAsBox((width / 2) / PPM, (height / 2) / PPM);
			Fixture platformFixture = body.createFixture(poly, density);
			platformFixture.setUserData("MOVING_PLATFORM");
			poly.dispose();
			
			PositionComponent pcom = new PositionComponent(posX, posY);
			movingPlatform.add(pcom)
				.add(new BodyComponent(pcom, body))
				.add(new VelocityComponent(new Vector2(velX, velY)))
				.add(new SpriteComponent(new Texture(imagePath)))
				.add(new RenderableComponent())
				.add(new MovingPlatformComponent(currentDistance, maxDistance));
			EntityManager.getEntityEngineInstance().addEntity(movingPlatform);	
			Gdx.app.debug("EntityHandler", "Successfully created moving platform and added it to the EntityManager Engine");
		}
	}
	
	public void createTextActions() {
		for (MapObject textActionMapObject : textActions) {
			Gdx.app.debug("EntityHandler", "Parsing Text Action...");
			MapProperties properties = textActionMapObject.getProperties();
			int collision = (int) properties.get(TextActionType.COLLISION);
			Gdx.app.debug("EntityHandler", "Parsed collision as " + collision);
			float width = (float) properties.get(TextActionType.WIDTH);
			Gdx.app.debug("EntityHandler", "Parsed width as " + width);
			float height = (float) properties.get(TextActionType.HEIGHT);
			Gdx.app.debug("EntityHandler", "Parsed height as " + height);
			String text = (String) properties.get(TextActionType.TEXT);
			Gdx.app.debug("EntityHandler", "Parsed Text as - " + text);
			String imagePath = (String) properties.get(TextActionType.IMAGE_PATH);
			Gdx.app.debug("EntityHandler", "Parsed imagePath as " + imagePath);
			float posX = (float) properties.get(TextActionType.POS_X);
			Gdx.app.debug("EntityHandler", "Parsed posX as " + posX);
			posX = posX + (width / 2);
			Gdx.app.debug("EntityHandler", "Adjusted posX to " + posX);
			// TODO: Change this to be the TILED Y Height rather than subtract 3200 for the TILED input
			float posY = (float) properties.get(TextActionType.POS_Y);
			Gdx.app.debug("EntityHandler", "Parsed posY as " + posY);
			posY = posY - (height / 2);
			Gdx.app.debug("EntityHandler", "Adjusted posY to " + posY);

			Entity textAction = new Entity();
			BodyDef bdef = new BodyDef();
			bdef.type = BodyType.StaticBody;
			Body body = WorldManager.getWorldInstance().createBody(bdef);
			PolygonShape poly = new PolygonShape();
			poly.setAsBox((width / 2) / PPM, (height / 2) / PPM);
			Fixture platformFixture = body.createFixture(poly, 1);
			platformFixture.setUserData("TEXT_ACTION");
			Filter filter = new Filter();
			// TODO: Should I be using a filter here?
			filter.maskBits = 0x0000;
			platformFixture.setFilterData(filter);
			poly.dispose();
			
			PositionComponent pcom = new PositionComponent(posX, posY);
			textAction.add(pcom)
				.add(new BodyComponent(pcom, body))
				.add(new SpriteComponent(new Texture(imagePath)))
				.add(new RenderableComponent());
			EntityManager.getEntityEngineInstance().addEntity(textAction);	
			Gdx.app.debug("EntityHandler", "Successfully created Text Action and added it to the EntityManager Engine");
		}
	}
	
	public void createZoneChangeActions() {
		for (MapObject zoneChangeActionMapObject : zoneChangeActions) {
			Gdx.app.debug("EntityHandler", "Parsing Zone Change Action...");
			MapProperties properties = zoneChangeActionMapObject.getProperties();
			int collision = (int) properties.get(ZoneChangeActionType.COLLISION);
			Gdx.app.debug("EntityHandler", "Parsed collision as " + collision);
			float width = (float) properties.get(ZoneChangeActionType.WIDTH);
			Gdx.app.debug("EntityHandler", "Parsed width as " + width);
			float height = (float) properties.get(ZoneChangeActionType.HEIGHT);
			Gdx.app.debug("EntityHandler", "Parsed height as " + height);
			String newZone = (String) properties.get(ZoneChangeActionType.NEW_ZONE);
			Gdx.app.debug("EntityHandler", "Parsed New Zone as - " + newZone);
			float posX = (float) properties.get(ZoneChangeActionType.POS_X);
			Gdx.app.debug("EntityHandler", "Parsed posX as " + posX);
			posX = posX + (width / 2);
			Gdx.app.debug("EntityHandler", "Adjusted posX to " + posX);
			// TODO: Change this to be the TILED Y Height rather than subtract 3200 for the TILED input
			float posY = (float) properties.get(ZoneChangeActionType.POS_Y);
			Gdx.app.debug("EntityHandler", "Parsed posY as " + posY);
			posY = posY - (height / 2);
			Gdx.app.debug("EntityHandler", "Adjusted posY to " + posY);

			Entity zoneChangeAction = new Entity();
			BodyDef bdef = new BodyDef();
			bdef.type = BodyType.StaticBody;
			Body body = WorldManager.getWorldInstance().createBody(bdef);
			PolygonShape poly = new PolygonShape();
			poly.setAsBox((width / 2) / PPM, (height / 2) / PPM);
			Fixture platformFixture = body.createFixture(poly, 1);
			platformFixture.setUserData("ZONE_CHANGE_ACTION");
			poly.dispose();
			
			PositionComponent pcom = new PositionComponent(posX, posY);
			zoneChangeAction.add(pcom)
				.add(new BodyComponent(pcom, body))
				.add(new SpriteComponent(new Texture("heart.png")))
				.add(new RenderableComponent());
			EntityManager.getEntityEngineInstance().addEntity(zoneChangeAction);	
			Gdx.app.debug("EntityHandler", "Successfully created Zone Change Action and added it to the EntityManager Engine");
		}
	}
	
	
}
