package com.mutantamoeba.ld25;

public class RoomTemplate {
	String name;
	public int maxLevel;
	int objectTiles[][];	
	float cost[];	// 0=creation, the rest are upgrade prices
	RoomTemplate(String name, int maxLevel) {
		this.name = name;
		this.maxLevel = maxLevel;
		objectTiles = new int[maxLevel][GameWorld.ROOM_SIZE * GameWorld.ROOM_SIZE];
		cost = new float[maxLevel];
		clear();
	}
	public void clear() {
		for (int i=0;i<maxLevel;i++) {
			for (int j=0; j < GameWorld.ROOM_SIZE * GameWorld.ROOM_SIZE; j++) {
				objectTiles[i][j] = -1;
			}
		}
	}
	public void setObjectTiles(int level, int[] tileIndices) {
		for (int j=0; j < GameWorld.ROOM_SIZE * GameWorld.ROOM_SIZE; j++) {
			objectTiles[level][j] = tileIndices[j];
		}		
	}
	public void setCosts(float ...costs) {
		for (int i=0;i<costs.length && i<maxLevel;i++) {
			cost[i] = costs[i];
		}
	}

	public RoomConfig createConfig() {
		RoomConfig config = new RoomConfig(this);
		
		return config;
	}
	public void upgradeConfig(RoomConfig conf) {
		conf.level((int)Math.min(conf.level()+1, maxLevel));
		conf.health = 100;
	}
	public float getCost() {	
		return getCost(0);
	}
	public float getCost(int level) {
		return cost[level];
	}
}
