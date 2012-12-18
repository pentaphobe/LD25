package com.mutantamoeba.ld25;

import com.badlogic.gdx.utils.Array;
import com.mutantamoeba.ld25.actors.GameEntity;
import com.mutantamoeba.ld25.screens.GameScreen;

public class Room {
	int mapX, mapY;
	Room up, down, left, right;
	private RoomConfig config;
	Array<GameEntity> entities = new Array<GameEntity>();
	Array<GameEntity> trapEntities = new Array<GameEntity>();
	public Room(RoomConfig config, int mapX, int mapY) {
		this.mapX = mapX;
		this.mapY = mapY;
		this.config(config);
	}
	public void destroy() {
		for (GameEntity trap:trapEntities) {
			removeTrapEntity(trap);
		}
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
		String extra = "";
		if (LD25.DEBUG_MODE){
			extra = String.format("trap entities: %d", trapEntities.size);
		}
		return String.format("type    : %s\n" +
							 "health  : %.0f\n" +
							 "level   : %d\n" +
							 "entities: %d\n" +
							 "extra   :\n" +
							 " %s\n", config().type, config().health, config().level()+1, entities.size, extra);
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
	public void addEntity(GameEntity e) {
		entities.add(e);
	}
	public void removeEntity(GameEntity e) {
		entities.removeValue(e, true);
	}
	public void addTrapEntity(GameEntity e) {
		trapEntities.add(e);
		GameWorld.instance().gameScreen().addEntity(e);
	}
	public void removeTrapEntity(GameEntity e) {
		trapEntities.removeValue(e, true);
		GameWorld.instance().gameScreen().removeEntity(e);
	}	
	public void activateTraps() {
		config.activateTraps(this);
	}
	public void createTrapEntity(int xx, int yy) {
		GameEntity trap = config.createTrapEntity(xx, yy);
		if (trap == null) return;
		trap.setPosition(getWorldX() + xx * GameScreen.TILE_SIZE, getWorldY() + yy * GameScreen.TILE_SIZE);
		
		addTrapEntity(trap);
	}
}
