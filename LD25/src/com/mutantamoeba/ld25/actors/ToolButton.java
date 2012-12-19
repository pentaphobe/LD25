package com.mutantamoeba.ld25.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ToolButton extends SimpleButton {
	public static final int NORMAL = 0;
	public static final int HOVER = 1;
	public static final int DOWN = 2;

	TextureRegion background[];
	TextureRegion icon;
	public String toolName;
	boolean enabled;
	private char hotKey;
	public ToolButton(TextureRegion background[], TextureRegion icon, String toolName) {
		super();
		this.toolName = toolName;
		this.setSize(background[0].getRegionWidth(), background[0].getRegionHeight());
		this.background = background;
		this.icon = icon;
		this.enabled = true;
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
		Color batchColor = batch.getColor();
		if (!isEnabled()) {
			batch.setColor(.5f, .5f, .5f, 0.25f);
		}
		batch.draw(background[state], getX(), getY(), getWidth(), getHeight());
		batch.draw(icon, getX(), getY(), getWidth(), getHeight());
		if (!isEnabled()) {
			batch.setColor(batchColor);
		}
		super.draw(batch, parentAlpha);
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public String getToolName() {
		return toolName;
	}
	public void setToolName(String toolName) {
		this.toolName = toolName;
	}
	public void setHotkey(char key) {
		this.hotKey = key;
	}
	public char getHotKey() {
		return this.hotKey;
	}
	@Override
	public String getToolTip() { 
		return super.getToolTip() + "[" + this.hotKey + "]";
	}

}
