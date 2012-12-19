package com.mutantamoeba.ld25.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mutantamoeba.ld25.LD25;
import com.mutantamoeba.ld25.actors.SimpleTextButton;
import com.mutantamoeba.ld25.engine.Console;

public class MainMenuScreen extends BasicScreen {
	Music music;
	public MainMenuScreen(Game game) {
		super(game);
		
		music = Gdx.audio.newMusic(Gdx.files.local("sounds/intro_music.wav"));
		music.setLooping(true);
		music.play();
		
		SimpleTextButton butt = new SimpleTextButton(this, "> press anything to start <");
		stage.addActor(butt);
		butt.setPosition((Gdx.graphics.getWidth()-butt.getWidth()) / 2, (Gdx.graphics.getHeight()-butt.getHeight()) / 2);
		
		butt.addListener(new ClickListener() {

			/* (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.utils.ClickListener#clicked(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float)
			 */
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Console.debug("%s clicked at %f, %f", this, x, y);
				super.clicked(event, x, y);
				startGame();
			}
			
			
		});
	}
	
	/* (non-Javadoc)
	 * @see com.mutantamoeba.ld25.engine.BasicInputProcessor#keyTyped(char)
	 */
	@Override
	public boolean keyTyped(char character) {
		startGame();
		
		return super.keyTyped(character);
	}
	
	public void startGame() {
		music.setLooping(false);
		music.setVolume(0f);
		music.stop();

		LD25.instance().setScreen(new GameScreen(LD25.instance()));
		
	}

	@Override
	public void dispose() {
		music.dispose();
		super.dispose();
	}
}
