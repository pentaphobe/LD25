package com.mutantamoeba.ld25.actors;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ToolButton extends SimpleButton {
	public static final int NORMAL = 0;
	public static final int HOVER = 1;
	public static final int DOWN = 2;

	TextureRegion background[];
	TextureRegion icon;
	public String toolName;
	public ToolButton(TextureRegion background[], TextureRegion icon, String toolName) {
		super();
		this.toolName = toolName;
		this.setSize(background[0].getRegionWidth(), background[0].getRegionHeight());
		this.background = background;
		this.icon = icon;
	}
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Actor#draw(com.badlogic.gdx.graphics.g2d.SpriteBatch, float)
	 */
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		int state = NORMAL;
		if (mouseOver) {
			state = HOVER;
		}
		batch.draw(background[state], getX(), getY(), getWidth(), getHeight());
		batch.draw(icon, getX(), getY(), getWidth(), getHeight());
		super.draw(batch, parentAlpha);
	}

}
