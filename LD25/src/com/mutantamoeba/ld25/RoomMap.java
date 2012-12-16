package com.mutantamoeba.ld25;

import com.mutantamoeba.ld25.utils.ParameterMap;


public class RoomMap extends ParameterMap<Room> {
	private GameWorld world;

	public RoomMap(GameWorld world, int w, int h) {
		super("rooms", w, h, null);
		this.world = world;
	}

	public void makeBlankRoom(int x, int y) {
		if (get(x, y) != null) {
			return;
		}
		Room r = new Room();
		set(x, y, r);
		int floorId = this.world.gameScreen.gameTiles.getId("floor");
		int wallId = this.world.gameScreen.gameTiles.getId("wall");
		
		int tileX = x * GameWorld.ROOM_SIZE;
		int tileY = y * GameWorld.ROOM_SIZE;
		for (int yy = 0; yy < GameWorld.ROOM_SIZE; yy++) {
			for (int xx = 0; xx < GameWorld.ROOM_SIZE; xx++) {
				Tile tile = new Tile();
				tile.layers[0] = floorId;
				if (xx == 0 || xx == GameWorld.ROOM_SIZE-1 || yy == 0 || yy == GameWorld.ROOM_SIZE-1) {
					tile.layers[1] = wallId;
				}
				world.tileMap.set(tileX + xx, tileY + yy, tile);
			}
		}
	}
}
