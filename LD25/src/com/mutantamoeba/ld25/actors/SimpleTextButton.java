package com.mutantamoeba.ld25.actors;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.mutantamoeba.ld25.screens.BasicScreen;

/* a simple text button with no skinning or anything fancy
 * [@todo this should really just be some kind of Label drawable object whicih gets attached to the SimpleButton]
 * [@... do this if you have time]
 */
public class SimpleTextButton extends SimpleButton {
	BasicScreen screen;
	String label;
	BitmapFont font;
	
	public SimpleTextButton(BasicScreen gameScreen, String label) {
		super();
		
		this.screen = gameScreen;		
		this.font = screen.getFont();
		
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
		if (mouseOver) {
			font.setColor(1, 0, 0, 1);
		} else {
			font.setColor(1, 1, 1, 1);
		}
		font.draw(batch, label, getX(), getY() + getHeight());
		
	}


}
