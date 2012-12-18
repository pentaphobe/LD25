package com.mutantamoeba.ld25.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.mutantamoeba.ld25.GameWorld;
import com.mutantamoeba.ld25.Room;
import com.mutantamoeba.ld25.engine.Console;
import com.mutantamoeba.ld25.screens.GameScreen;

public class GameEntity extends Group {	
	TextureRegion regions[];
	int currentFrame = 0;
	float frameRate = .15f;
	float frameRateCounter = 0;
	boolean playing = false;
	
	private Room room;
	boolean restrictToRoom = true;
	float scale=1f, rotation=90f;
	boolean flipX = false;
	public GameEntity(Texture tex, int tileIndex) {
		super();
		int texTileWidth = tex.getWidth() / GameScreen.TILE_SIZE;
		int tileX = tileIndex % texTileWidth;
		int tileY = tileIndex / texTileWidth;
		this.setOrigin(GameScreen.TILE_SIZE / 2f, GameScreen.TILE_SIZE / 2f);
		this.regions = new TextureRegion[] {
				new TextureRegion(tex, tileX * GameScreen.TILE_SIZE, tileY * GameScreen.TILE_SIZE, GameScreen.TILE_SIZE, GameScreen.TILE_SIZE)		
		};
	}
	public GameEntity(Texture tex, int tileIndices[]) {
		super();
		int texTileWidth = tex.getWidth() / GameScreen.TILE_SIZE;
		this.setOrigin(GameScreen.TILE_SIZE / 2f, GameScreen.TILE_SIZE / 2f);
		this.regions = new TextureRegion[tileIndices.length];
		for (int i=0;i<tileIndices.length;i++) {
			int tileIndex = tileIndices[i];
			int tileX = tileIndex % texTileWidth;
			int tileY = tileIndex / texTileWidth;

			regions[i] = new TextureRegion(tex, tileX * GameScreen.TILE_SIZE, tileY * GameScreen.TILE_SIZE, GameScreen.TILE_SIZE, GameScreen.TILE_SIZE);		
		};
		play();
	}
	
	public GameEntity(TextureRegion region) {
		super();
		this.regions = new TextureRegion[] {
				region
		};
		this.setOrigin(GameScreen.TILE_SIZE / 2f, GameScreen.TILE_SIZE / 2f);
	}
	public GameEntity(TextureRegion regions[]) {
		super();
		this.regions = regions;
		this.setOrigin(GameScreen.TILE_SIZE / 2f, GameScreen.TILE_SIZE / 2f);
		play();
	}
	
	public GameEntity(GameEntity other) {
		super();
		this.regions = other.regions;
		this.setOrigin(other.getOriginX(), other.getOriginY());
	}

	public GameEntity clone() {
		return new GameEntity(this);
	}
	public void destroy() {
		GameScreen.instance().removeEntity(this);
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
		batch.draw(regions[currentFrame], getX(), getY(), getOriginX(), getOriginY(), GameScreen.TILE_SIZE, GameScreen.TILE_SIZE, scale, flipX?-1:1 * scale, rotation, true);
		
//		batch.draw(GameScreen.texture, getX() - getWidth()/2, getY() - getHeight()/2, 32, 32, 32 /*texX*/, 40 /*texY*/, 32, 32, false, false);
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Actor#act(float)
	 */
	@Override
	public void act(float delta) {
		super.act(delta);
		if (playing && regions.length > 1) {
			frameRateCounter += delta;
			if (frameRateCounter > frameRate) {
				frameRateCounter -= frameRate;
				currentFrame = (currentFrame + 1) % regions.length;
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Actor#setPosition(float, float)
	 */
	@Override
	public void setPosition(float x, float y) {
		Vector2 oldPos = new Vector2(getX(), getY());
		super.setPosition(x, y);
		
		if (!updateRoom((int)(x / (GameWorld.ROOM_SIZE * GameScreen.TILE_SIZE)), (int)(y / (GameWorld.ROOM_SIZE * GameScreen.TILE_SIZE)))) {
			if (restrictToRoom) {
				super.setPosition(oldPos.x, oldPos.y);
			}
		}
	}
	
	private boolean updateRoom(int x, int y) {
		EntityGroup par = ((EntityGroup)this.getParent());
		Room oldRoom = getRoom();
		setRoom(GameWorld.instance().roomMap.get(x, y));
//		Console.debug("(%d, %d) oldRoom:%s room:%s", x, y, oldRoom, room);
		if (oldRoom != room) {
//			Console.debug("changed rooms to %s [%d, %d]", room, x, y);
			if (room == null || !room.isInside(this)) {
//				Console.debug("room:%s inside? %s", room, room == null ? "" : (room.isInside(this) ? "true" : "false"));
				setRoom(oldRoom);
				return false;
			}
			if (oldRoom != null)
				oldRoom.removeEntity(this);
			if (room != null) {
				getRoom().addEntity(this);
			}
		}
		return true;
	}
	/**
	 * @param room the room to set
	 */
	public void setRoom(Room room) {
		this.room = room;
	}
	/**
	 * @return the room
	 */
	public Room getRoom() {
		return room;
	}
	
	public void stop() {
		playing = false;
	}
	public void play() {
		playing = true;
	}
}

