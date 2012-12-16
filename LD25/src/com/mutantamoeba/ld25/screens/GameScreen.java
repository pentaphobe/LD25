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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mutantamoeba.ld25.GameWorld;
import com.mutantamoeba.ld25.actors.FpsCounter;
import com.mutantamoeba.ld25.engine.Console;

public class GameScreen extends BasicScreen {
	static final int WORLD_WIDTH = 16;
	static final int WORLD_HEIGHT = 16;
	static final int TILE_SIZE = 32;
	static final float SCROLL_SPEED = 200f; // pixels per second
	static final float SCROLL_FAST_MULTIPLIER = 3f; // how much faster than SCROLL_SPEED do we go in fast mode?
	
	Texture texture;
	boolean showFPS = true;
	Stage uiStage;
	GameWorld world;
	RoomRenderer roomRenderer;
	TileRenderer tileRenderer;
	GameTileset gameTiles;

	public GameScreen(Game game) {
		super(game);
		texture = new Texture("data/tiles.png");
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		texture.setWrap(TextureWrap.ClampToEdge, TextureWrap.ClampToEdge);
		gameTiles = new GameTileset(texture, 32);

		gameTiles.addSubset("blank", TileSubset.Type.SINGLE, 27);		
		gameTiles.addSubset("walls", TileSubset.Type.NINEPATCH, 0, 1, 2, 8, 9, 10, 16, 17, 18);
		gameTiles.addSubset("floor", TileSubset.Type.SINGLE, 24);
		
		
		uiStage = new Stage( Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
		uiStage.getSpriteBatch().getTransformMatrix().scale(1, -1, 1);
		Actor fpsCounter = new FpsCounter(this);
		fpsCounter.setPosition(10, 20);
		uiStage.addActor(fpsCounter);	
		
		world = new GameWorld(this, WORLD_WIDTH, WORLD_HEIGHT);
		
		tileRenderer = new TileRenderer(world, gameTiles);
		stage.addActor(tileRenderer);
		tileRenderer.updateFromMap();
		
		roomRenderer = new RoomRenderer(world);
		roomRenderer.addListener(new ClickListener() {
			/* (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.InputListener#touchDown(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, int)
			 */
			@Override
			public void clicked(InputEvent event, float x, float y) {				
				Console.debug("roomRenderer touched at %f, %f", x, y);
				super.clicked(event, x, y);
			}
			
		});
		stage.addActor(roomRenderer);
		
	}

	/* (non-Javadoc)
	 * @see com.mutantamoeba.ld25.screens.BasicScreen#render(float)
	 */
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		update(delta);
		
		stage.draw();			
		uiStage.draw();		
	}
	
	public void update(float delta) {
		OrthographicCamera cam = (OrthographicCamera)stage.getCamera();
		float moveX = 0;
		float moveY = 0;
		float scrollSpeed = SCROLL_SPEED * delta;
		if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
			scrollSpeed *= SCROLL_FAST_MULTIPLIER;
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
		if (uiStage.touchDown(x, y, pointer, button)) {
			return true;
		}
		Console.debug("touchdown %d, %d", x, y);
		
		return super.touchDown(x, y, pointer, button);
	}


	/* (non-Javadoc)
	 * @see com.mutantamoeba.ld25.engine.BasicInputProcessor#touchUp(int, int, int, int)
	 */
	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		if (uiStage.touchUp(x, y, pointer, button)) {
			return true;
		}
		return super.touchUp(x, y, pointer, button);
	}


	/* (non-Javadoc)
	 * @see com.mutantamoeba.ld25.engine.BasicInputProcessor#touchDragged(int, int, int)
	 */
	@Override
	public boolean touchDragged(int x, int y, int pointer) {

		if (uiStage.touchDragged(x, y, pointer)) {
			return true;
		}
		Console.debug("dragged");		
		return stage.touchDragged(x, y, pointer);
	}		
}
