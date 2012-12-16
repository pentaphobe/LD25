/**
 * 
 */
package com.mutantamoeba.ld25;

import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.mutantamoeba.ld25.engine.Console;
import com.mutantamoeba.ld25.screens.BasicScreen;

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
			addListener(new DragListener() {

				/* (non-Javadoc)
				 * @see com.badlogic.gdx.scenes.scene2d.utils.DragListener#drag(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int)
				 */
				@Override
				public void drag(InputEvent event, float x, float y, int pointer) {
					beginEdit();
					setTile(x, y, 0, 24);
					endEdit();
					super.drag(event, x, y, pointer);
				}				
			});
			addListener(new ClickListener() {

				/* (non-Javadoc)
				 * @see com.badlogic.gdx.scenes.scene2d.utils.ClickListener#clicked(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float)
				 */
				@Override
				public void clicked(InputEvent event, float x, float y) {
					beginEdit();
					setTile(x, y, 0, 24);
					endEdit();
					super.clicked(event, x, y);
				}
				
			});
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
						cache.add(texture, tileX, tileY, texX * tileSize, texY * tileSize, tileSize, tileSize);
					}
				}
			}
			cacheID = cache.endCache();
			
		}
		public void setTile(int x, int y, int layer, int tileIndex) {
			if (x < 0 || y < 0 || x >= w || y >= h) return;
			theMap[y * w + x][layer] = tileIndex;
		}
		public void setTile(float x, float y, int layer, int tileIndex) {
			Vector2 pos = new Vector2(x, y);
			pos = this.stageToLocalCoordinates(pos);
			pos.div(tileSize);
			Console.debug("Setting %f, %f [layer:%d] to %d", pos.x, pos.y, layer, tileIndex);

			setTile((int)pos.x, (int)pos.y, layer, tileIndex);
		}
	}
	public TileTestScreen(Game game) {
		super(game);
		
//		texture = new Texture("data/tileTest.png");
		texture = new Texture("data/tiles.png");
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		texture.setWrap(TextureWrap.ClampToEdge, TextureWrap.ClampToEdge);
				 	
		tileMap = new SimpleTileMap(16, 32, texture);
		int wallTiles[] = new int[] { 0, 1, 2, 8, 9, 10, 16, 17, 18 };
		tileMap.beginEdit();
		for (int i=0;i<tileMap.area;i++) {
			tileMap.theMap[i][0] = 27;
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
		uiStage.getSpriteBatch().getTransformMatrix().scale(1, -1, 1);
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
				this.setSize(30, 10);
			}

			/* (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.Actor#act(float)
			 */
			@Override
			public void act(float delta) {
				// TODO Auto-generated method stub
				super.act(delta);
			}

			/* (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.Actor#fire(com.badlogic.gdx.scenes.scene2d.Event)
			 */
			@Override
			public boolean fire(Event event) {
				Console.debug("FPSCounter");
				if (event instanceof InputEvent && ((InputEvent)event).getType() == InputEvent.Type.touchDown) {
					Console.debug("FPS counter:%s", event);
				}
				return super.fire(event);
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
		return stage.touchDragged(x, y, pointer);
	}	
}
