package com.mutantamoeba.ld25;

import com.mutantamoeba.ld25.engine.Console;
import com.mutantamoeba.ld25.screens.GameScreen;

public class GameBondSpawner {
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
		Room edgeRoom = world.getRandomEdgeRoom();
		if (edgeRoom == null) {
			return;
		}
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
