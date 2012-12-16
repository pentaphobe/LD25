package com.mutantamoeba.ld25.actors;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mutantamoeba.ld25.GameWorld;
import com.mutantamoeba.ld25.Room;
import com.mutantamoeba.ld25.engine.Console;
import com.mutantamoeba.ld25.screens.GameScreen;
import com.mutantamoeba.ld25.utils.RandomNumbers;

public class GameEntity extends Actor {	
	TextureRegion region;
	Room room;
	public GameEntity(TextureRegion region) {
		super();
		this.region = region;
	}
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Actor#draw(com.badlogic.gdx.graphics.g2d.SpriteBatch, float)
	 */
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		batch.draw(region, getX(), getY());
		
//		batch.draw(GameScreen.texture, getX() - getWidth()/2, getY() - getHeight()/2, 32, 32, 32 /*texX*/, 40 /*texY*/, 32, 32, false, false);
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Actor#act(float)
	 */
	@Override
	public void act(float delta) {
		setPosition(getX() + RandomNumbers.nextFloat() - 0.5f, getY() + RandomNumbers.nextFloat() - 0.5f);
		// TODO Auto-generated method stub
		super.act(delta);
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Actor#setPosition(float, float)
	 */
	@Override
	public void setPosition(float x, float y) {		
		super.setPosition(x, y);
		updateRoom((int)(x / (GameWorld.ROOM_SIZE * GameScreen.TILE_SIZE)), (int)(y / (GameWorld.ROOM_SIZE * GameScreen.TILE_SIZE)));
	}
	
	private void updateRoom(int x, int y) {
		EntityGroup par = ((EntityGroup)this.getParent());
		Room oldRoom = room;
		room = par.world.roomMap.get(x, y);
//		Console.debug("(%d, %d) oldRoom:%s room:%s", x, y, oldRoom, room);
		if (oldRoom != room) {
//			Console.debug("changed rooms to %s [%d, %d]", room, x, y);
		}
	}
	
}

