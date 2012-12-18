package com.mutantamoeba.ld25;

import com.mutantamoeba.ld25.screens.GameScreen;

public class RoomCreationTool extends GameTool {
	public RoomCreationTool(String name, GameScreen gameScreen) {
		super(name, gameScreen, 0);
	}
	@Override
	public void apply(int mx, int my) {
		GameWorld world = gameScreen.getWorld();
		
		RoomTemplate tpl = world.getRoomTemplate(name);
		cost = gameScreen.getWorld().getRoomTemplate(name).getCost();
		Room existingRoom = world.roomMap.get((int)mx, (int)my);
		if (canApply() && (existingRoom == null || existingRoom.config().type.equals("basic")) ) {
			world.roomMap.makeTemplatedRoom((int)mx, (int)my, tpl);
			gameScreen.getTileRenderer().updateFromMap();
			applyCost();
		}
		gameScreen.selectRoom((int)mx, (int)my);		
	}

}
