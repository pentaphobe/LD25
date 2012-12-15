package com.mutantamoeba.ld25.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mutantamoeba.ld25.actors.FpsCounter;

public class GameScreen extends BasicScreen {
	Texture texture;
	boolean showFPS = true;
	Stage uiStage;

	public GameScreen(Game game) {
		super(game);
		texture = new Texture("data/tileTest.png");
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		texture.setWrap(TextureWrap.ClampToEdge, TextureWrap.ClampToEdge);
		uiStage = new Stage( Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
		uiStage.getSpriteBatch().getTransformMatrix().scale(1, -1, 1);
		Actor fpsCounter = new FpsCounter(this);
		fpsCounter.setPosition(10, 20);
		uiStage.addActor(fpsCounter);		
	}

	/* (non-Javadoc)
	 * @see com.mutantamoeba.ld25.screens.BasicScreen#render(float)
	 */
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1,0,1,1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		stage.draw();
		
		uiStage.act(delta);
		uiStage.draw();		
	}
	
	
}
