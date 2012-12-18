package com.mutantamoeba.ld25.actors;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mutantamoeba.ld25.GameWorld;
import com.mutantamoeba.ld25.screens.GameScreen;

public class SimpleButton extends Actor {
	boolean mouseOver = false;
	String toolTip = "";
	SimpleButton() {
		addListener(new InputListener() {

			/* (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.InputListener#enter(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, com.badlogic.gdx.scenes.scene2d.Actor)
			 */
			@Override
			public void enter(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				mouseOver = true;
				super.enter(event, x, y, pointer, fromActor);
			}

			/* (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.InputListener#exit(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, com.badlogic.gdx.scenes.scene2d.Actor)
			 */
			@Override
			public void exit(InputEvent event, float x, float y, int pointer,
					Actor toActor) {
				mouseOver = false;
				super.exit(event, x, y, pointer, toActor);
			}
			
		});
	}
	public String getToolTip() {
		return toolTip;
	}
	public void setToolTip(String toolTip) {
		this.toolTip = toolTip;
	}
	
}
