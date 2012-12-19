package com.mutantamoeba.ld25;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.mutantamoeba.ld25.RoomTemplate.WallType;
import com.mutantamoeba.ld25.actors.BondEntity;
import com.mutantamoeba.ld25.actors.GameEntity;
import com.mutantamoeba.ld25.actors.GasVentEntity;
import com.mutantamoeba.ld25.actors.LaserTurretEntity;
import com.mutantamoeba.ld25.actors.ParticleEmitterEntity;
import com.mutantamoeba.ld25.actors.TextBox;
import com.mutantamoeba.ld25.actors.TrapDoorEntity;
import com.mutantamoeba.ld25.actors.TrapEntity;
import com.mutantamoeba.ld25.screens.GameScreen;
import com.mutantamoeba.ld25.tilemap.TileMap;
import com.mutantamoeba.ld25.utils.RandomNumbers;

public class GameWorld {
	static GameWorld instance;
	
	public static final int ROOM_SIZE = 5;

	public static final float SECRET_LAIR_INITIAL_HEALTH = 500f;

	private static final float DEFAULT_SPAWN_FREQUENCY = 8f;
	public int mapWidth, mapHeight;
	public int tileMapWidth, tileMapHeight;

	public RoomMap roomMap;
	public TileMap tileMap;
	ObjectMap<String, RoomTemplate>	roomTemplates = new ObjectMap<String, RoomTemplate>();
	
	private GameScreen gameScreen;
	private GameEconomy economy;
	private GameBondSpawner spawner;
	private GameScoreKeeper scoreKeeper;
	private float secretLairHealth = SECRET_LAIR_INITIAL_HEALTH;

	private boolean gameOver;
	
	
	public GameWorld(GameScreen gameScreen, int mapWidth, int mapHeight) {
		this.instance = this;
		this.gameScreen(gameScreen);
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
		roomMap = new RoomMap(this, mapWidth, mapHeight);
		this.tileMapWidth = mapWidth * ROOM_SIZE;
		this.tileMapHeight = mapHeight * ROOM_SIZE;
		tileMap = new TileMap(this, this.tileMapWidth, this.tileMapHeight);
		
		scoreKeeper = new GameScoreKeeper();
		
		economy = new GameEconomy();
		setSpawner(new GameBondSpawner(this, DEFAULT_SPAWN_FREQUENCY));
		createRoomTemplates();
	}

	public void tick(float delta) {
		if (!isGameOver()) {
			economy.tick(delta);
			getSpawner().tick(delta);
		}
	}
	
	private boolean isGameOver() {
		return gameOver;
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
		tpl.setCosts(500, 600, 800, 1000);
		
		tpl = addRoomTemplate("gas", 4);
		tpl.setCosts(roomCosts);
		tpl.setTrapEntity(WallType.OTHER, new GasVentEntity(gameScreen.texture, 41));
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
		tpl.setTrapEntity(WallType.OTHER, new TrapDoorEntity(gameScreen.texture, 57));
		
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
//		tpl.setTrapEntity(WallType.UPLEFT, new TrapEntity(gameScreen.texture, 35));		
//		tpl.setTrapEntity(WallType.UPRIGHT, new TrapEntity(gameScreen.texture, 34));
//		tpl.setTrapEntity(WallType.DOWNRIGHT, new TrapEntity(gameScreen.texture, 33));
//		tpl.setTrapEntity(WallType.DOWNLEFT, new TrapEntity(gameScreen.texture, 32));
		tpl.setTrapEntity(WallType.UPLEFT, new LaserTurretEntity(gameScreen.texture, 36));		
		tpl.setTrapEntity(WallType.UPRIGHT, new LaserTurretEntity(gameScreen.texture, 36));
		tpl.setTrapEntity(WallType.DOWNRIGHT, new LaserTurretEntity(gameScreen.texture, 36));
		tpl.setTrapEntity(WallType.DOWNLEFT, new LaserTurretEntity(gameScreen.texture, 36));

		
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
		tpl.setTrapEntity(WallType.UP, new TrapEntity(gameScreen.texture, 51));		
		tpl.setTrapEntity(WallType.RIGHT, new TrapEntity(gameScreen.texture, 49));
		tpl.setTrapEntity(WallType.DOWN, new TrapEntity(gameScreen.texture, 50));
		tpl.setTrapEntity(WallType.LEFT, new TrapEntity(gameScreen.texture, 48));
		
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
	
	public Array<Room> getEdgeRooms() {
		return roomMap.entryRooms;
	}

	/**
	 * @param spawner the spawner to set
	 */
	public void setSpawner(GameBondSpawner spawner) {
		this.spawner = spawner;
	}

	/**
	 * @return the spawner
	 */
	public GameBondSpawner getSpawner() {
		return spawner;
	}

	public void attackDestructRoom(float attackStrength, float delta) {
		if (getSecretLairHealth() > 0) {
			setSecretLairHealth(getSecretLairHealth() - (attackStrength * delta));		
			if (getSecretLairHealth() <= 0) {
				// send a message to the game logic indicating the end (eventually)
				// for now just spam explosions and disable editing
				
	//			GameScreen.instance().setPaused(true);
				float halfRoom = GameScreen.HTILE_SIZE * GameWorld.ROOM_SIZE;
				for (int i=0;i<roomMap._area;i++) {
					Room room = roomMap.get(i);
					if (room == null) continue;
					if (room.hasBonds()) {
						for (GameEntity ent:room.getEntities()) {
							if (ent instanceof BondEntity) {
								((BondEntity)ent).hurt(100);
							}
						}
					}
					for (int j=0;j<3;j++) {
						// spawn explosion generators
						ParticleEmitterEntity pee = new ParticleEmitterEntity(61);
						GameScreen.instance().addEntity(pee);
						room.addEntity(pee);
						pee.setColor(1, .2f + RandomNumbers.nextFloat()*0.5f, .2f, 0.5f);
						float xOffs = (RandomNumbers.nextFloat()-0.5f) * halfRoom;
						float yOffs = (RandomNumbers.nextFloat()-0.5f) * halfRoom;
						pee.setPosition(room.getWorldX() + halfRoom + xOffs, room.getWorldY() + halfRoom + yOffs);
					}
				}
				GameScreen.instance().sounds.trigger("explosion", 0.2f);
				GameScreen.instance().setAllowEditing(false);
				TextBox textBox = new TextBox(GameScreen.instance(), getScoreKeeper().getScoreString());
				GameScreen.instance().getUiStage().addActor(textBox);
				textBox.setPosition( (Gdx.graphics.getWidth()-textBox.getWidth())/2, (Gdx.graphics.getHeight()-textBox.getHeight())/2);
				setGameOver();
			}
		} 
	}

	private void setGameOver() {
		gameOver = true;
	}

	/**
	 * @param secretLairHealth the secretLairHealth to set
	 */
	public void setSecretLairHealth(float secretLairHealth) {
		this.secretLairHealth = secretLairHealth;
	}

	/**
	 * @return the secretLairHealth
	 */
	public float getSecretLairHealth() {
		return secretLairHealth;
	}

	public GameScoreKeeper getScoreKeeper() {
		return scoreKeeper;
	}

	public void setScoreKeeper(GameScoreKeeper scoreKeeper) {
		this.scoreKeeper = scoreKeeper;
	}
}
