package com.mutantamoeba.ld25;

import com.mutantamoeba.ld25.screens.GameScreen;

public class RoomCreationTool extends GameTool {
	public RoomCreationTool(String name, GameScreen gameScreen) {
		super(name, gameScreen, gameScreen.getWorld().getRoomTemplate(name).getCost());
	}
	@Override
	public boolean apply(int mx, int my) {
		GameWorld world = gameScreen.getWorld();
		
		RoomTemplate tpl = world.getRoomTemplate(name);
		Room existingRoom = world.roomMap.get((int)mx, (int)my);
		if (canApply() && (existingRoom == null || existingRoom.config().type.equals("basic")) ) {
			world.roomMap.makeTemplatedRoom((int)mx, (int)my, tpl);
			gameScreen.getTileRenderer().updateFromMap();
			applyCost();
		} else {
			gameScreen.selectRoom((int)mx, (int)my);

			return false;
		}
		gameScreen.selectRoom((int)mx, (int)my);
		return true;
	}

}
