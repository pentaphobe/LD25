package com.mutantamoeba.ld25.tilemap;

import com.mutantamoeba.ld25.tilemap.TileSubset.Type;

public class TileSubsetMulti extends TileSubset {
	public int tileIndices[];
	public TileSubsetMulti(int tileIndices[]) {
		super(Type.MULTI);
		this.tileIndices = tileIndices;
	}
}