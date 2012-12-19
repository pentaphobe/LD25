package com.mutantamoeba.ld25;

import com.badlogic.gdx.utils.Array;
import com.mutantamoeba.ld25.screens.GameScreen;

public class GameBondSpawner {

	/** For testing.  if TRUE then bonds are spawned in EVERY entry room per spawn tick
	 *  otherwise they are spawned in ONE of the entry rooms per spawn tick
	 */
	private static final boolean SPAWN_SCALES_TO_ENTRY_ROOMS = true;
	
	GameWorld world;
	private float spawnFrequency;
	float spawnCounter;
	
	GameBondSpawner(GameWorld world, float spawnFreq) {
		this.world = world;
		this.setSpawnFrequency(spawnFreq);
		this.spawnCounter = 0;
	}
	public void tick(float delta) {
		this.spawnCounter += delta;
		while (spawnCounter > getSpawnFrequency()) {
			spawnCounter -= getSpawnFrequency();
			spawn();
		}
	}
	public void spawn() {
//		Console.debug("Spawning");
		if (world.getSecretLairHealth() <= 0) return;
		
		if (SPAWN_SCALES_TO_ENTRY_ROOMS) {
			Array<Room> entries = world.getEdgeRooms();
			for (Room room:entries) {
				spawnActorInRoom(room);
			}
		} else {
			Room edgeRoom = world.getRandomEdgeRoom();
			if (edgeRoom == null) {
				return;
			}
			spawnActorInRoom(edgeRoom);
		}
	}
	
	public void spawnActorInRoom(Room edgeRoom) {
		world.gameScreen().spawnActor(edgeRoom.getWorldX() + (0.5f * (GameWorld.ROOM_SIZE * GameScreen.TILE_SIZE)), 
				edgeRoom.getWorldY()  + (0.5f * (GameWorld.ROOM_SIZE * GameScreen.TILE_SIZE)));		
	}
	/**
	 * @param spawnFrequency the spawnFrequency to set
	 */
	public void setSpawnFrequency(float spawnFrequency) {
		this.spawnFrequency = spawnFrequency;
	}
	/**
	 * @return the spawnFrequency
	 */
	public float getSpawnFrequency() {
		return spawnFrequency;
	}
}
