package com.mutantamoeba.ld25.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mutantamoeba.ld25.engine.Console;

public abstract class BasicScreen extends AbstractScreen {
	protected Stage stage;
	protected Stage uiStage;
	private BitmapFont font;	
	protected InputMultiplexer inputMultiplexer;
	boolean clearScreen = true;
	private boolean paused;
	
	public BasicScreen(Game game) {
		super(game);
		stage = new Stage( Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
		stage.getSpriteBatch().getProjectionMatrix().scl(1, -1, 1);

		setUiStage(new Stage( Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true));
		
		
		inputMultiplexer = new InputMultiplexer();
		Gdx.input.setInputProcessor(inputMultiplexer);
		
		inputMultiplexer.addProcessor(this);		
		inputMultiplexer.addProcessor(0, getUiStage());			
		inputMultiplexer.addProcessor(stage);
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
		update(delta);

		preGameRender(stage.getSpriteBatch());

		if (clearScreen) {
			Gdx.gl.glClearColor(0,0,0,1);
			Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		}
		
		
		stage.draw();
		postGameRender(stage.getSpriteBatch());
		preUiRender(uiStage.getSpriteBatch());
		getUiStage().draw();
		postUiRender(uiStage.getSpriteBatch());
	}
	
	private void postUiRender(SpriteBatch spriteBatch) {
		// TODO Auto-generated method stub
		
	}


	public void preUiRender(SpriteBatch batch) {
		// TODO Auto-generated method stub
		
	}


	public void postGameRender(SpriteBatch spriteBatch) {
		// TODO Auto-generated method stub
		
	}


	public void postUiRender() {
		// TODO Auto-generated method stub
		
	}


	public void preGameRender(SpriteBatch spriteBatch) {
		// TODO Auto-generated method stub
		
	}


	public void update(float delta) {
		if (!isPaused()) {
			stage.act(delta);
			getUiStage().act(delta);
		}
	}	

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, true);
		
		// updates the viewport preventing GUI scaling, but also requires adjustment of all widgets
//		uiStage.setViewport(width, height, true);
	}

	@Override
	public void dispose() {
		font.dispose();
		stage.dispose();
		getUiStage().dispose();
	}

	public void toggleFullscreen() {
		if (Gdx.graphics.isFullscreen()) {
			Console.debug("disabling fullscreen");
			Gdx.graphics.setDisplayMode(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		} else {
			Console.debug("enabling fullscreen");
			Gdx.graphics.setDisplayMode(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
		}
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}	
	
	@Override
	public void show() {
		// TODO Auto-generated method stub		
		Console.debug("Show");
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		Console.debug("Hide");
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		Console.debug("Pause");
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		Console.debug("Resume");
	}


	/**
	 * @return the clearScreen
	 */
	public boolean isClearScreen() {
		return clearScreen;
	}


	/**
	 * @param clearScreen the clearScreen to set
	 */
	public void setClearScreen(boolean clearScreen) {
		this.clearScreen = clearScreen;
	}


	/**
	 * @param paused the paused to set
	 */
	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	
	public void togglePaused() {
		this.paused = !this.paused;
	}
	
	/**
	 * @return the paused
	 */
	public boolean isPaused() {
		return paused;
	}


	/**
	 * @param uiStage the uiStage to set
	 */
	public void setUiStage(Stage uiStage) {
		this.uiStage = uiStage;
	}


	/**
	 * @return the uiStage
	 */
	public Stage getUiStage() {
		return uiStage;
	}

}
