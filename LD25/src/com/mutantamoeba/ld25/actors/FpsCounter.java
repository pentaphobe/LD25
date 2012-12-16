package com.mutantamoeba.ld25.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mutantamoeba.ld25.engine.Console;
import com.mutantamoeba.ld25.screens.BasicScreen;
import com.mutantamoeba.ld25.utils.DebugListener;

public class FpsCounter extends SimpleTextButton {
	BasicScreen screen;
	public FpsCounter(BasicScreen gameScreen) {
		super(gameScreen, "");
		addListener(new ClickListener() {
			/* (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.utils.ClickListener#clicked(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float)
			 */
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Console.debug("Clicked FpsCounter");
				event.cancel();
				super.clicked(event, x, y);
			}				
		});
		addListener(new DebugListener("fpsCounter"));
	}
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Actor#act(float)
	 */
	@Override
	public void act(float delta) {
		setLabel(String.format("FPS:%d", Gdx.graphics.getFramesPerSecond()));
		super.act(delta);
	}


}	