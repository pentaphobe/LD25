package com.mutantamoeba.ld25;

public class RoomConfig {
	String type;
	int level;
	float health;
	RoomConfig(String type, int level) {
		this.type = type;
		this.level = level;
		this.health = 100;
	}
	RoomConfig() {
		this("vanilla", 1);
	}
}
