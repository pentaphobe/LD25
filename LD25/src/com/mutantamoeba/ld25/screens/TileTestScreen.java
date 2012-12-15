/**
 * 
 */
package com.mutantamoeba.ld25.screens;

import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mutantamoeba.ld25.engine.Console;

/** The main game screen
 * @author keili
 *
 */
public class TileTestScreen extends BasicScreen {
	SimpleTileMap tileMap;
	static Random rand = new Random();
	Texture texture;
	boolean showFPS = true;
	Stage uiStage;

	public class SimpleTileMap extends Actor {
		static final int TOTAL_LAYERS = 2;
		Texture texture;
		SpriteCache cache;
		int w, h, area;
		int textureTileWidth, textureTileHeight;
		int maxTileIndex;
		int tileSize;
		int theMap[][];
		int cacheID = -1;
		SimpleTileMap(int w, int h, Texture texture) {
			super();
			this.tileSize = 32;
			setSize(w * this.tileSize, h * this.tileSize);

			this.w = w;
			this.h = h;
			this.area = this.w * this.h;
			this.theMap = new int[this.area][TOTAL_LAYERS];
			for (int i=0;i<this.area;i++) {
				for (int j=0;j<TOTAL_LAYERS;j++) {
					theMap[i][j] = -1;
				}
			}
			this.texture = texture;
			this.textureTileWidth = this.texture.getWidth() / this.tileSize;
			this.textureTileHeight = this.texture.getHeight() / this.tileSize;
			this.maxTileIndex = this.textureTileWidth * this.textureTileHeight;
			this.cache = new SpriteCache(this.area * TOTAL_LAYERS, true);
			Console.debug("constructed SimpleTileMap:");
			Console.debug("  tiles: %dx%d (area:%d)", this.w, this.h, this.area);
			Console.debug("  texture tile size: %d x %d", this.textureTileWidth, this.textureTileHeight);
			Console.debug("  tileSize: %d", tileSize);
			
		}
		/* (non-Javadoc)
		 * @see com.badlogic.gdx.scenes.scene2d.Actor#draw(com.badlogic.gdx.graphics.g2d.SpriteBatch, float)
		 */
		@Override
		public void draw(SpriteBatch batch, float parentAlpha) {
			cache.setProjectionMatrix(batch.getProjectionMatrix());
//			batch.enableBlending();
//			batch.setBlendFunction(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			
			cache.begin();
			Gdx.gl.glEnable(GL11.GL_BLEND);
			Gdx.gl.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			cache.draw(cacheID);			
			Gdx.gl.glDisable(GL11.GL_BLEND);
//			batch.disableBlending();
			cache.end();
//			super.draw(batch, parentAlpha);
		}

		/* (non-Javadoc)
		 * @see com.badlogic.gdx.scenes.scene2d.Actor#act(float)
		 */
		@Override
		public void act(float delta) {
			// TODO Auto-generated method stub
			super.act(delta);
		}
		
		public void beginEdit() {
			cache.clear();
			cache.beginCache();			
		}
		public void endEdit() {
			for (int layer = 0; layer < TOTAL_LAYERS; layer++) {
				for (int y=0, offs=0;y<h;y++) {
					int tileY = y * (tileSize);
					for (int x=0;x<w;x++, offs++) {
						int tileX = x * (tileSize);
						int tileIndex = theMap[offs][layer];
						if (tileIndex == -1) {
							continue;
						}
						int texX = tileIndex % textureTileWidth;
						int texY = tileIndex / textureTileWidth;					
						cache.add(texture, tileX, tileY, texX * tileSize, texY * tileSize, texX + tileSize, texY + tileSize);
					}
				}
			}
			cacheID = cache.endCache();
			
		}
		/* (non-Javadoc)
		 * @see com.badlogic.gdx.scenes.scene2d.Actor#notify(com.badlogic.gdx.scenes.scene2d.Event, boolean)
		 */
		@Override
		public boolean fire(Event event) {
			if (!(event instanceof InputEvent)) {
				return false;
			}
			InputEvent inputEvent = (InputEvent)event;
			if (inputEvent.getType() == Type.touchDown
					|| inputEvent.getType() == Type.touchDragged) {				
				Vector2 pos = new Vector2(inputEvent.getStageX(), inputEvent.getStageY());
				pos = this.stageToLocalCoordinates(pos);
				pos.div(tileSize);
				int tx = (int)pos.x;
				int ty = (int)pos.y;
				beginEdit();
				theMap[ty * w + tx][1] = 4;
				endEdit();
				Console.debug("set %d, %d to 0", tx, ty);
			}
			
			return super.fire(event);
		}
		
		 
	}
	public TileTestScreen(Game game) {
		super(game);
		
		texture = new Texture("data/tileTest.png");
		tileMap = new SimpleTileMap(64, 64, texture);
		int wallTiles[] = new int[] { 0, 1, 2, 8, 9, 10, 16, 17, 18 };
		tileMap.beginEdit();
		for (int i=0;i<tileMap.area;i++) {
			tileMap.theMap[i][0] = 4;
			if (rand.nextInt(100) < 10) {
				int tx = i % tileMap.textureTileWidth;
				int ty = i / tileMap.textureTileWidth;
				int wx = tx % 3;
				int wy = ty % 3;
				tileMap.theMap[i][1] = wallTiles[wy * 3 + wx];
			} 
		}
		tileMap.endEdit();
		stage.addActor(tileMap);
		uiStage = new Stage( Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
		Actor fpsCounter = new Actor() {
			int lastFPS;
			/* (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.Actor#draw(com.badlogic.gdx.graphics.g2d.SpriteBatch, float)
			 */
			@Override
			public void draw(SpriteBatch batch, float parentAlpha) {
				// TODO Auto-generated method stub
				BitmapFont fnt = getFont();				
				
				fnt.draw(batch, 
						String.format("FPS:%d", Gdx.graphics.getFramesPerSecond()), getX(), getY());
				
			}

			/* (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.Actor#act(float)
			 */
			@Override
			public void act(float delta) {
				// TODO Auto-generated method stub
				super.act(delta);
			}
		};
		fpsCounter.setPosition(10, 20);
		uiStage.addActor(fpsCounter);
	}
	/* (non-Javadoc)
	 * @see com.mutantamoeba.ld25.screens.BasicScreen#render(float)
	 */
	@Override
	public void render(float delta) {	
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
		
		Gdx.gl.glClearColor(1,0,1,1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		stage.draw();
		
		uiStage.act(delta);
		uiStage.draw();
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
		return super.touchDragged(x, y, pointer);
	}	
}
