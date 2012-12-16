package com.mutantamoeba.ld25.tilemap;

public class Tile {
	public static final int FLOOR_LAYER = 0;
	public static final int HAZARD_LAYER = 1;
	public static final int WALL_LAYER = 2;
	
	public int layers[] = new int[TileMap.TOTAL_LAYERS];
	public Tile() {
		for (int i=0;i<TileMap.TOTAL_LAYERS;i++) {
			layers[i] = -1;
		}
	}
	public Tile(int layers[]) {
		this();
		for (int i=0;i<TileMap.TOTAL_LAYERS && i < layers.length;i++) {
			this.layers[i] = layers[i];
		}
	}
	public int getLayer(int layerNum) {
		if (layerNum < 0 || layerNum >= TileMap.TOTAL_LAYERS) {
			return -1;
		}
		return layers[layerNum];
	}
	public void setLayer(int layerNum, int tileIndex) {
		if (layerNum < 0 || layerNum >= TileMap.TOTAL_LAYERS) {
			return;
		}
		layers[layerNum] = tileIndex;
	}
}