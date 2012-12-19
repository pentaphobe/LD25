package com.mutantamoeba.ld25.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mutantamoeba.ld25.GameWorld;
import com.mutantamoeba.ld25.Room;
import com.mutantamoeba.ld25.engine.Console;
import com.mutantamoeba.ld25.screens.GameScreen;
import com.mutantamoeba.ld25.utils.MathUtils;
import com.mutantamoeba.ld25.utils.RandomNumbers;

public class LaserTurretEntity extends TrapEntity {
	private static final float SPAWN_RANDOM_POS = 0;
	private static final float LASER_DAMAGE = 48f;
	private static final float LASER_LINE_DISTANCE = 24;
	float mouseDistance = 1;
	Vector2 mousePos = new Vector2();
	boolean gotMouse=false;
	float targetRotation = 0;
	public LaserTurretEntity(LaserTurretEntity other) {
		super(other);
		setAlwaysDraw(true);
		deactivate();
	}

	public LaserTurretEntity(Texture tex, int tileIndex) {
		super(tex, tileIndex);
		setAlwaysDraw(true);
		deactivate();
		
	}
	
	public LaserTurretEntity clone() {
		return new LaserTurretEntity(this);
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		float turretLength = 10;

		float xSize = mouseDistance - turretLength;
		float ySize = GameScreen.HTILE_SIZE;
		
		float xOffs = (float) Math.cos(MathUtils.radians(rotation)) * turretLength;
		float yOffs = (float) Math.sin(MathUtils.radians(rotation)) * turretLength;
//		Console.debug("activated:%s, reloaded:%s", isActivated(), isReloaded());
		if (isActivated()) {
//			Console.debug("drawing beam");
			
			batch.draw(regions[0].getTexture(), getX() + GameScreen.HTILE_SIZE + xOffs, getY() + ySize/2f + yOffs, 0, ySize/2f, xSize, ySize, 1, 1, rotation, 64, 160, 32, 32, false, false);
			drawSparks(batch, getX() + mousePos.x, getY() + mousePos.y);
			GameScreen.instance().sounds.trigger("laser", 0.05f);
		}
			
		if (isActivated() && isReloaded()) {
//			batch.draw(regions[currentFrame], getX(), getY(), getOriginX(), getOriginY(), GameScreen.TILE_SIZE, GameScreen.TILE_SIZE, scale, flipX?-1:1 * scale, rotation, true);
			
//			Console.debug("drawing beam");
//			batch.draw(regions[0].getTexture(), getX(), getY(), getOriginX(), getOriginY(), 32, 32, 1, 1, rotation, 128, 96, 32, 32, false, false);
			
//			batch.draw(regions[0], getX(), getY(), getOriginX(), getOriginY(), GameScreen.TILE_SIZE, GameScreen.TILE_SIZE, scale, flipX?-1:1 * scale, rotation + 90, true);

		}
//		batch.draw(regions[currentFrame], getX(), getY(), getOriginX(), getOriginY(), GameScreen.TILE_SIZE, GameScreen.TILE_SIZE, scale, flipX?-1:1 * scale, rotation, true);		
		super.draw(batch, parentAlpha);
	}
	
	private void drawSparks(SpriteBatch batch, float x, float y) {
		// create spark entities (replaced as particles looked weird)
//		ParticleEntity spark = new ParticleEntity(43);
//		float xOffs = (RandomNumbers.nextFloat()-0.5f) * SPAWN_RANDOM_POS + mousePos.x;
//		float yOffs = (RandomNumbers.nextFloat()-0.5f) * SPAWN_RANDOM_POS + mousePos.y;
//		spark.setPosition(this.getX() + xOffs, this.getY() + yOffs);
//		spark.originalRoom = getRoom();		
//		GameScreen.instance().addEntity(spark);
		batch.draw(regions[0].getTexture(), x, y, GameScreen.HTILE_SIZE	, GameScreen.HTILE_SIZE, GameScreen.TILE_SIZE, GameScreen.TILE_SIZE, scale, flipX?-1:1 * scale, RandomNumbers.nextInt(360), 96, 160, 32, 32, false, false);		
	}

	@Override
	public void mouseMoved(float x, float y) {
//		Console.debug("%f,%f", x, y);
		mousePos.set(x-GameScreen.HTILE_SIZE, y - GameScreen.HTILE_SIZE);
		float dir = (float)Math.atan2(mousePos.y, mousePos.x);
		mouseDistance = (float)MathUtils.sqrt(mousePos.x*mousePos.x + mousePos.y*mousePos.y);
		targetRotation = MathUtils.degrees(dir);
		 		
	}

	@Override
	public void act(float delta) {
		// reset laser's position if mouse is away (removed so I can engineer a nicer way)
//		if (timeSinceMouse >) {
//			float x = GameScreen.HTILE_SIZE * GameWorld.ROOM_SIZE;
//			float y = GameScreen.HTILE_SIZE * GameWorld.ROOM_SIZE;
//			float dir = (float)Math.atan2(y, x - GameScreen.HTILE_SIZE);
//			mouseDistance = (float)MathUtils.sqrt(x*x + y*y);
//			rotation += (MathUtils.degrees(dir)-rotation) * .25f;
//		}
		rotation += MathUtils.dirDelta(rotation, targetRotation, 180) * 0.25f;
		super.act(delta);
		
		if (isActivated()) {
			// check intersection with entities
			float startX = getX();
			float startY = getY();
			float endX = startX + mousePos.x;
			float endY = startY + mousePos.y;
			Room room = getRoom();
//			Console.debug("laser line is from %f, %f to %f, %f", startX, startY, endX, endY);
			for (GameEntity ent:room.getEntities()) {
				if (ent instanceof BondEntity) {
					BondEntity bond = (BondEntity)ent;
					if (!bond.isAlive()) continue;
					float bondX = bond.getX();//room.getRelativeX(bond);
					float bondY = bond.getY(); //room.getRelativeY(bond);
					float distanceToLine = MathUtils.distanceToLine(bondX, bondY, startX, startY, endX, endY);
//					Console.debug("distance to %f, %f: %f", bondX, bondY, distanceToLine);
					if (distanceToLine <= LASER_LINE_DISTANCE) {
						bond.hurt(LASER_DAMAGE * delta);
						if (!bond.isAlive()) {
							GameWorld.instance().getScoreKeeper().addScore("laser kills", 1);
						}
					}
				}
			}
		}
	}
	
}
