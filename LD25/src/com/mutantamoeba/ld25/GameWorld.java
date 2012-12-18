package com.mutantamoeba.ld25;

import com.badlogic.gdx.utils.ObjectMap;
import com.mutantamoeba.ld25.RoomTemplate.WallType;
import com.mutantamoeba.ld25.actors.GameEntity;
import com.mutantamoeba.ld25.screens.GameScreen;
import com.mutantamoeba.ld25.tilemap.TileMap;
import com.mutantamoeba.ld25.utils.RandomNumbers;

public class GameWorld {
	static GameWorld instance;
	
	public static final int ROOM_SIZE = 5;
	public int mapWidth, mapHeight;
	public int tileMapWidth, tileMapHeight;

	public RoomMap roomMap;
	public TileMap tileMap;
	ObjectMap<String, RoomTemplate>	roomTemplates = new ObjectMap<String, RoomTemplate>();
	
	private GameScreen gameScreen;
	private GameEconomy economy;
	private GameBondSpawner spawner;
	
	
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
		spawner = new GameBondSpawner(this, 1);
		createRoomTemplates();
	}

	public void tick(float delta) {
		economy.tick(delta);
		spawner.tick(delta);
	}
	
	public RoomTemplate addRoomTemplate(String name, int maxLevel) {
		RoomTemplate tpl = new RoomTemplate(name, maxLevel);
		roomTemplates.put(name, tpl);
		return tpl;
	}
	
	public RoomTemplate getRoomTemplate(String name) {
		if (!roomTemplates.containsKey(name)) return null;
		return roomTemplates.get(name);
	}
	
	
	private void createRoomTemplates() {
		RoomTemplate tpl;
		
		float roomCosts[] = new float[] { 1000, 1000, 2000, 3000 };
		
		tpl = addRoomTemplate("basic", 4);
		tpl.setCosts(200, 200, 400, 800);
		
		tpl = addRoomTemplate("gas", 4);
		tpl.setCosts(roomCosts);
		tpl.setTrapEntity(WallType.OTHER, new GameEntity(gameScreen.texture, 57));
		tpl.setObjectTiles(0, new int[] {
			-1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1,
			-1, -1, 40, -1, -1,
			-1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1			
		});
		tpl.setObjectTiles(1, new int[] {
			-1, -1, -1, -1, -1,
			-1, 40, -1, -1, -1,
			-1, -1, -1, -1, -1,
			-1, -1, -1, 40, -1,
			-1, -1, -1, -1, -1							
		});
		tpl.setObjectTiles(2, new int[] {
			-1, -1, -1, -1, -1,
			-1, -1, 40, -1, -1,
			-1, -1, -1, -1, -1,
			-1, 40, -1, 40, -1,
			-1, -1, -1, -1, -1							
		});
		tpl.setObjectTiles(3, new int[] {
			-1, -1, -1, -1, -1,
			-1, 40, -1, 40, -1,
			-1, -1, -1, -1, -1,
			-1, 40, -1, 40, -1,
			-1, -1, -1, -1, -1							
		});		
		
		tpl = addRoomTemplate("trapdoor", 4);
		tpl.setCosts(roomCosts);
		tpl.setObjectTiles(0, new int[] {
			-1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1,
			-1, -1, 56, -1, -1,
			-1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1			
		});	
		tpl.setObjectTiles(1, new int[] {
			-1, -1, -1, -1, -1,
			-1, -1, 56, -1, -1,
			-1, 56, -1, 56, -1,
			-1, -1, 56, -1, -1,
			-1, -1, -1, -1, -1			
		});	
		tpl.setObjectTiles(2, new int[] {
			-1, -1, -1, -1, -1,
			-1, 56, -1, 56, -1,
			-1, -1, 56, -1, -1,
			-1, 56, -1, 56, -1,
			-1, -1, -1, -1, -1			
		});	
		tpl.setObjectTiles(3, new int[] {
			-1, -1, 56, -1, -1,
			-1, 56, -1, 56, -1,
			56, -1, 56, -1, 56,
			-1, 56, -1, 56, -1,
			-1, -1, 56, -1, -1			
		});	
		
		tpl = addRoomTemplate("laser", 4);
		tpl.setCosts(roomCosts);
		tpl.setObjectTiles(0, new int[] {
			35, -1, -1, -1, -1,
			-1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1			
		});
		tpl.setObjectTiles(1, new int[] {
				35, -1, -1, -1, -1,
				-1, -1, -1, -1, -1,
				-1, -1, -1, -1, -1,
				-1, -1, -1, -1, -1,
				-1, -1, -1, -1, 33			
			});			
		tpl.setObjectTiles(2, new int[] {
				35, -1, -1, -1, -1,
				-1, -1, -1, -1, -1,
				-1, -1, -1, -1, -1,
				-1, -1, -1, -1, -1,
				32, -1, -1, -1, 33			
			});			
		tpl.setObjectTiles(3, new int[] {
				35, -1, -1, -1, 34,
				-1, -1, -1, -1, -1,
				-1, -1, -1, -1, -1,
				-1, -1, -1, -1, -1,
				32, -1, -1, -1, 33			
			});			
		
		
		tpl = addRoomTemplate("dart", 4);
		tpl.setCosts(roomCosts);
		tpl.setObjectTiles(0, new int[] {
			-1, -1, -1, -1, -1,
			48, -1, -1, -1, -1,
			-1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1			
		});		
		tpl.setObjectTiles(1, new int[] {
				-1, -1, -1, 51, -1,
				48, -1, -1, -1, -1,
				-1, -1, -1, -1, -1,
				-1, -1, -1, -1, -1,
				-1, -1, -1, -1, -1			
			});	
		tpl.setObjectTiles(2, new int[] {
				-1, -1, -1, -1, -1,
				48, -1, -1, -1, -1,
				-1, -1, -1, -1, -1,
				-1, -1, -1, -1, 49,
				-1, 50, -1, -1, -1			
			});	
		tpl.setObjectTiles(3, new int[] {
				-1, -1, -1, 51, -1,
				48, -1, -1, -1, -1,
				-1, -1, -1, -1, -1,
				-1, -1, -1, -1, 49,
				-1, 50, -1, -1, -1			
			});			
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

	public Room getRandomEdgeRoom() {
		if (roomMap.entryRooms.size > 0) {
			return roomMap.entryRooms.get(RandomNumbers.nextInt(roomMap.entryRooms.size));
		}
		return null;
	}
}
