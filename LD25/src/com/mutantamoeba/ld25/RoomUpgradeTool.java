package com.mutantamoeba.ld25;

import com.mutantamoeba.ld25.engine.Console;
import com.mutantamoeba.ld25.screens.GameScreen;

public class RoomUpgradeTool extends GameTool {
	public RoomUpgradeTool(String name, GameScreen gameScreen) {
		super(name, gameScreen, 0);
	}
	@Override
	public boolean apply(int mx, int my) {
		GameWorld world = gameScreen.getWorld();
		
		RoomTemplate tpl = world.getRoomTemplate(name);
		Room room = world.roomMap.get((int)mx, (int)my);
		if (room == null) {
//			Console.debug("no room to upgrade at %d, %d", (int)mx, (int)my);			
			return false;
		} else {
			float theCost = room.config().getUpgradeCost();
//			Console.debug("upgrade cost: %f", getCost());
			if (canApply(theCost) && room.config().canUpgrade()) {						
//				Console.debug("level before upgrade:%d", room.config().level());
				room.upgrade();
//				Console.debug("  after upgrade:%d", room.config().level());
				world.roomMap.makeTemplatedRoom((int)mx, (int)my, room.config());
				gameScreen.getTileRenderer().updateFromMap();
				applyCost(theCost);
			} else {
				gameScreen.selectRoom((int)mx, (int)my);
				return false;
			}
			gameScreen.selectRoom((int)mx, (int)my);
		}
		return true;
	}
}
