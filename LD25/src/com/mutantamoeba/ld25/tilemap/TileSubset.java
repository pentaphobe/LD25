package com.mutantamoeba.ld25.tilemap;

public class TileSubset {
	public enum Type {
		SINGLE,	// a single tile
		MULTI,	// a tile which spans more than a single tile (wraps around)
		RAND,	// choose randomly from a set of tiles
		NINEPATCH	// the set of tiles represents a box
	}
	public Type type;
	public TileSubset(Type type) {
		this.type = type;
	}	
	public static TileSubsetSingle createSingle(int idx) {
		return new TileSubsetSingle(idx);
	}
	public static TileSubsetMulti createMulti(int indices[]) {
		return new TileSubsetMulti(indices);
	}
	public static TileSubsetRand createRand(int indices[]) {
		return new TileSubsetRand(indices);
	}
	
	public static TileSubsetNinePatch createNinePatch(int indices[]) {
		return new TileSubsetNinePatch(indices);
	}
	public String toString() {
		switch (type) {
		case SINGLE: return "TileSubset.SINGLE";		
		case MULTI: return "TileSubset.MULTI";
		case RAND: return "TileSubset.RAND";
		case NINEPATCH: return "TileSubset.NINEPATCH";
		}
		return "TileSubset";
	}
	
}
