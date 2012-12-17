package com.mutantamoeba.ld25;

public class RoomConfig {
	String type;
	int level;
	float health;
	private RoomTemplate template;
	RoomConfig(RoomTemplate template) {
		this.template = template;
		this.level = 0;
		this.health = 100;
	}
	boolean canUpgrade() {
		return this.level < this.template.maxLevel;
	}
}
