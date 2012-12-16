package com.mutantamoeba.ld25.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class BasicScreen extends AbstractScreen {
	protected Stage stage;
	protected Stage uiStage;
	private BitmapFont font;	
	protected InputMultiplexer inputMultiplexer;
	boolean clearScreen = true;
	
	public BasicScreen(Game game) {
		super(game);
		stage = new Stage( Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
		stage.getSpriteBatch().getProjectionMatrix().scl(1, -1, 1);

		uiStage = new Stage( Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
		uiStage.setViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
		
		inputMultiplexer = new InputMultiplexer();
		Gdx.input.setInputProcessor(inputMultiplexer);
		
		inputMultiplexer.addProcessor(this);		
		inputMultiplexer.addProcessor(0, uiStage);			
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
		if (clearScreen) {
			Gdx.gl.glClearColor(0,0,0,1);
			Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		}
		
		update(delta);
		
		stage.draw();
		uiStage.draw();		
	}
	
	public void update(float delta) {
		stage.act(delta);
		uiStage.act(delta);
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

}
