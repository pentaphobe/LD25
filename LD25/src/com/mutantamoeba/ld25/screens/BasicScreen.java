package com.mutantamoeba.ld25.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class BasicScreen extends AbstractScreen {
	protected Stage stage;
	private BitmapFont font;	
	
	public BasicScreen(Game game) {
		super(game);
		stage = new Stage( Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
		Gdx.input.setInputProcessor(this);
		
	}

	
	public BitmapFont getFont() {
		if (font == null) {
			font = new BitmapFont(Gdx.files.internal("skin/default.fnt"), false);
		}
		return font;
	}
	
	public Stage getStage() {
		return stage;
	}
	
	@Override
	public void render(float delta) {		
		stage.act(delta);
		
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		stage.draw();

	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, true);			
	}

	@Override
	public void dispose() {
		font.dispose();
		stage.dispose();
	}

	public void toggleFullscreen() {
		if (Gdx.graphics.isFullscreen()) {
			Gdx.graphics.setDisplayMode(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
		} else {
			Gdx.graphics.setDisplayMode(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		}
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}	
	
	@Override
	public void show() {
		// TODO Auto-generated method stub		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}


	/* (non-Javadoc)
	 * @see com.mutantamoeba.ld25.engine.BasicInputProcessor#keyDown(int)
	 */
	@Override
	public boolean keyDown(int keycode) {
		return stage.keyDown(keycode);
	}


	/* (non-Javadoc)
	 * @see com.mutantamoeba.ld25.engine.BasicInputProcessor#keyUp(int)
	 */
	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Input.Keys.F11) {
			toggleFullscreen();
		}
		return stage.keyUp(keycode);
	}


	/* (non-Javadoc)
	 * @see com.mutantamoeba.ld25.engine.BasicInputProcessor#keyTyped(char)
	 */
	@Override
	public boolean keyTyped(char character) {
		return stage.keyTyped(character);
	}


	/* (non-Javadoc)
	 * @see com.mutantamoeba.ld25.engine.BasicInputProcessor#touchDown(int, int, int, int)
	 */
	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		return stage.touchDown(x, y, pointer, button);
	}


	/* (non-Javadoc)
	 * @see com.mutantamoeba.ld25.engine.BasicInputProcessor#touchUp(int, int, int, int)
	 */
	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		return stage.touchUp(x, y, pointer, button);
	}


	/* (non-Javadoc)
	 * @see com.mutantamoeba.ld25.engine.BasicInputProcessor#touchDragged(int, int, int)
	 */
	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		return stage.touchDragged(x, y, pointer);
	}


	/* (non-Javadoc)
	 * @see com.mutantamoeba.ld25.engine.BasicInputProcessor#mouseMoved(int, int)
	 */
	@Override
	public boolean mouseMoved(int x, int y) {
		return stage.mouseMoved(x, y);
	}


	/* (non-Javadoc)
	 * @see com.mutantamoeba.ld25.engine.BasicInputProcessor#scrolled(int)
	 */
	@Override
	public boolean scrolled(int amount) {
		return stage.scrolled(amount);
	}


}
