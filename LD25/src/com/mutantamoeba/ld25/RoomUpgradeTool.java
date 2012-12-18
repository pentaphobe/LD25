package com.mutantamoeba.ld25;

import com.mutantamoeba.ld25.engine.Console;
import com.mutantamoeba.ld25.screens.GameScreen;

public class RoomUpgradeTool extends GameTool {
	public RoomUpgradeTool(String name, GameScreen gameScreen) {
		super(name, gameScreen, 0);
	}
	@Override
	public void apply(int mx, int my) {
		GameWorld world = gameScreen.getWorld();
		
		RoomTemplate tpl = world.getRoomTemplate(name);
		Room room = world.roomMap.get((int)mx, (int)my);
		if (room == null) {
			Console.debug("no room to upgrade at %d, %d", (int)mx, (int)my);
		} else {
			cost = room.config().getUpgradeCost();
			Console.debug("upgrade cost: %f", cost);
			if (canApply() && room.config().canUpgrade()) {						
				Console.debug("level before upgrade:%d", room.config().level());
				room.upgrade();
				Console.debug("  after upgrade:%d", room.config().level());
				world.roomMap.makeTemplatedRoom((int)mx, (int)my, room.config());
				gameScreen.getTileRenderer().updateFromMap();
				applyCost(cost);
			}
			gameScreen.selectRoom((int)mx, (int)my);
		}
	}
}
