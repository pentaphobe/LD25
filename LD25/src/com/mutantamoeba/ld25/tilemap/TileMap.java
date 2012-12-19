package com.mutantamoeba.ld25.tilemap;

import com.mutantamoeba.ld25.GameWorld;
import com.mutantamoeba.ld25.utils.ParameterMap;


/** The raw tile map (for rendering only)
 * @author keili
 *
 */
public class TileMap extends ParameterMap<Tile> {
	public static final int TOTAL_LAYERS = 4;
	public static Tile empty = new Tile();
	private GameWorld world;
	
	public TileMap(GameWorld world, int w, int h) {
		super("tiles", w, h, empty);
		if (empty.layers[0] == -1) {
			empty.layers[0] = world.gameScreen().gameTiles.getTileIndex(0, 0, world.gameScreen().gameTiles.getId("blank"));
		}
		this.world = world;
		
//		// put 
//		for (int y=0, offs =0;y<_h;y++) {
//			for (int x=0; x<_w; x++, offs++) {
//				Tile t = new Tile();
//				t.setTileMapX(x);
//				t.setTileMapY(y);
//			}
//		}
	}

}
