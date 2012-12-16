package com.mutantamoeba.ld25.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer10;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mutantamoeba.ld25.GameWorld;
import com.mutantamoeba.ld25.engine.Console;

public class RoomRenderer extends Actor {
	ImmediateModeRenderer debugRenderer;
	GameWorld world;
	public RoomRenderer(GameWorld world) {
		super(); 
		setSize(world.mapWidth * world.ROOM_SIZE * GameScreen.TILE_SIZE, world.mapHeight * world.ROOM_SIZE * GameScreen.TILE_SIZE);
		this.world = world;
		if (Gdx.graphics.isGL20Available())
			debugRenderer = new ImmediateModeRenderer20(64, false, true, 0);
		else
			debugRenderer = new ImmediateModeRenderer10(64);
	}
	
	@Override
	public void draw(SpriteBatch batch, float delta) {
		// TEMPORARY
				
		debugRenderer.begin(batch.getProjectionMatrix(), GL10.GL_LINES);
		debugRenderer.color(1, 0, 1, 0.5f);
		int roomSizeInTiles = GameScreen.TILE_SIZE * world.ROOM_SIZE;
		
		for (int y=0;y<world.mapWidth;y++) {
			float ry = y * roomSizeInTiles;
			for (int x=0;x<world.mapHeight;x++) {
				float rx = x * roomSizeInTiles;
				debugRenderer.vertex(rx, ry, 0);
				debugRenderer.vertex(rx, ry+roomSizeInTiles, 0);
				
				debugRenderer.vertex(rx, ry+roomSizeInTiles, 0);
				debugRenderer.vertex(rx+roomSizeInTiles, ry+roomSizeInTiles, 0);
				
				debugRenderer.vertex(rx+roomSizeInTiles, ry+roomSizeInTiles, 0);
				debugRenderer.vertex(rx+roomSizeInTiles, ry, 0);
				
				debugRenderer.vertex(rx+roomSizeInTiles, ry, 0);
				debugRenderer.vertex(rx, ry, 0);
								
				if (debugRenderer.getNumVertices() == 64) {
					debugRenderer.end();
					debugRenderer.begin(batch.getProjectionMatrix(), GL10.GL_LINES);					
				}
			}
		}
		debugRenderer.end();
		// END TEMPORARY		
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Actor#act(float)
	 */
	@Override
	public void act(float delta) {
		
		super.act(delta);
	}
}
