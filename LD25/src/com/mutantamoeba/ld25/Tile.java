package com.mutantamoeba.ld25;

public class Tile {
	int layers[] = new int[TileMap.TOTAL_LAYERS];
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
