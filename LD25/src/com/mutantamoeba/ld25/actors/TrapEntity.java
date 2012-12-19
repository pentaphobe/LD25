package com.mutantamoeba.ld25.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mutantamoeba.ld25.RoomTemplate;
import com.mutantamoeba.ld25.engine.Console;
import com.mutantamoeba.ld25.utils.MathUtils;

public class TrapEntity extends GameEntity {

	private boolean activated;
	private boolean alwaysDraw = false ;
	
	// how long this trap stays activated
	public float resetTime = 2f;
	public float resetCounter = 0;
	
	// how long before the trap can be reactivated (including resetTime)
	public float reloadTime = 1f;		// so if reloadTime is 2f and resetTime == 1f, then this trap will need (at most) a second between finishing and starting again. 
	public float reloadCounter = 0;
	
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
		if (reloadCounter > 0) {
			reloadCounter -= delta;
		} else {
			reloadCounter = 0;
		}
		if (isActivated() && resetCounter == 0) {
			start(delta);
		}
		
		if (activated) {
			resetCounter += delta;
			if (resetCounter >= resetTime) {
				stop(delta);
				deactivate();
			}
		}
	}
	
	public void stop(float delta) {
		// TODO Auto-generated method stub
		
	}

	public void start(float delta) {
		// TODO Auto-generated method stub
		
	}
	
	public float timeRemaining() {
		return resetTime - resetCounter;
	}

	public void activate() {
//		Console.debug("trap entity:%s's room:%s", this, getRoom());
		if (resetCounter == 0 && isReloaded()) {
			activated = true;
		}
	}
	
	public void deactivate() {
		activated = false;
		resetCounter = 0;
		reloadCounter = reloadTime;
	}
	
	public boolean isActivated() {
		return activated;
	}
	
	public boolean isReloaded() {
		return reloadCounter <= 0;
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		if (activated || alwaysDraw) {
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

	public float getReloadTime() {
		return reloadTime;
	}

	public void setReloadTime(float reloadTime) {
		this.reloadTime = reloadTime;
	}

	public boolean isAlwaysDraw() {
		return alwaysDraw;
	}

	public void setAlwaysDraw(boolean alwaysDraw) {
		this.alwaysDraw = alwaysDraw;
	}

	@Override
	protected boolean updateRoom(int x, int y) {
		return true;
	}


}
