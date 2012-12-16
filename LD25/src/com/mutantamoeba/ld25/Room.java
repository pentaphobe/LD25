package com.mutantamoeba.ld25;

public class Room {
	int mapX, mapY;
	Room up, down, left, right;
	RoomConfig config;
	public Room(RoomConfig config, int mapX, int mapY) {
		this.mapX = mapX;
		this.mapY = mapY;	
		this.config = config;
	}
	public Room(int mapX, int mapY) {
		this(new RoomConfig(), mapX, mapY);
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
							 "level : %d\n", config.type, config.health, config.level);
	}
}
