package com.mutantamoeba.ld25;

import com.mutantamoeba.ld25.utils.ParameterMap;


public class TileMap extends ParameterMap<Tile> {
	public static final int TOTAL_LAYERS = 4;
	public static Tile empty = new Tile(new int[] {0});
	
	public TileMap(int w, int h) {
		super("tiles", w, h, empty);
	}

}
