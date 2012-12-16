package com.mutantamoeba.ld25.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mutantamoeba.ld25.GameWorld;
import com.mutantamoeba.ld25.actors.FpsCounter;
import com.mutantamoeba.ld25.engine.Console;

public class GameScreen extends BasicScreen {
	static final int WORLD_WIDTH = 32;
	static final int WORLD_HEIGHT = 32;
	static final int TILE_SIZE = 32;
	
	Texture texture;
	boolean showFPS = true;
	Stage uiStage;
	GameWorld world;
	RoomRenderer roomRenderer;

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
		
		world = new GameWorld(this, WORLD_WIDTH, WORLD_HEIGHT);
		roomRenderer = new RoomRenderer(world);
		stage.addActor(roomRenderer);
	}

	/* (non-Javadoc)
	 * @see com.mutantamoeba.ld25.screens.BasicScreen#render(float)
	 */
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1,0,1,1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		update(delta);
		
		stage.draw();			
		uiStage.draw();		
	}
	
	public void update(float delta) {
		OrthographicCamera cam = (OrthographicCamera)stage.getCamera();
		float moveX = 0;
		float moveY = 0;
		float scrollSpeed = 2;
		if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
			scrollSpeed *= 3;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			moveX -= scrollSpeed * cam.zoom;
		} 
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			moveX += scrollSpeed * cam.zoom;
		} 
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			moveY += scrollSpeed * cam.zoom;
		} 
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			moveY -= scrollSpeed * cam.zoom;
		} 
		cam.translate(moveX, moveY);
		
		stage.act(delta);
		uiStage.act(delta);
	}
	
	/* (non-Javadoc)
	 * @see com.mutantamoeba.ld25.screens.BasicScreen#dispose()
	 */
	@Override
	public void dispose() {
		texture.dispose();
		super.dispose();
	}
	
	/* (non-Javadoc)
	 * @see com.mutantamoeba.ld25.screens.BasicScreen#scrolled(int)
	 */
	@Override
	public boolean scrolled(int amount) {
//		Console.debug("SCROLLED:%d", amount);
		OrthographicCamera cam = (OrthographicCamera)stage.getCamera(); 
		cam.zoom += amount / 10.0f;
		float minZoom = 0.1f;
		float maxZoom = 10;
		if (cam.zoom < minZoom) {
			cam.zoom = minZoom;
		} else if (cam.zoom > maxZoom) {
			cam.zoom = maxZoom;
		}
		return super.scrolled(amount);
	}
	
	/* (non-Javadoc)
	 * @see com.mutantamoeba.ld25.engine.BasicInputProcessor#touchDown(int, int, int, int)
	 */
	@Override
	public  boolean touchDown(int x, int y, int pointer, int button) {
//		Console.debug("touchdown %d, %d", x, y);
		uiStage.touchDown(x, y, pointer, button);
		return super.touchDown(x, y, pointer, button);
	}


	/* (non-Javadoc)
	 * @see com.mutantamoeba.ld25.engine.BasicInputProcessor#touchUp(int, int, int, int)
	 */
	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		uiStage.touchUp(x, y, pointer, button);
		return super.touchUp(x, y, pointer, button);
	}


	/* (non-Javadoc)
	 * @see com.mutantamoeba.ld25.engine.BasicInputProcessor#touchDragged(int, int, int)
	 */
	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		Console.debug("dragged");
		uiStage.touchDragged(x, y, pointer);
		return stage.touchDragged(x, y, pointer);
	}		
}
