package com.mutantamoeba.ld25;

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
			empty.layers[0] = world.gameScreen.gameTiles.getTileIndex(0, 0, world.gameScreen.gameTiles.getId("blank"));
		}
		this.world = world;
	}

}
