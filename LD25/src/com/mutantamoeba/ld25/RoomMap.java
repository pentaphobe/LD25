package com.mutantamoeba.ld25;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mutantamoeba.ld25.actors.GameEntity;
import com.mutantamoeba.ld25.engine.Console;
import com.mutantamoeba.ld25.screens.GameScreen;
import com.mutantamoeba.ld25.tilemap.Tile;
import com.mutantamoeba.ld25.utils.ParameterMap;


/** The logical map
 * @author keili
 *
 */
public class RoomMap extends ParameterMap<Room> {
	private GameWorld world;
	Array<Room> entryRooms = new Array<Room>();
	
	public RoomMap(GameWorld world, int w, int h) {
		super("rooms", w, h, null);
		this.world = world;
	}
	public void makeTemplatedRoom(int x, int y, RoomConfig config) {
		makeBlankRoom(config, x, y, config.template().objectTiles[config.level()]);
		
	}
	public void makeTemplatedRoom(int x, int y, RoomTemplate roomTemplate) {
		makeTemplatedRoom(x, y, roomTemplate, 0);
	}
	public void makeTemplatedRoom(int x, int y, RoomTemplate roomTemplate, int level) {
		RoomConfig roomConfig = new RoomConfig(roomTemplate);
		makeBlankRoom(roomConfig, x, y, roomTemplate.objectTiles[level]);
//		// [@temp use real templates from a data file]
//		int idx = RandomNumbers.nextInt(5);
//		switch (idx) {
//		case 0:
//			makeBlankRoom(new RoomConfig("gas chamber", 3), x, y, new int[] {
//					-1, -1, -1, -1, -1,
//					-1, 40, -1, 40, -1,
//					-1, -1, -1, -1, -1,
//					-1, 40, -1, 40, -1,
//					-1, -1, -1, -1, -1			
//				});
//			break;
//		case 1:
//			makeBlankRoom(new RoomConfig("trap doors", 2), x, y, new int[] {
//					-1, -1, -1, -1, -1,
//					-1, -1, 56, -1, -1,
//					-1, 56, -1, 56, -1,
//					-1, -1, 56, -1, -1,
//					-1, -1, -1, -1, -1			
//				});
//			break;
//		case 2:
//			makeBlankRoom(new RoomConfig("trap doors", 3), x, y, new int[] {
//					-1, -1, -1, -1, -1,
//					-1, 57, -1, 57, -1,
//					-1, -1, 57, -1, -1,
//					-1, 57, -1, 57, -1,
//					-1, -1, -1, -1, -1			
//				});
//			break;
//		case 3:
//			makeBlankRoom(new RoomConfig("wall darts", 4), x, y, new int[] {
//					-1, -1, -1, 51, -1,
//					48, -1, -1, -1, -1,
//					-1, -1, -1, -1, -1,
//					-1, -1, -1, -1, 49,
//					-1, 50, -1, -1, -1			
//				});
//			break;
//		case 4:
//			makeBlankRoom(new RoomConfig("turrets", 4), x, y, new int[] {
//					35, -1, -1, -1, 34,
//					-1, -1, -1, -1, -1,
//					-1, -1, -1, -1, -1,
//					-1, -1, -1, -1, -1,
//					32, -1, -1, -1, 33			
//				});
//			break;
//		case 5:
//			makeBlankRoom(x, y, new int[] {
//					-1, -1, -1, -1, -1,
//					-1, -1, -1, -1, -1,
//					-1, -1, -1, -1, -1,
//					-1, -1, -1, -1, -1,
//					-1, -1, -1, -1, -1			
//				});
//			break;
//						
//		}
		
	}
//	public Room makeBlankRoom(int x, int y) {
//		return makeBlankRoom(new RoomConfig(), x, y, null);
//	}
//	public Room makeBlankRoom(int x, int y, int objects[]) {
//		return makeBlankRoom(new RoomConfig(), x, y, objects);
//	}
	public Room makeBlankRoom(RoomConfig config, int x, int y, int objects[]) {
		Room oldRoom = get(x, y);
		Room r = new Room(config, x, y);
		if (oldRoom != null) {
			// cleanup			
			r.inheritEntities(oldRoom);
			oldRoom.destroy();
			entryRooms.removeValue(oldRoom, true);
		}
		
		super.set(x, y, r);
		int floorId = this.world.gameScreen().gameTiles.getId("floor");
		int wallId = this.world.gameScreen().gameTiles.getId("wall");
		int wallIndices[] = this.world.gameScreen().gameTiles.getTileIndices(wallId);
		
		int tileX = x * GameWorld.ROOM_SIZE;
		int tileY = y * GameWorld.ROOM_SIZE;
		for (int yy = 0, offs=0; yy < GameWorld.ROOM_SIZE; yy++) {
			boolean top = yy == 0;
			boolean bottom = yy == GameWorld.ROOM_SIZE-1;
			
			for (int xx = 0; xx < GameWorld.ROOM_SIZE; xx++, offs++) {
				Tile tile = new Tile();
				tile.layers[Tile.FLOOR_LAYER] = this.world.gameScreen().gameTiles.getTileIndex(xx, yy, floorId);
				boolean left = xx == 0;
				boolean right = xx == GameWorld.ROOM_SIZE-1;
				if (left || right || top || bottom) {
					// we're on an edge, so apply the nine patch
					int index = -1;
					if (left) {
						if (top) {
							index = 0;
						} else if (bottom) {
							index = 6;
						} else {
							index = 3;
						}
					} else if (right) {
						if (top) {
							index = 2;
						} else if (bottom) {
							index = 8;
						} else {
							index = 5;
						}
					} else if (top) {
						index = 1;
					} else if (bottom) {
						index = 7;
					}
//					Console.debug("creating wall tileIndex:%d %s %s %s %s", index, left ? "left":"", right ? "right":"", top ? "top":"", bottom ? "bottom":"");
					tile.layers[Tile.WALL_LAYER] = wallIndices[index];
				}
				if (objects != null && objects[offs] != -1) {
					tile.layers[Tile.HAZARD_LAYER] = objects[offs];
					r.createTrapEntity(xx, yy);
				}
				world.tileMap.set(tileX + xx, tileY + yy, tile);
			}
		}
		updateDoors(x, y);
		if (x == 0 || y == 0 || x == _w-1 | y == _h-1) {
			entryRooms.add(r);
//			Console.debug("added entry room: %s", r);
//			Console.debug("all entries:");
//			for (Room entryRoom:entryRooms) {
//				Console.debug("  %s", entryRoom);
//			}
		}
		return r;
	}
	/* (non-Javadoc)
	 * @see com.mutantamoeba.ld25.utils.ParameterMap#set(int, int, java.lang.Object)
	 */
	@Override
	public void set(int x, int y, Room v) {		
		super.set(x, y, v);
		updateDoors(x, y);
	}
	
	public void remove(int x, int y) {
		int tileX = x * GameWorld.ROOM_SIZE;
		int tileY = y * GameWorld.ROOM_SIZE;
		for (int yy = 0, offs=0; yy < GameWorld.ROOM_SIZE; yy++) {			
			for (int xx = 0; xx < GameWorld.ROOM_SIZE; xx++, offs++) {
				Tile tile = this.world.tileMap.get(tileX + xx, tileY + yy);
				tile.clear();
			}
		}
		Room room = get(x, y);
		if (entryRooms.contains(room, false)) {
			entryRooms.removeValue(room, false);
		}

		set(x, y, null);
		updateDoors(x-1, y);
		updateDoors(x+1, y);
		updateDoors(x, y-1);
		updateDoors(x, y+1);
	}
	
	public void setRoomTile(Room r, int rx, int ry, int layer, int tileIdx) {
		int tileX = r.getMapX() * GameWorld.ROOM_SIZE + rx;
		int tileY = r.getMapY() * GameWorld.ROOM_SIZE + ry;
		Tile tile = world.tileMap.get(tileX, tileY);
		if (tile == null) return;
		tile.layers[layer] = tileIdx;
	}
	
	public Tile setRoomTile(Room r, int rx, int ry) {
		int tileX = r.getMapX() * GameWorld.ROOM_SIZE + rx;
		int tileY = r.getMapY() * GameWorld.ROOM_SIZE + ry;
		Tile tile = world.tileMap.get(tileX, tileY);
		return tile;
	}	
	
	private void updateDoors(int x, int y) {
//		Console.debug("updateDoors(%d, %d)", x, y);
		Room me = get(x, y);
//		Console.debug("  before: %s", me);
		if (me == null) return;
		Room left = get(x-1, y);
		Room right = get(x+1, y);
		Room up = get(x, y-1);
		Room down = get(x, y+1);
		
		// I wrote ParameterMap<?>.get() to not return null, in this case we want null. 
		me.up = up == me ? null : up;
		me.down = down == me ? null : down;
		me.left = left == me ? null : left;
		me.right = right == me ? null : right;
		
		int wallId = this.world.gameScreen().gameTiles.getId("wall");
		int wallIndices[] = this.world.gameScreen().gameTiles.getTileIndices(wallId);
		// the second line in each of these assignments looks weird because it's defined
		// in terms of the target room ("me") just for clarity of reading 
		// (one might argue that if it was so clear, no explanation would be needed)
		if (me.up != null) {
			setRoomTile(me, 2, 0, Tile.WALL_LAYER, -1);
			setRoomTile(me, 2, -1, Tile.WALL_LAYER, -1);
			me.up.down = me;
		} else {
			setRoomTile(me, 2, 0, Tile.WALL_LAYER, wallIndices[1]);
			setRoomTile(me, 2, -1, Tile.WALL_LAYER, -1);			
		}
		if (me.down != null) {
			setRoomTile(me, 2, 4, Tile.WALL_LAYER, -1);
			setRoomTile(me, 2, 5, Tile.WALL_LAYER, -1);
			me.down.up = me;
		}else {
			setRoomTile(me, 2, 4, Tile.WALL_LAYER, wallIndices[7]);
			setRoomTile(me, 2, 5, Tile.WALL_LAYER, -1);
		}
		if (me.left != null) {
			setRoomTile(me, 0, 2, Tile.WALL_LAYER, -1);
			setRoomTile(me, -1, 2, Tile.WALL_LAYER, -1);
			me.left.right = me;
		}else {
			setRoomTile(me, 0, 2, Tile.WALL_LAYER, wallIndices[3]);
			setRoomTile(me, -1, 2, Tile.WALL_LAYER, -1);
		}
		if (me.right != null) {
			setRoomTile(me, 4, 2, Tile.WALL_LAYER, -1);
			setRoomTile(me, 5, 2, Tile.WALL_LAYER, -1);
			me.right.left = me;
		}else {
			setRoomTile(me, 4, 2, Tile.WALL_LAYER, wallIndices[5]);
			setRoomTile(me, 5, 2, Tile.WALL_LAYER, -1);
		}
//		Console.debug("   after: %s", me);
	
	}
	
	public Room getRoomAt(GameEntity e) {
		Vector2 pos = new Vector2(e.getX(), e.getY());
		pos.div(GameScreen.TILE_SIZE * GameWorld.ROOM_SIZE);
		int mx = (int)pos.x;
		int my = (int)pos.y;
		return get(mx, my);
	}
}
