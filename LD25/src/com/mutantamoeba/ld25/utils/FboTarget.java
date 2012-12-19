package com.mutantamoeba.ld25.utils;

import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

public class FboTarget {
	private boolean isEnabled = true;
	private FrameBuffer fbo = null;
	private TextureRegion region = null;
	private int width, height;
	public FboTarget(int width, int height) {
		this.width = width;
		this.height = height;
		fbo = new FrameBuffer(Format.RGBA8888, this.width, this.height, false);
		region = new TextureRegion(fbo.getColorBufferTexture());
		region.flip(false, true);
	}
	public void begin() {
		fbo.begin();		
	}
	public void end() {
		fbo.end();
	}
	public void render(SpriteBatch batch) {
		batch.begin();
		batch.draw(region, 0, 0, this.width, this.height);
		batch.end();
	}
}
