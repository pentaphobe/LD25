package com.mutantamoeba.ld25;

import com.badlogic.gdx.utils.Array;
import com.mutantamoeba.ld25.engine.Console;
import com.mutantamoeba.ld25.screens.GameScreen;

public class GameBondSpawner {

	/** For testing.  if TRUE then bonds are spawned in EVERY entry room per spawn tick
	 *  otherwise they are spawned in ONE of the entry rooms per spawn tick
	 */
	private static final boolean SPAWN_SCALES_TO_ENTRY_ROOMS = true;

	private static final int DEFAULT_WAVE_FREQUENCY = 8;
	private static final int DEFAULT_WAVE_FREQUENCY_DECREMENT = 1;	
	private static final int DEFAULT_MINIMUM_WAVE_FREQUENCY = 2;
	
	private static final int DEFAULT_WAVE_SIZE = 1;	// multiplied by number of rooms (if SPAWN_SCALES_TO_ENTRY_ROOMS is true)

	private static final int DEFAULT_WAVE_SIZE_INCREMENT = 1;

	private static final float DEFAULT_WAVE_TIME = 2f;
	
	GameWorld world;
	private float spawnFrequency;
	float spawnCounter;
	public int waveCounter = 0;
	public int waveFrequency = DEFAULT_WAVE_FREQUENCY;	// after how many spawns do we have a wave?
	int waveFrequencyDecrement = DEFAULT_WAVE_FREQUENCY_DECREMENT;
	float waveTime = DEFAULT_WAVE_TIME; // how long the wave lasts
	float waveTimeCounter = 0;
	boolean inWave = false;
	int waveSize = DEFAULT_WAVE_SIZE; // how many spawns in a wave?
	int waveSizeIncrement = DEFAULT_WAVE_SIZE_INCREMENT; // each wave increases in size by this much
	float spawnFrequencyDecrement = 0.05f;	// seconds
	GameBondSpawner(GameWorld world, float spawnFreq) {
		this.world = world;
		this.setSpawnFrequency(spawnFreq);
		this.spawnCounter = 0;
	}
	public void tick(float delta) {
		this.spawnCounter += delta;
		
		float spawnFreq = getSpawnFrequency();
		if (inWave) {
			spawnFreq *= 0.1f;
		}
		if (spawnCounter > spawnFreq) {
			spawnCounter = 0;
			
			if (inWave) {
				doWave(spawnFreq);
			} else {
				Console.debug("spawning");
				spawn();
				if (++waveCounter >= waveFrequency) {
					waveCounter = 0;
					inWave = true;		
					GameScreen.instance().sounds.trigger("fanfare", 0.75f);
				}
			}
		}

		
	}
	private void doWave(float delta) {
		Console.debug("in wave: %f/%f", waveTimeCounter, waveTime);
		waveTimeCounter += delta;
		if (waveTimeCounter >= waveTime) {
			increaseWaveDifficulties();	
			inWave = false;
			waveTimeCounter = 0;
			world.getScoreKeeper().addScore("waves survived", 1);
		}				
		// do a wave
		for (int i=0;i<waveSize;i++) {
			spawn();
		}
	}
	
	
	private void increaseWaveDifficulties() {
		waveSize += waveSizeIncrement;
		if (waveFrequency > DEFAULT_MINIMUM_WAVE_FREQUENCY) {
			waveFrequency -= waveFrequencyDecrement;
		}
		spawnFrequency -= spawnFrequencyDecrement;		
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
