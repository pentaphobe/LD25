package com.mutantamoeba.ld25.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mutantamoeba.ld25.screens.GameScreen;
import com.mutantamoeba.ld25.utils.MathUtils;

public class TrapDoorEntity extends TrapEntity {

	public TrapDoorEntity(TrapDoorEntity other) {
		super(other);
		deactivate();
	}

	public TrapDoorEntity(Texture tex, int tileIndex) {
		super(tex, tileIndex);
	}
	
	public TrapDoorEntity clone() {
		return new TrapDoorEntity(this);
	}

	@Override
	public void activate() {
		super.activate();
	}

	@Override
	public void deactivate() {
		super.deactivate();
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}

	@Override
	public void act(float delta) {		
		super.act(delta);
		if (isActivated()) {			
			for (GameEntity ent:getRoom().getEntities()) {
				if (ent instanceof BondEntity) {
					BondEntity bond = (BondEntity)ent;
					float xd = bond.getX() - this.getX();
					float yd = bond.getY() - this.getY();
					float dist = (float)MathUtils.sqrt(xd*xd + yd*yd);
					if (dist < GameScreen.TILE_SIZE) {
						bond.destroy();
					}
				}
			}
		}		
	}
	
	
}
