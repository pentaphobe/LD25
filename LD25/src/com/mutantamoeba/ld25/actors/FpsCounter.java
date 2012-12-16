package com.mutantamoeba.ld25.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mutantamoeba.ld25.engine.Console;
import com.mutantamoeba.ld25.screens.BasicScreen;

public class FpsCounter extends Actor {
	BasicScreen screen;
	public FpsCounter(BasicScreen gameScreen) {
		super();
		this.screen = gameScreen;
		this.setSize(40, 20);
		addListener(new ClickListener() {
			/* (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.utils.ClickListener#clicked(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float)
			 */
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Console.debug("Clicked FpsCounter");
				super.clicked(event, x, y);
			}				
		});
	}
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Actor#draw(com.badlogic.gdx.graphics.g2d.SpriteBatch, float)
	 */
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		BitmapFont fnt = screen.getFont();				
		
		fnt.draw(batch, 
				String.format("FPS:%d", Gdx.graphics.getFramesPerSecond()), getX(), getY());
		
	}


}	