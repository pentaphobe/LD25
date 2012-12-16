package com.mutantamoeba.ld25;

import com.mutantamoeba.ld25.utils.ParameterMap;


public class TileMap extends ParameterMap<Tile> {

	public TileMap(int w, int h) {
		super("tiles", w, h, null);
	}

}
