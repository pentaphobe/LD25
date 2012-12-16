package com.mutantamoeba.ld25.utils;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mutantamoeba.ld25.engine.Console;

public class DebugListener extends InputListener {
	String label;
	public DebugListener(String label) {
		this.label = label;
	}
	DebugListener() {
		this("debugListener");
	}
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.InputListener#handle(com.badlogic.gdx.scenes.scene2d.Event)
	 */
	@Override
	public boolean handle(Event e) {
		Console.debug("%s:%s", this.label, e);
		return super.handle(e);
	}

}
