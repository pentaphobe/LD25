package com.mutantamoeba.ld25.tilemap;

import com.mutantamoeba.ld25.tilemap.TileSubset.Type;

public class TileSubsetSingle extends TileSubset {
	public int tileIndex;

	public TileSubsetSingle(int idx) {
		super(Type.SINGLE);
		tileIndex = idx;
	}
}
