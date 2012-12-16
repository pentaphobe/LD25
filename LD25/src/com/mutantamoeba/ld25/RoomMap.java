package com.mutantamoeba.ld25;

import com.mutantamoeba.ld25.tilemap.Tile;
import com.mutantamoeba.ld25.utils.ParameterMap;
import com.mutantamoeba.ld25.utils.RandomNumbers;


/** The logical map
 * @author keili
 *
 */
public class RoomMap extends ParameterMap<Room> {
	private GameWorld world;

	public RoomMap(GameWorld world, int w, int h) {
		super("rooms", w, h, null);
		this.world = world;
	}
	public void makeTemplatedRoom(int x, int y /*, Template template */) {
		// [@temp use real templates from a data file]
		int idx = RandomNumbers.nextInt(5);
		switch (idx) {
		case 0:
			makeBlankRoom(x, y, new int[] {
					-1, -1, -1, -1, -1,
					-1, 40, -1, 40, -1,
					-1, -1, -1, -1, -1,
					-1, 40, -1, 40, -1,
					-1, -1, -1, -1, -1			
				});
			break;
		case 1:
			makeBlankRoom(x, y, new int[] {
					-1, -1, -1, -1, -1,
					-1, -1, 56, -1, -1,
					-1, 56, -1, 56, -1,
					-1, -1, 56, -1, -1,
					-1, -1, -1, -1, -1			
				});
			break;
		case 2:
			makeBlankRoom(x, y, new int[] {
					-1, -1, -1, -1, -1,
					-1, 57, -1, 57, -1,
					-1, -1, 57, -1, -1,
					-1, 57, -1, 57, -1,
					-1, -1, -1, -1, -1			
				});
			break;
		case 3:
			makeBlankRoom(x, y, new int[] {
					-1, -1, -1, 51, -1,
					48, -1, -1, -1, -1,
					-1, -1, -1, -1, -1,
					-1, -1, -1, -1, 49,
					-1, 50, -1, -1, -1			
				});
			break;
		case 4:
			makeBlankRoom(x, y, new int[] {
					35, -1, -1, -1, 34,
					-1, -1, -1, -1, -1,
					-1, -1, -1, -1, -1,
					-1, -1, -1, -1, -1,
					32, -1, -1, -1, 33			
				});
			break;
		case 5:
			makeBlankRoom(x, y, new int[] {
					-1, -1, -1, -1, -1,
					-1, -1, -1, -1, -1,
					-1, -1, -1, -1, -1,
					-1, -1, -1, -1, -1,
					-1, -1, -1, -1, -1			
				});
			break;
						
		}
		
	}
	public Room makeBlankRoom(int x, int y) {
		return makeBlankRoom(x, y, null);
	}
	public Room makeBlankRoom(int x, int y, int objects[]) {
		Room r = get(x, y);
		if (r != null) {
			return r;
		}
		r = new Room();
		set(x, y, r);
		int floorId = this.world.gameScreen.gameTiles.getId("floor");
		int wallId = this.world.gameScreen.gameTiles.getId("wall");
		int wallIndices[] = this.world.gameScreen.gameTiles.getTileIndices(wallId);
		
		int tileX = x * GameWorld.ROOM_SIZE;
		int tileY = y * GameWorld.ROOM_SIZE;
		for (int yy = 0, offs=0; yy < GameWorld.ROOM_SIZE; yy++) {
			boolean top = yy == 0;
			boolean bottom = yy == GameWorld.ROOM_SIZE-1;
			
			for (int xx = 0; xx < GameWorld.ROOM_SIZE; xx++, offs++) {
				Tile tile = new Tile();
				tile.layers[0] = this.world.gameScreen.gameTiles.getTileIndex(xx, yy, floorId);
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
					tile.layers[1] = wallIndices[index];
				}
				if (objects != null && objects[offs] != -1) {
					tile.layers[2] = objects[offs];
				}
				world.tileMap.set(tileX + xx, tileY + yy, tile);
			}
		}
		return r;
	}
}
