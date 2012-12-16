package com.mutantamoeba.ld25.actors;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class SimpleButton extends Actor {
	boolean mouseOver = false;
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
	
}
