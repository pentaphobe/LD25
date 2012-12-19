package com.mutantamoeba.ld25;

import com.mutantamoeba.ld25.actors.TrapEntity;

public class RoomTemplate {
	public enum WallType {
		OTHER(0), 
		UP(1), DOWN(2), LEFT(3), RIGHT(4),
		UPLEFT(5), UPRIGHT(6), DOWNRIGHT(7), DOWNLEFT(8);
		private int numVal;
		WallType(int numVal) { this.numVal = numVal; }
		public int val() {
			return numVal;
		}
	};
	
	String name;
	public int maxLevel;
	int objectTiles[][];	
	float cost[];	// 0=creation, the rest are upgrade prices
	TrapEntity traps[] = new TrapEntity[9];
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
	public void setTrapEntity(WallType wallType, TrapEntity entity) {
		traps[wallType.val()] = entity;
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
	public TrapEntity createTrapEntity(int xx, int yy) {
		TrapEntity trap = null;
		if (xx == 0 && yy == 0 && traps[WallType.UPLEFT.val()] != null) {
			trap = traps[WallType.UPLEFT.val()].clone();
			trap.setLocation(WallType.UPLEFT);
		} else if (xx == GameWorld.ROOM_SIZE-1 && yy == 0 && traps[WallType.UPRIGHT.val()] != null) {
			trap = traps[WallType.UPRIGHT.val()].clone();
			trap.setLocation(WallType.UPRIGHT);
		} else if (xx == GameWorld.ROOM_SIZE-1 && yy == GameWorld.ROOM_SIZE-1 && traps[WallType.DOWNRIGHT.val()] != null) {
			trap = traps[WallType.DOWNRIGHT.val()].clone();
			trap.setLocation(WallType.DOWNRIGHT);
		} else if (xx == 0 && yy == GameWorld.ROOM_SIZE-1 && traps[WallType.DOWNLEFT.val()] != null) {
			trap = traps[WallType.DOWNLEFT.val()].clone();
			trap.setLocation(WallType.DOWNLEFT);
		} else if (xx == 0 && traps[WallType.LEFT.val()] != null) {
			trap = traps[WallType.LEFT.val()].clone();
			trap.setLocation(WallType.LEFT);
		} else if (yy == 0 && traps[WallType.UP.val()] != null) {
			trap = traps[WallType.UP.val()].clone();
			trap.setLocation(WallType.UP);
		} else if (xx == GameWorld.ROOM_SIZE-1 && traps[WallType.RIGHT.val()] != null) {
			trap = traps[WallType.RIGHT.val()].clone();
			trap.setLocation(WallType.RIGHT);
		} else if (yy == GameWorld.ROOM_SIZE-1 && traps[WallType.DOWN.val()] != null) {
			trap = traps[WallType.DOWN.val()].clone();
			trap.setLocation(WallType.DOWN);
		} else if (traps[WallType.OTHER.val()] != null) {
			trap = traps[WallType.OTHER.val()].clone();
			trap.setLocation(WallType.OTHER);
		}
		return trap;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
