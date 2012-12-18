package com.mutantamoeba.ld25;

import com.badlogic.gdx.utils.Array;
import com.mutantamoeba.ld25.actors.GameEntity;
import com.mutantamoeba.ld25.actors.TrapEntity;
import com.mutantamoeba.ld25.engine.Console;
import com.mutantamoeba.ld25.screens.GameScreen;

public class Room {
	int mapX, mapY;
	Room up, down, left, right;
	private RoomConfig config;
	private Array<GameEntity> entities = new Array<GameEntity>();
	Array<TrapEntity> trapEntities = new Array<TrapEntity>();
	public Room(RoomConfig config, int mapX, int mapY) {
		this.mapX = mapX;
		this.mapY = mapY;
		this.config(config);
	}
	public void destroy() {
		for (TrapEntity trap:trapEntities) {
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
							 " %s\n", config().type, config().health, config().level()+1, getEntities().size, extra);
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
		if (getEntities().contains(e, true)) {
			return;
		}
		getEntities().add(e);
	}
	public void removeEntity(GameEntity e) {
		if (e.getRoom() == this) {
			e.setRoom(null);
		}
		getEntities().removeValue(e, true);
	}
	public void addTrapEntity(TrapEntity e) {
		trapEntities.add(e);
		GameWorld.instance().gameScreen().addEntity(e);
	}
	public void removeTrapEntity(TrapEntity e) {
		trapEntities.removeValue(e, true);
		GameWorld.instance().gameScreen().removeEntity(e);
	}	
	public void activateTraps() {
		config.activateTraps(this);
	}
	public void createTrapEntity(int xx, int yy) {
		TrapEntity trap = config.createTrapEntity(xx, yy);
		if (trap == null) return;
		trap.setPosition(getWorldX() + xx * GameScreen.TILE_SIZE, getWorldY() + yy * GameScreen.TILE_SIZE);
		
		addTrapEntity(trap);
	}
	public void mouseMoved(float x, float y) {
		for (TrapEntity trap:trapEntities) {
			trap.mouseMoved(x, y);
		}
	}
	public void inheritEntities(Room oldRoom) {
		setEntities(oldRoom.getEntities());
		for (GameEntity ent:getEntities()) {
			ent.setRoom(this);
		}
	}
	public float getRelativeX(GameEntity e) {
		float x = e.getX() - (mapX * (GameScreen.TILE_SIZE*GameWorld.ROOM_SIZE));
		return x;
	}
	public float getRelativeY(GameEntity e) {
		float y = e.getY() - (mapY * (GameScreen.TILE_SIZE*GameWorld.ROOM_SIZE));
		return y;
	}
	
	/**
	 * @param entities the entities to set
	 */
	public void setEntities(Array<GameEntity> entities) {
		this.entities = entities;
	}
	/**
	 * @return the entities
	 */
	public Array<GameEntity> getEntities() {
		return entities;
	}
	public boolean isInside(GameEntity gameEntity) {
		// ### Approach the first (relies on ParameterMap returning edge tiles instead of null for out of bounds areas)
//		float rx = getRelativeX(gameEntity);
//		float ry = getRelativeY(gameEntity);
//		int roomSize = (GameScreen.TILE_SIZE*GameWorld.ROOM_SIZE);
////		Console.debug("relative pos:%f, %f", rx, ry);
//		return rx >= 0 && ry >= 0 && rx < roomSize && ry < roomSize;
		
		// ### Second approach
		Room r = GameWorld.instance().roomMap.getRoomAt(gameEntity);
		return r == this;
	}
	public Array<Room> getExits() {
		Array<Room> exits = new Array<Room>(4);
		if (up != null) exits.add(up);
		if (right != null) exits.add(right);
		if (down != null) exits.add(down);
		if (left != null) exits.add(left);
		return exits;
	}
}
