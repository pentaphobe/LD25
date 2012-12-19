package com.mutantamoeba.ld25.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.mutantamoeba.ld25.screens.BasicScreen;

public class TextBox extends Actor {
	BasicScreen screen;
	String label;
	BitmapFont font;
	
	public TextBox(BasicScreen gameScreen, String label) {
		super();
		
		this.screen = gameScreen;		
		this.font = screen.getFont();
		setTouchable(Touchable.disabled);
		setLabel(label);
	}
	public void setLabel(String label) {
		this.label = label;
		TextBounds bounds = font.getBounds(label);		
		this.setSize(bounds.width, bounds.height);
	}
	
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Actor#draw(com.badlogic.gdx.graphics.g2d.SpriteBatch, float)
	 */
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {		

		font.setScale(1.5f);
		font.drawMultiLine(batch, label, getX(), getY() + getHeight());
		font.setScale(1);
	}


}
