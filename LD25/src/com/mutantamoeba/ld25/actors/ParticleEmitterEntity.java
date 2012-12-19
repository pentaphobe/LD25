package com.mutantamoeba.ld25.actors;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mutantamoeba.ld25.GameWorld;
import com.mutantamoeba.ld25.screens.GameScreen;
import com.mutantamoeba.ld25.utils.RandomNumbers;

public class ParticleEmitterEntity extends GameEntity {
	private static final float SPAWN_RANDOM_POS = GameScreen.HTILE_SIZE * GameWorld.ROOM_SIZE;
	private static final float DEFAULT_ACTIVE_TIME = 4f;
	float activeTime = DEFAULT_ACTIVE_TIME;
	float activeCounter = 0;
	int particleTile;
	public ParticleEmitterEntity(int particleTileIndex) {
		super(GameScreen.instance().texture, particleTileIndex);
		particleTile = particleTileIndex;
	}	
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		// no drawing for this entity (fix this later)
		// [@refactor base entity should have capacity for a non-renderable]
//		super.draw(batch, parentAlpha);
	}

	public void spawnParticles() {
		// create particle entities
		ParticleEntity particle = new ParticleEntity(particleTile);
		float xOffs = (RandomNumbers.nextFloat()-0.5f) * SPAWN_RANDOM_POS + GameScreen.TILE_SIZE / 2f;
		float yOffs = (RandomNumbers.nextFloat()-0.5f) * SPAWN_RANDOM_POS + GameScreen.TILE_SIZE / 2f;
		particle.setPosition(this.getX() + xOffs, this.getY() + yOffs);
		particle.originalRoom = getRoom();		
		particle.setColor(getColor());
		GameScreen.instance().addEntity(particle);		
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		if (activeCounter < activeTime) {
			spawnParticles();
			activeCounter += delta;
		}
	}
}
