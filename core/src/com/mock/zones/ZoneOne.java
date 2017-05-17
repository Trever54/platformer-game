package com.mock.zones;

import com.mock.managers.GameStateManager;
import com.mock.states.PlatformerState;

public class ZoneOne extends PlatformerState {
	
	public ZoneOne(GameStateManager gsm) {
		super(gsm, "testTileMap.tmx");
	}

}
