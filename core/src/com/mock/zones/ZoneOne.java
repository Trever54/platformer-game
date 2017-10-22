package com.mock.zones;

import com.mock.managers.GameStateManager;
import com.mock.states.PlatformerState;
import com.mock.utility.ZoneBounds;

/**
 * Currently a test zone such that to test
 * the PlatformerState class and prototype
 * zone classes.
 * @author Trever Mock
 */
public class ZoneOne extends PlatformerState {
	
	public ZoneOne(GameStateManager gsm) {
		super(gsm, "testTileMap.tmx", new ZoneBounds(0, 4800, 0, 800));
		
	}

}
