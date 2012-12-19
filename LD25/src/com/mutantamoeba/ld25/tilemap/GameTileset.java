package com.mutantamoeba.ld25.tilemap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.mutantamoeba.ld25.engine.Console;
import com.mutantamoeba.ld25.utils.RandomNumbers;

public class GameTileset {
	public Texture texture;
	int		tileSize;
	ObjectMap<String, Integer>	setNameToIndex = new ObjectMap<String, Integer>();
	Array<TileSubset> tileSubsets = new Array<TileSubset>();
	
	public GameTileset(Texture texture, int tileSize) {
		 this.texture = texture;
		 this.tileSize = tileSize;
	}

	public TileSubset addSubset(String name, TileSubset.Type type, int ...tileIndices) {
		TileSubset set;
		switch (type) {
		case SINGLE:
			set = TileSubset.createSingle(tileIndices[0]);
			break;
		case MULTI:
			set = TileSubset.createMulti(tileIndices);
			break;
		case RAND:
			set = TileSubset.createRand(tileIndices);
			break;
		case NINEPATCH:
			set = TileSubset.createNinePatch(tileIndices);
			break;
		default:
			Console.debug("definition of %s [%s] contains an unknown type: %s", name, tileIndices, type);
			return null;
		}
		
		int idx = tileSubsets.size;
		setNameToIndex.put(name, idx);
		tileSubsets.add(set);
		
		// [@temp DEBUGGING]
//		String listStr = "";
//		for (int i=0;i<tileIndices.length;i++) {
//			listStr += Integer.toString(tileIndices[i]);
//			if (i < tileIndices.length-1) {
//				listStr += ", ";
//			}
//		}
//
//		Console.debug("added tileset %s [%s] {%s}", name, type, listStr);
//		
		// END DEBUGGING
		
		return set;
	}

	/** Returns an actual tile index based on the position and id
	 * @param x tile map position
	 * @param y tile map position
	 * @param tileId the tile subset id
	 * @return tile index as specified by the subset
	 */
	public int getTileIndex(int x, int y, int tileId) {
		TileSubset subset = tileSubsets.get(tileId);
		int indices[];
		int index;
		switch (subset.type) {
		case SINGLE:
			return ((TileSubsetSingle)subset).tileIndex;
		case MULTI:
			indices = ((TileSubsetMulti)subset).tileIndices; 
			index = (int)((x+y) % indices.length);
			return indices[index];
		case RAND:
			indices = ((TileSubsetRand)subset).tileIndices; 
			index = (int)((RandomNumbers.locHash(x, y+x) & 0xfffffff) % indices.length);
//			Console.debug("%d, %d - %d", x, y, index);
			return indices[index];			
		default:
			return 0;
		}
	}

	public int getId(String string) {
		if (setNameToIndex.containsKey(string)) {
			return setNameToIndex.get(string);
		}
		return -1;
	}

	public int[] getTileIndices(int tileId) {
		if (tileId == -1) return null;
		
		TileSubset subset = tileSubsets.get(tileId);
		switch (subset.type) {
		case MULTI:
			int indices[] = ((TileSubsetMulti)subset).tileIndices; 
			return indices;
		default:
			return null;
		}		
	}
}
