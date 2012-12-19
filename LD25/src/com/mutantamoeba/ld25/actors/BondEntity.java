package com.mutantamoeba.ld25.actors;

import java.util.LinkedList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mutantamoeba.ld25.GameWorld;
import com.mutantamoeba.ld25.Room;
import com.mutantamoeba.ld25.engine.Console;
import com.mutantamoeba.ld25.screens.GameScreen;
import com.mutantamoeba.ld25.utils.MathUtils;
import com.mutantamoeba.ld25.utils.RandomNumbers;

public class BondEntity extends GameEntity {

	private static final float INITIAL_HEALTH = 20;
	private static final float MAX_WAYPOINT_PROXIMITY = 2f;
	private static final float MIN_WAYPOINT_PROXIMITY = .5f;
	private static final int MEMORY_LENGTH = 12;
	private static final float DESTRUCTION_HEALTH = -10f;
	private static final float ATTACK_STRENGTH = 4;
	public static float speed = 60f;
	private float health;
	public boolean attackingLair = false;
	
	// switching to standard linked list since PooledLinkedList doesn't keep track of its size (!?)
//	PooledLinkedList<Room> previousRooms = new PooledLinkedList<Room>(MEMORY_LENGTH);
	private LinkedList<Room> previousRooms = new LinkedList<Room>();

	Vector2 waypoint = null;
	float desiredProximity = getNewProximity();
	public BondEntity(BondEntity other) {
		super(other);	
		health = other.health;
	}

	public BondEntity(Texture tex, int tileIndex) {
		super(tex, tileIndex);
		health = INITIAL_HEALTH;
	}

	public BondEntity(TextureRegion region) {
		super(region);
		health = INITIAL_HEALTH;
	}

	public BondEntity(Texture texture, int ...indices) {
		super(texture, indices);
		health = INITIAL_HEALTH;
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		if (isAlive()) {
			if (attackingLair) {
				attackDestructRoom(delta);
				return;
			}
			if (waypoint == null) {
				waypoint = new Vector2();
				reachedWaypoint();
				return;
			}
//			float speed = 6f;
//			setPosition(getX() + speed * (RandomNumbers.nextFloat() - 0.5f), getY() + speed * (RandomNumbers.nextFloat() - 0.5f));
			Vector2 move = new Vector2(waypoint).sub(getX(), getY());
			float roomMidpoint = (GameScreen.HTILE_SIZE * GameWorld.ROOM_SIZE);
			float dist = (float)MathUtils.sqrt(move.x*move.x + move.y*move.y);
			
			if (dist > desiredProximity) {
				move.nor();
				flipX = move.x < 0;
				move.mul(speed);
				float xPos = getX() + move.x * delta;
				float yPos = getY() + move.y * delta;
				
				setPosition(xPos, yPos);
			} else {
				reachedWaypoint();
			}
			
		} else {
			rotation = 0;
			health -= delta;
			frameRate = 0;
			if (health < DESTRUCTION_HEALTH) {
				destroy();
			}
		}
	}
	
	private boolean isInDestructRoom() {
		return getRoom() != null && getRoom() == GameScreen.instance().selfDestructRoom;
	}

	private void attackDestructRoom(float delta) {
		if (!isInDestructRoom()) {
			attackingLair = false;
			return;
		}
		GameWorld.instance().attackDestructRoom(ATTACK_STRENGTH, delta);
	}

	private void reachedWaypoint() {
		Room targetRoom = null;
		if (getRoom() == null) {
			targetRoom = GameWorld.instance().roomMap.getRoomAt(this);	
			if (targetRoom != null) {
				targetRoom.addEntity(this);
				setRoom(targetRoom);				
			} else {
				Console.debug("### BAD!  a bond is somewhere unreachable - suiciding him before he causes any mischief..");
				destroy();
				return;
			}			
		}
		if (isInDestructRoom()) {
			stop();
			attackingLair = true;
			return;
		}
		attackingLair = false;
		
		
		Array<Room> exits = getRoom().getExits();
		if (exits.size == 0) {
			Console.debug("### BAD!  disconnected room - trapped Bond");
			waypoint.set(getX(), getY());
			return;
		}
//		Console.debug("%d total exits available", exits.size);
		
		// straight randomness
//		int randomRoomIndex = RandomNumbers.nextInt(exits.size);
		
		// picking weighted by memory
		int weights[] = new int[exits.size];

//		Console.debug("### CHOOSING ROOM from %d exits", exits.size);
//		for (Room rememberedRoom:previousRooms) {
//			Console.debug(" -- previos: %d, %d", rememberedRoom.getMapX(), rememberedRoom.getMapY());
//		}
		for (int i=0;i<weights.length;i++) {
			Room exit = exits.get(i);
			if (exit == null) {
				weights[i] = 0;
			} else if (previousRooms.contains(exit)) {
				weights[i] = 1;
			} else {
				weights[i] = 1000;
			}
//			Console.debug(" %d, %d", i, weights[i]);
		}
		int randomRoomIndex = RandomNumbers.weightedRandom(weights);
//		Console.debug("   Chose room index %d, weight:%d", randomRoomIndex, weights[randomRoomIndex]);
		
		targetRoom = exits.get(randomRoomIndex);
		
		waypoint.set(targetRoom.getWorldX(), targetRoom.getWorldY());
		float halfRoom = GameScreen.TILE_SIZE * (GameWorld.ROOM_SIZE * 0.5f);
		waypoint.add(halfRoom, halfRoom);
		desiredProximity = getNewProximity();
		waypoint.add(RandomNumbers.nextFloat()-0.5f * desiredProximity, RandomNumbers.nextFloat()-0.5f * desiredProximity);
//		Console.debug("bond at %f, %f 's waypoint is now: %s (origin room: %s)", getX(), getY(), waypoint, getRoom());
		
	}

	public boolean isAlive() {		
		return health > 0;
	}

	@Override
	public GameEntity clone() {
		return new BondEntity(this);
	}

	public void hurt(float damage) {
		if (health > 0) {
			health -= damage;
			if (health <= 0) {
				die();
			}
		}
	}

	private void die() {
		stop();
	}

	public void setWaypoint(float x, float y) {
		waypoint.set(x, y);
	}

	private float getNewProximity() {
		return GameScreen.TILE_SIZE * MathUtils.map(RandomNumbers.nextFloat(), 0, 1, MIN_WAYPOINT_PROXIMITY, MAX_WAYPOINT_PROXIMITY);	
	}

	@Override
	public void setRoom(Room room) {		
		super.setRoom(room);
		if (previousRooms.contains(room)) {
			return;
		}
		previousRooms.addLast(room);
		while (previousRooms.size() > MEMORY_LENGTH) {
			previousRooms.removeFirst();
		}
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		if (health < 0) {
			float norm = 1f - 1f / (health - DESTRUCTION_HEALTH );
			if (norm < 0) norm = 0f;
			setColor(1, 0, 0, norm);
			
		} else if (health < INITIAL_HEALTH) {
			float norm = health / INITIAL_HEALTH;
			setColor(1, norm, norm, 1);
		}
		super.draw(batch, parentAlpha);
	}

	/**
	 * @param previousRooms the previousRooms to set
	 */
	public void setPreviousRooms(LinkedList<Room> previousRooms) {
		this.previousRooms = previousRooms;
	}

	/**
	 * @return the previousRooms
	 */
	public LinkedList<Room> getPreviousRooms() {
		return previousRooms;
	}
	
}
