package com.mutantamoeba.ld25.actors;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mutantamoeba.ld25.engine.Console;

public class SelectionBox extends Actor {
	private NinePatch selectionBox;
	public SelectionBox(NinePatch patch) {
		selectionBox = patch;
	}
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {		
		super.draw(batch, parentAlpha);
//		Console.debug("%s drawing selection at: %f, %f - %fx%f", (Object)this, getX(), getY(), getWidth(), getHeight());
		selectionBox.draw(batch, getX(), getY(), getWidth(), getHeight());
	}
}
