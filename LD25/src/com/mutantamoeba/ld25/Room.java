package com.mutantamoeba.ld25;

import com.mutantamoeba.ld25.screens.GameScreen;

public class Room {
	int mapX, mapY;
	Room up, down, left, right;
	private RoomConfig config;
	public Room(RoomConfig config, int mapX, int mapY) {
		this.mapX = mapX;
		this.mapY = mapY;
		this.config(config);
	}
	public String toString() {
		return String.format("%s {up:%s, down:%s, left:%s, right:%s}", Room.toString(this, false), Room.toString(up, false), Room.toString(down, false),
				Room.toString(left, false), Room.toString(right, false));
	}
	public static String toString(Room room, boolean recurse) {
		if (room == null) return "Room[null]";
		if (recurse) {
			return room.toString();
		} 
		return String.format("Room[%d, %d]", room.mapX, room.mapY);
	}
	public String infoString() {
		return String.format("type  : %s\n" +
							 "health: %.0f\n" +
							 "level : %d\n", config().type, config().health, config().level()+1);
	}
	/**
	 * @param config the config to set
	 */
	public void config(RoomConfig config) {
		this.config = config;
	}
	/**
	 * @return the config
	 */
	public RoomConfig config() {
		return config;
	}
	public void upgrade() {
		config.upgrade();		
	}
	public boolean equals(Room r) {
		return r.mapX == mapX && r.mapY == mapY;
	}
	@Override
	public boolean equals(Object o) {
		if (o instanceof Room) return equals((Room)o);
		return false;
	}
	public float getWorldX() {		
		return mapX * GameWorld.ROOM_SIZE * GameScreen.TILE_SIZE;
	}
	public float getWorldY() {
		return mapY * GameWorld.ROOM_SIZE * GameScreen.TILE_SIZE;
	}
}
