package com.mutantamoeba.ld25.tilemap;

import com.mutantamoeba.ld25.tilemap.TileSubset.Type;

public class TileSubsetRand extends TileSubset {
	public int tileIndices[];
	public TileSubsetRand(int tileIndices[]) {
		super(Type.RAND);
		this.tileIndices = tileIndices;
	}
}