package com.mutantamoeba.ld25;

import com.mutantamoeba.ld25.actors.GameEntity;
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
		if (xx == 0 && yy == 0 && traps[WallType.UPLEFT.val()] != null) {
			return traps[WallType.UPLEFT.val()].clone();
		} else if (xx == GameWorld.ROOM_SIZE-1 && yy == 0 && traps[WallType.UPRIGHT.val()] != null) {
			return traps[WallType.UPRIGHT.val()].clone();
		} else if (xx == GameWorld.ROOM_SIZE-1 && yy == GameWorld.ROOM_SIZE-1 && traps[WallType.DOWNRIGHT.val()] != null) {
			return traps[WallType.DOWNRIGHT.val()].clone();
		} else if (xx == 0 && yy == GameWorld.ROOM_SIZE-1 && traps[WallType.DOWNLEFT.val()] != null) {
			return traps[WallType.DOWNLEFT.val()].clone();
		} else if (xx == 0 && traps[WallType.LEFT.val()] != null) {
			return traps[WallType.LEFT.val()].clone();
		} else if (yy == 0 && traps[WallType.UP.val()] != null) {
			return traps[WallType.UP.val()].clone();
		} else if (xx == GameWorld.ROOM_SIZE-1 && traps[WallType.RIGHT.val()] != null) {
			return traps[WallType.RIGHT.val()].clone();
		} else if (yy == GameWorld.ROOM_SIZE-1 && traps[WallType.DOWN.val()] != null) {
			return traps[WallType.DOWN.val()].clone();
		} else if (traps[WallType.OTHER.val()] != null) {
			return traps[WallType.OTHER.val()].clone();
		}

		return null;
	}
}
