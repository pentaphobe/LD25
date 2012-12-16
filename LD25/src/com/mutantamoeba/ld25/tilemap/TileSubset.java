package com.mutantamoeba.ld25.tilemap;

public class TileSubset {
	public enum Type {
		SINGLE,
		MULTI,
		NINEPATCH
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
	public static TileSubsetNinePatch createNinePatch(int indices[]) {
		return new TileSubsetNinePatch(indices);
	}
	public String toString() {
		switch (type) {
		case SINGLE: return "TileSubset.SINGLE";
		case MULTI: return "TileSubset.MULTI";
		case NINEPATCH: return "TileSubset.NINEPATCH";
		}
		return "TileSubset";
	}
	
}
