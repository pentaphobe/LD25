package com.mutantamoeba.ld25;

import com.mutantamoeba.ld25.screens.GameScreen;
import com.mutantamoeba.ld25.tilemap.TileMap;

public class GameWorld {
	static GameWorld instance;
	
	public static final int ROOM_SIZE = 5;
	public int mapWidth, mapHeight;
	public int tileMapWidth, tileMapHeight;

	public RoomMap roomMap;
	public TileMap tileMap;
	private GameScreen gameScreen;
	private GameEconomy economy;
	
	public GameWorld(GameScreen gameScreen, int mapWidth, int mapHeight) {
		this.instance = this;
		this.gameScreen(gameScreen);
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
		roomMap = new RoomMap(this, mapWidth, mapHeight);
		this.tileMapWidth = mapWidth * ROOM_SIZE;
		this.tileMapHeight = mapHeight * ROOM_SIZE;
		tileMap = new TileMap(this, this.tileMapWidth, this.tileMapHeight);
		
		economy = new GameEconomy();
	}
	
	public void tick(float delta) {
		economy.tick(delta);
	}
	
	public static GameWorld instance() {
		return instance;
	}

	/**
	 * @param gameScreen the gameScreen to set
	 */
	public void gameScreen(GameScreen gameScreen) {
		this.gameScreen = gameScreen;
	}

	/**
	 * @return the gameScreen
	 */
	public GameScreen gameScreen() {
		return gameScreen;
	}

	/**
	 * @param economy the economy to set
	 */
	public void setEconomy(GameEconomy economy) {
		this.economy = economy;
	}

	/**
	 * @return the economy
	 */
	public GameEconomy getEconomy() {
		return economy;
	}
}
