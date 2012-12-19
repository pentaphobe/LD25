package com.mutantamoeba.ld25.actors;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;
import com.mutantamoeba.ld25.Room;
import com.mutantamoeba.ld25.screens.GameScreen;

public class DestructButtonEntity extends GameEntity {
	private static final float ALARM_VOLUME_ATTACKING = 0.8f;
	private static final float ALARM_VOLUME_PROXIMITY = 0.5f;
	public DestructButtonEntity() {
		super(GameScreen.instance().texture, new int [] {
			//52
			52
		});
		setOrigin(16,0);		
	}
	float alarmFrequency = 2f;
	float alarmCounter = 0;
	@Override
	protected boolean updateRoom(int x, int y) {
		return true;
	}
	@Override
	public void act(float delta) {		
		alarmCounter -= delta;
		super.act(delta);
		Room room = getRoom();
		if (room == null) {
			return;
		}
		if (room.hasBonds()) {
			startAlarm(ALARM_VOLUME_ATTACKING);
			return;
		}
		Array<Room> others = room.getExits();
		for (Room other:others) {
			if (other.hasBonds()) {
				startAlarm();
				return;
			}
		}
		stopAlarm();
	}
	private void stopAlarm() {
		
	}
	private void startAlarm() {
		startAlarm(ALARM_VOLUME_PROXIMITY);
	}
	private void startAlarm(float volume) {
		if (alarmCounter <= 0) {
			GameScreen.instance().sounds.trigger("alarm", volume);
			alarmCounter = alarmFrequency;
		}
	}
	
}
