package com.mutantamoeba.ld25.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.mutantamoeba.ld25.GameWorld;
import com.mutantamoeba.ld25.Room;
import com.mutantamoeba.ld25.screens.GameScreen;
import com.mutantamoeba.ld25.utils.RandomNumbers;

public class GameEntity extends Group {	
	TextureRegion region;
	Room room;
	boolean restrictToRoom = true;
	float scale=1f, rotation=90f;
	public GameEntity(Texture tex, int tileIndex) {
		super();
		int texTileWidth = tex.getWidth() / GameScreen.TILE_SIZE;
		int tileX = tileIndex % texTileWidth;
		int tileY = tileIndex / texTileWidth;
		this.setOrigin(GameScreen.TILE_SIZE / 2f, GameScreen.TILE_SIZE / 2f);
		this.region = new TextureRegion(tex, tileX * GameScreen.TILE_SIZE, tileY * GameScreen.TILE_SIZE, GameScreen.TILE_SIZE, GameScreen.TILE_SIZE);
	}
	public GameEntity(TextureRegion region) {
		super();
		this.region = region;
		this.setOrigin(GameScreen.TILE_SIZE / 2f, GameScreen.TILE_SIZE / 2f);
	}
	public GameEntity(GameEntity other) {
		super();
		this.region = other.region;
		this.setOrigin(other.getOriginX(), other.getOriginY());
	}

	public GameEntity clone() {
		return new GameEntity(this);
	}
	public void destroy() {
		GameWorld.instance().gameScreen().removeEntity(this);
		if (room != null) {
			room.removeEntity(this);
		}
	}
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Actor#draw(com.badlogic.gdx.graphics.g2d.SpriteBatch, float)
	 */
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
//		super.draw(batch, parentAlpha);
		
//		batch.draw(region, getX() - getOriginX(), getY() - getOriginY());
		batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), GameScreen.TILE_SIZE, GameScreen.TILE_SIZE, scale, scale, rotation, true);
		
//		batch.draw(GameScreen.texture, getX() - getWidth()/2, getY() - getHeight()/2, 32, 32, 32 /*texX*/, 40 /*texY*/, 32, 32, false, false);
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Actor#act(float)
	 */
	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Actor#setPosition(float, float)
	 */
	@Override
	public void setPosition(float x, float y) {
		
		if (!restrictToRoom || updateRoom((int)(x / (GameWorld.ROOM_SIZE * GameScreen.TILE_SIZE)), (int)(y / (GameWorld.ROOM_SIZE * GameScreen.TILE_SIZE)))) {
			super.setPosition(x, y);
		}
	}
	
	private boolean updateRoom(int x, int y) {
		EntityGroup par = ((EntityGroup)this.getParent());
		Room oldRoom = room;
		room = GameWorld.instance().roomMap.get(x, y);
//		Console.debug("(%d, %d) oldRoom:%s room:%s", x, y, oldRoom, room);
		if (oldRoom != room) {
//			Console.debug("changed rooms to %s [%d, %d]", room, x, y);
			if (room == null) {
				room = oldRoom;
				return false;
			}
			if (oldRoom != null)
				oldRoom.removeEntity(this);
			room.addEntity(this);
		}
		return true;
	}
	
}

