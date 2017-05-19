package com.mock.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * The Sprite, or Image, component for an Entity.
 * @author Trever Mock
 */
public class SpriteComponent implements Component {

	public Sprite sprite;
	
	public SpriteComponent(Texture image) {
		this.sprite = new Sprite(image);
	}
	
}
