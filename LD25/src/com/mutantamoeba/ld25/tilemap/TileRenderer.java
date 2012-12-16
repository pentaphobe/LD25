package com.mutantamoeba.ld25.tilemap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mutantamoeba.ld25.GameWorld;
import com.mutantamoeba.ld25.engine.Console;
import com.mutantamoeba.ld25.screens.GameScreen;

public class TileRenderer extends Actor {
	GameWorld world;
	TileMap tileMap;
	GameTileset tileSet;
	SpriteCache cache;
	int cacheID;
	int textureTileWidth, textureTileHeight;

	public TileRenderer(GameWorld world, GameTileset tileSet) {
		this.world = world;
		tileMap = world.tileMap;
		this.tileSet = tileSet;
		textureTileWidth = tileSet.texture.getWidth() / GameScreen.TILE_SIZE;
		textureTileHeight = tileSet.texture.getHeight() / GameScreen.TILE_SIZE;
		
		this.cache = new SpriteCache(this.world.tileMap._area
				* this.world.tileMap.TOTAL_LAYERS, true);
	}

	public void updateFromMap() {
		cache.clear();
		cache.beginCache();
		
		for (int layer = 0; layer < tileMap.TOTAL_LAYERS; layer++) {
			for (int y = 0, offs = 0; y < tileMap._h; y++) {
				int tileY = y * (GameScreen.TILE_SIZE);
				for (int x = 0; x < tileMap._w; x++, offs++) {
					int tileX = x * (GameScreen.TILE_SIZE);
					Tile t = tileMap.get(offs);

//					int tileId = t.getLayer(layer);
//					if (tileId == -1) {
//						continue;
//					}
//					int tileIndex = tileSet.getTileIndex(x, y, tileId); 
//					Console.debug("tileId:%d, tileIndex:%d", tileId, tileIndex);
					
					int tileIndex = t.getLayer(layer);
					if (tileIndex == -1) {
						continue;
					}
					int texX = tileIndex % textureTileWidth;
					int texY = tileIndex / textureTileWidth;
					cache.add(tileSet.texture, tileX, tileY, texX * GameScreen.TILE_SIZE, texY
							* GameScreen.TILE_SIZE, GameScreen.TILE_SIZE, GameScreen.TILE_SIZE);
				}
			}
		}
		cacheID = cache.endCache();
	}
	
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Actor#draw(com.badlogic.gdx.graphics.g2d.SpriteBatch, float)
	 */
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		cache.setProjectionMatrix(batch.getProjectionMatrix());
//		batch.enableBlending();
//		batch.setBlendFunction(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		cache.begin();
		Gdx.gl.glEnable(GL11.GL_BLEND);
		Gdx.gl.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		cache.draw(cacheID);			
		Gdx.gl.glDisable(GL11.GL_BLEND);
//		batch.disableBlending();
		cache.end();
//		super.draw(batch, parentAlpha);
	}	
}
