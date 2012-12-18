package com.mutantamoeba.ld25.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mutantamoeba.ld25.RoomTemplate;

public class TrapEntity extends GameEntity {

	private boolean activated;
	public float resetTime = 1f;
	public float resetCounter = 0;
	RoomTemplate.WallType location;
	
	public TrapEntity(TrapEntity other) {
		super(other);
	}

	public TrapEntity(Texture tex, int tileIndex) {
		super(tex, tileIndex);
	}

	public TrapEntity(TextureRegion region) {
		super(region);
	}
	
	public TrapEntity clone() {
		return new TrapEntity(this);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		if (activated) {
			resetCounter += delta;
			if (resetCounter >= resetTime) {
				deactivate();
			}
		}
	}
	
	public void activate() {
		if (resetCounter == 0) {
			activated = true;
		}
	}
	
	public void deactivate() {
		activated = false;
		resetCounter = 0;
	}
	
	public boolean isActivated() {
		return activated;
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		if (activated) {
			super.draw(batch, parentAlpha);
		}
	}

	public void mouseMoved(float x, float y) {
		
	}

	public float getResetTime() {
		return resetTime;
	}

	public void setResetTime(float resetTime) {
		this.resetTime = resetTime;
	}

	public RoomTemplate.WallType getLocation() {
		return location;
	}

	public void setLocation(RoomTemplate.WallType location) {
		this.location = location;
	}
}
