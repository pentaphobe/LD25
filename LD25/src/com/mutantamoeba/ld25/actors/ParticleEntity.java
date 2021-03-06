package com.mutantamoeba.ld25.actors;

import com.badlogic.gdx.graphics.Color;
import com.mutantamoeba.ld25.Room;
import com.mutantamoeba.ld25.screens.GameScreen;
import com.mutantamoeba.ld25.utils.MathUtils;
import com.mutantamoeba.ld25.utils.RandomNumbers;

public class ParticleEntity extends GameEntity {
	private static final float DISPERSION_SPEED = 2f;
	private static final float UP_SPEED = 0.5f;
	private static final float SCALE_SPEED = .75f;
	private static final float ROTATION_SPEED = 90;
	private static final double ANGULAR_SPEED = .25f;
	
	public float maxAge = 2f;
	public float ageCounter = 0;
	public float direction;
	public float rotationDir;
	public Room originalRoom;
	public ParticleEntity(int particleTileIndex) {
		super(GameScreen.instance().texture, particleTileIndex);		
		this.setColor(1,1,1,0.25f);
		scale = .25f;
		rotation = RandomNumbers.nextInt(360);
		direction = RandomNumbers.nextInt(360);
		rotationDir = RandomNumbers.nextFloat() * 2f - 1f;
		restrictToRoom = false;
		setOrigin(0, 0);

	}
	@Override
	public void act(float delta) {		
		super.act(delta);
		ageCounter += delta;
		Color myColor = getColor();
		if (ageCounter > maxAge / 2f) {
			setColor(myColor.r, myColor.g, myColor.b, MathUtils.map(ageCounter, maxAge/2f, maxAge, 1, 0));
		} /*else {
			setColor(myColor.r, myColor.g, myColor.b, myColor.a);
		}*/
		if (ageCounter > maxAge) {
//			Console.debug("Destroying");
			setRoom(originalRoom);
			destroy();
		}
		scale += SCALE_SPEED * delta;
		rotation += ROTATION_SPEED * rotationDir * delta;
		float moveX = (RandomNumbers.nextFloat() - 0.5f) * DISPERSION_SPEED;
		float moveY = (RandomNumbers.nextFloat()) * UP_SPEED;
		moveX += Math.cos(MathUtils.radians(direction)) * ANGULAR_SPEED;
		moveY += Math.sin(MathUtils.radians(direction)) * ANGULAR_SPEED;
		direction += RandomNumbers.nextFloat()-0.5f;
		setX(getX() + moveX);
		setY(getY() + moveY);
//		setPosition(getX() + moveX, getY() + moveY);
	}
	@Override
	public void destroy() {
		GameScreen.instance().removeEntity(this);
		if (originalRoom != null) {
			originalRoom.removeEntity(this);
		}
		if (getRoom() != null) {
			getRoom().removeEntity(this);
		}
	}
}
