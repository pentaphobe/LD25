package com.mutantamoeba.ld25.tilemap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
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
		
		// [@note this needs its own shader, or we need to render to texture]
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
		
		Gdx.gl.glEnable(GL11.GL_BLEND);
		Gdx.gl.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);		
		cache.begin();

		cache.draw(cacheID);			

		cache.end();
		Gdx.gl.glDisable(GL11.GL_BLEND);

		// [@screw-you not knowing I had to do this just wasted me 3 hours]
		// [@... apparently there's no stage management.]
		if (Gdx.graphics.isGL20Available()) {
			batch.setShader(world.gameScreen().shaderProgram);
		}
		
		
		super.draw(batch, parentAlpha);
	}

	public void setShader(ShaderProgram sp) {
		cache.setShader(sp);

	}	
	
	public ShaderProgram createShader() {
		// from SpriteCache.java
		
		if (!Gdx.graphics.isGL20Available()) return null;
		String vertexShader = "attribute vec4 " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
		+ "attribute vec4 " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
		+ "attribute vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
		+ "uniform mat4 u_projectionViewMatrix;\n" //
		+ "varying vec4 v_color;\n" //
		+ "varying vec2 v_texCoords;\n" //
		+ "\n" //
		+ "void main()\n" //
		+ "{\n" //
		+ " v_color = " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
		+ " v_texCoords = " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
		+ " gl_Position = u_projectionViewMatrix * " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
		+ "}\n";
		String fragmentShader = "#ifdef GL_ES\n" //
		+ "precision mediump float;\n" //
		+ "#endif\n" //
		+ "varying vec4 v_color;\n" //
		+ "varying vec2 v_texCoords;\n" //
		+ "uniform sampler2D u_texture;\n" //
		+ "void main()\n"//
		+ "{\n" //
		+ " gl_FragColor = v_color * texture2D(u_texture, v_texCoords);\n" //
		+ "}";
		ShaderProgram shader = new ShaderProgram(vertexShader, fragmentShader);
		if (shader.isCompiled() == false) throw new IllegalArgumentException("Error compiling shader: " + shader.getLog());
		return shader;
	}
}
