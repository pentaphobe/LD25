package com.mutantamoeba.ld25;

import com.mutantamoeba.ld25.screens.GameScreen;
import com.mutantamoeba.ld25.tilemap.TileMap;

public class GameWorld {
	public static final int ROOM_SIZE = 5;
	public int mapWidth, mapHeight;
	public int tileMapWidth, tileMapHeight;

	public RoomMap roomMap;
	public TileMap tileMap;
	public GameScreen gameScreen;
	
	public GameWorld(GameScreen gameScreen, int mapWidth, int mapHeight) {
		this.gameScreen = gameScreen;
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
		roomMap = new RoomMap(this, mapWidth, mapHeight);
		this.tileMapWidth = mapWidth * ROOM_SIZE;
		this.tileMapHeight = mapHeight * ROOM_SIZE;
		tileMap = new TileMap(this, this.tileMapWidth, this.tileMapHeight);
	}
}
