package com.mutantamoeba.ld25;

import com.mutantamoeba.ld25.screens.GameScreen;

public class GameWorld {
	public static final int ROOM_SIZE = 5;
	public int mapWidth, mapHeight;
	public int tileMapWidth, tileMapHeight;

	RoomMap roomMap;
	TileMap tileMap;
	GameScreen gameScreen;
	
	public GameWorld(GameScreen gameScreen, int mapWidth, int mapHeight) {
		this.gameScreen = gameScreen;
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
		roomMap = new RoomMap(mapWidth, mapHeight);
		this.tileMapWidth = mapWidth * ROOM_SIZE;
		this.tileMapHeight = mapHeight * ROOM_SIZE;
		tileMap = new TileMap(this.tileMapWidth, this.tileMapHeight);
	}
}
