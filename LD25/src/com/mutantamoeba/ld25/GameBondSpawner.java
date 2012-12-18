package com.mutantamoeba.ld25;

import com.mutantamoeba.ld25.engine.Console;
import com.mutantamoeba.ld25.screens.GameScreen;

public class GameBondSpawner {
	GameWorld world;
	float spawnFrequency;
	float spawnCounter;
	GameBondSpawner(GameWorld world, float spawnFreq) {
		this.world = world;
		this.spawnFrequency = spawnFreq;
		this.spawnCounter = 0;
	}
	public void tick(float delta) {
		this.spawnCounter += delta;
		while (spawnCounter > spawnFrequency) {
			spawnCounter -= spawnFrequency;
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
}
