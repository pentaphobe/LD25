package com.mutantamoeba.ld25.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mutantamoeba.ld25.GameWorld;
import com.mutantamoeba.ld25.screens.GameScreen;
import com.mutantamoeba.ld25.utils.RandomNumbers;

public class GasVentEntity extends TrapEntity {

	private static final float SPAWN_RANDOM_POS = 4f;
	private static final float POISON_DAMAGE = 5.5f;

	public GasVentEntity(Texture tex, int tileIndex) {
		super(tex, tileIndex);
		setResetTime(2f);
		deactivate();
	}

	public GasVentEntity(GasVentEntity other) {
		super(other);
		setResetTime(other.getResetTime());
	}
	
	

	public GasVentEntity clone() {
		return new GasVentEntity(this);
	}

	@Override
	public void act(float delta) {		
		super.act(delta);
		if (isActivated()) {
			if (timeRemaining() > 0.5f) {
				spawnGas();
			}
//			Console.debug("Hurting %d bonds", getRoom().getEntities().size);
			for (GameEntity ent:getRoom().getEntities()) {
				if (ent instanceof BondEntity) {
					BondEntity bond = (BondEntity)ent;
					bond.hurt(POISON_DAMAGE * delta);
				}
			}
		}
	}
	
	public void spawnGas() {
		// create gas entities
		GasEntity gas = new GasEntity();
		float xOffs = (RandomNumbers.nextFloat()-0.5f) * SPAWN_RANDOM_POS + GameScreen.TILE_SIZE / 2f;
		float yOffs = (RandomNumbers.nextFloat()-0.5f) * SPAWN_RANDOM_POS + GameScreen.TILE_SIZE / 2f;
		gas.setPosition(this.getX() + xOffs, this.getY() + yOffs);
		gas.originalRoom = getRoom();		
		GameScreen.instance().addEntity(gas);		
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		// no drawing for this entity (fix this later)
		// [@refactor base entity should have capacity for a non-renderable]
//		super.draw(batch, parentAlpha);
	}

	@Override
	public void start(float delta) {
		GameScreen.instance().sounds.trigger("gas", .03f);
	}
	
	@Override
	public void activate() {
		super.activate();
	}	
}
