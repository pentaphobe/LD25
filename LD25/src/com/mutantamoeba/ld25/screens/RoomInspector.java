package com.mutantamoeba.ld25.screens;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.mutantamoeba.ld25.Room;

public class RoomInspector extends Group {
	Room room;
	NinePatch ninePatch;
	BitmapFont font;
	RoomInspector(NinePatch ninePatch, BitmapFont bitmapFont) {
		this.ninePatch = ninePatch;
		this.font = bitmapFont;
		setVisible(false);
		addListener(new DragListener() {
			float xOffset=0, yOffset=0;
			/* (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.utils.DragListener#drag(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int)
			 */
			@Override
			public void drag(InputEvent event, float x, float y, int pointer) {
				setPosition(x - xOffset, y - yOffset);
				super.drag(event, x, y, pointer);				
			}

			/* (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.utils.DragListener#dragStart(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int)
			 */
			@Override
			public void dragStart(InputEvent event, float x, float y,
					int pointer) {
				xOffset = x - getX();
				yOffset = y - getY();
				super.dragStart(event, x, y, pointer);
			}

			/* (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.utils.DragListener#dragStop(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int)
			 */
			@Override
			public void dragStop(InputEvent event, float x, float y, int pointer) {
				// TODO Auto-generated method stub
				super.dragStop(event, x, y, pointer);
			}
			
		});
	}
	/**
	 * @return the room
	 */
	public Room getRoom() {
		return room;
	}

	/**
	 * @param room the room to set
	 */
	public void setRoom(Room room) {
		this.room = room;
	}
	
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Group#draw(com.badlogic.gdx.graphics.g2d.SpriteBatch, float)
	 */
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		if (isVisible() && room != null) {
			
			this.ninePatch.draw(batch, getX(), getY(), getWidth(), getHeight());
			String infoString = room.infoString();
			float height = font.getMultiLineBounds(infoString).height;
			font.drawMultiLine(batch, infoString, getX() + 30, getY() + getHeight() - 25);
		}
		super.draw(batch, parentAlpha);
	}
	
}
