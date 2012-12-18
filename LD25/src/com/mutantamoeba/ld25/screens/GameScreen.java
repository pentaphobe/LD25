package com.mutantamoeba.ld25.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.ObjectMap;
import com.mutantamoeba.ld25.GameTool;
import com.mutantamoeba.ld25.GameWorld;
import com.mutantamoeba.ld25.LD25;
import com.mutantamoeba.ld25.Room;
import com.mutantamoeba.ld25.RoomCreationTool;
import com.mutantamoeba.ld25.RoomRenderer;
import com.mutantamoeba.ld25.RoomUpgradeTool;
import com.mutantamoeba.ld25.actors.BondEntity;
import com.mutantamoeba.ld25.actors.EntityGroup;
import com.mutantamoeba.ld25.actors.FpsCounter;
import com.mutantamoeba.ld25.actors.GameEntity;
import com.mutantamoeba.ld25.actors.SelectionBox;
import com.mutantamoeba.ld25.actors.SidePanel;
import com.mutantamoeba.ld25.actors.SimpleButton;
import com.mutantamoeba.ld25.actors.SimpleTextButton;
import com.mutantamoeba.ld25.actors.TextBox;
import com.mutantamoeba.ld25.actors.ToolButton;
import com.mutantamoeba.ld25.engine.Console;
import com.mutantamoeba.ld25.tilemap.GameTileset;
import com.mutantamoeba.ld25.tilemap.TileRenderer;
import com.mutantamoeba.ld25.tilemap.TileSubset;

public class GameScreen extends BasicScreen {
	static final int WORLD_WIDTH = 16;
	static final int WORLD_HEIGHT = 16;
	public static final int TILE_SIZE = 32;
	static final float SCROLL_SPEED = 200f; // pixels per second
	static final float SCROLL_FAST_MULTIPLIER = 3f; // how much faster than SCROLL_SPEED do we go in fast mode?
	private static final float SIDEPANEL_WIDTH = 90;
	
	public Texture texture;
	boolean showFPS = true;

	private GameWorld world;
	RoomRenderer roomRenderer;
	private TileRenderer tileRenderer;
	public GameTileset gameTiles;
	private EntityGroup entities;
	public Room currentRoom;
	SelectionBox selectionBox, toolSelectionBox, mouseHoverBox;
	private TextBox toolTipBox;
	public ShaderProgram shaderProgram;
	
	RoomInspector roomInspector;
	private ObjectMap<String, GameTool> tools = new ObjectMap<String, GameTool>();
	protected GameTool currentTool;
	private SidePanel sidePanel;
	private ParticleEffect particleEffect;
	

	public GameScreen(Game game) {
		super(game);
//		setClearScreen(false);
		
		texture = new Texture("data/tiles.png");
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		texture.setWrap(TextureWrap.ClampToEdge, TextureWrap.ClampToEdge);
		gameTiles = new GameTileset(texture, 32);

		gameTiles.addSubset("blank", TileSubset.Type.MULTI, 26, 27);		
//		gameTiles.addSubset("blank", TileSubset.Type.MULTI, 27, 24);
		gameTiles.addSubset("wall", TileSubset.Type.NINEPATCH, 16, 17, 18, 8, 9, 10, 0, 1, 2 );
		gameTiles.addSubset("floor", TileSubset.Type.SINGLE, 24);
		
//		uiStage.getSpriteBatch().getTransformMatrix().scale(1, -1, 1);
		
//		uiStage.getCamera().combined.set

		
		particleEffect = new ParticleEffect();
		particleEffect.load(Gdx.files.internal("data/particleEffects.p"), Gdx.files.internal("data"));
		particleEffect.setPosition(0, 0);
		
		
		setWorld(new GameWorld(this, WORLD_WIDTH, WORLD_HEIGHT));
		
		setTileRenderer(new TileRenderer(getWorld(), gameTiles));
		stage.addActor(getTileRenderer());

		getTileRenderer().updateFromMap();
		
		this.entities = new EntityGroup(getWorld());		
		stage.addActor(this.entities);		
		
		roomRenderer = new RoomRenderer(getWorld());
		roomRenderer.addListener(new ClickListener() {
			/* (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.InputListener#touchDown(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, int)
			 */
			@Override
			public void clicked(InputEvent event, float x, float y) {				
				float rx = x / (TILE_SIZE * GameWorld.ROOM_SIZE);
				float ry = y / (TILE_SIZE * GameWorld.ROOM_SIZE);

				// test behaviour
//				if (world.roomMap.get((int)rx, (int)ry) == null) {
//					world.roomMap.makeTemplatedRoom((int)rx, (int)ry);				
//					tileRenderer.updateFromMap();
//				}					
//				selectRoom((int)rx, (int)ry);
				if (currentTool != null) {
					currentTool.apply((int)rx, (int)ry);
				}
				
				super.clicked(event, x, y);
			}			
		});
		roomRenderer.addListener(new DragListener() {
			/* (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.InputListener#touchDown(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, int)
			 */
			@Override
			public void drag(InputEvent event, float x, float y, int pointer) {				
				x /= TILE_SIZE * GameWorld.ROOM_SIZE;
				y /= TILE_SIZE * GameWorld.ROOM_SIZE;

				// test behaviour
//				if (world.roomMap.get((int)x, (int)y) == null) {
//					world.roomMap.makeTemplatedRoom((int)x, (int)y);
//					tileRenderer.updateFromMap();
//				}
//				selectRoom((int)x, (int)y);
				if (currentTool != null) {
					currentTool.apply((int)x, (int)y);
				}
				super.drag(event, x, y, pointer);
			}			
		});
		roomRenderer.addListener(new InputListener() {
			/* (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.InputListener#touchDown(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, int)
			 */
			@Override
			public boolean mouseMoved(InputEvent event, float x, float y) {				
				int mx = (int) (x / (TILE_SIZE * GameWorld.ROOM_SIZE));
				int my = (int) (y / (TILE_SIZE * GameWorld.ROOM_SIZE));

				// test behaviour
//				if (world.roomMap.get((int)x, (int)y) == null) {
//					world.roomMap.makeTemplatedRoom((int)x, (int)y);
//					tileRenderer.updateFromMap();
//				}
//				selectRoom((int)x, (int)y);
				Room room = world.roomMap.get(mx, my);
				int roomSize = (TILE_SIZE * GameWorld.ROOM_SIZE);
				mouseHoverBox.setBounds(mx * roomSize, my * roomSize, roomSize, roomSize);

				if (room != null) {
					
					room.mouseMoved(x - (mx * roomSize), y - (my * roomSize));					
					mouseHoverBox.setVisible(false);

				} else {
					mouseHoverBox.setColor(1,1,1,1);
					mouseHoverBox.setVisible(true);

				}
				return super.mouseMoved(event, x, y);
			}			
		});
		roomRenderer.setZIndex(0);
		stage.addActor(roomRenderer);
		
		TextureRegion region = new TextureRegion(texture, 224, 160, 32, 32);
		selectionBox = new SelectionBox(new NinePatch(region, 2, 2, 2, 2));
//		selectionBox.setBounds(10, 10, TILE_SIZE * GameWorld.ROOM_SIZE, TILE_SIZE * GameWorld.ROOM_SIZE);
		selectionBox.setZIndex(10000);
		selectionBox.setVisible(false);
		roomRenderer.addActor(selectionBox);

		setupUI();

		
//		OrthographicCamera cam = (OrthographicCamera)stage.getCamera();
//		cam.translate(1000, 1000);
		
		if (Gdx.graphics.isGL20Available()) {
			shaderProgram = createShader();
			stage.getSpriteBatch().setShader(shaderProgram);
//			tileRenderer.setShader(sp);
		}
		
	}
	
	private void setupUI() {
		Actor fpsCounter = new FpsCounter(this);
		fpsCounter.setPosition(Gdx.graphics.getWidth() - 60, Gdx.graphics.getHeight() - 20);
		uiStage.addActor(fpsCounter);	
		
		SimpleTextButton budget = new SimpleTextButton(this, "") {

			/* (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.Actor#act(float)
			 */
			@Override
			public void act(float delta) {
				setLabel(String.format("budget: $%.2f", getWorld().getEconomy().budget()));
				super.act(delta);
			}
			
		};
		budget.setPosition(10, Gdx.graphics.getHeight() - 20);		
		uiStage.addActor(budget);
		
		SimpleTextButton entityCount = new SimpleTextButton(this, "") {
			/* (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.Actor#act(float)
			 */
			@Override
			public void act(float delta) {
				setLabel(String.format("total entities: %d", entities.getChildren().size));
				super.act(delta);
			}
			
		};
		entityCount.setPosition(10, Gdx.graphics.getHeight() - 50);
		uiStage.addActor(entityCount);
		
		
		TextureRegion region = new TextureRegion(texture, 192, 192, 64, 64);
		NinePatch ninePatch = new NinePatch(region);
		ninePatch.setTopHeight(8);
		ninePatch.setBottomHeight(8);
		ninePatch.setLeftWidth(8);
		ninePatch.setRightWidth(8);
		ninePatch.setPadding(2, 2, 0, 1);
		
		roomInspector = new RoomInspector(ninePatch, getFont());
		roomInspector.setSize(200, 150);
		roomInspector.setPosition(Gdx.graphics.getWidth() - roomInspector.getWidth(), Gdx.graphics.getHeight() - roomInspector.getHeight() - fpsCounter.getHeight() - 5);

		///////// [@note this is the first object in the list now]
		uiStage.getActors().insert(0, roomInspector);
		
		region = new TextureRegion(texture, 192, 160, 32, 32);
		NinePatch panelPatch = new NinePatch(region, 2, 2, 2, 2);
//		panelPatch.setTopHeight(2);
//		panelPatch.setBottomHeight(2);
//		panelPatch.setLeftWidth(2);
//		panelPatch.setRightWidth(2);
//		panelPatch.setMiddleWidth(28);
//		panelPatch.setMiddleHeight(28);
		sidePanel = new SidePanel(panelPatch);
		sidePanel.setBounds(-1, -1, 96, Gdx.graphics.getHeight()+2);
		uiStage.getActors().insert(0, sidePanel);
		
		TextureRegion buttRegions[] = new TextureRegion[3];
		buttRegions[ToolButton.NORMAL] = new TextureRegion(texture, 160, 128, 32, 32);
		buttRegions[ToolButton.HOVER] = new TextureRegion(texture, 192, 128, 32, 32);
		buttRegions[ToolButton.DOWN] = new TextureRegion(texture, 224, 128, 32, 32);
		
		region = new TextureRegion(texture, 224, 160, 32, 32);
		toolSelectionBox = new SelectionBox(new NinePatch(region, 2, 2, 2, 2));
		toolSelectionBox.setZIndex(10000);
		toolSelectionBox.setTouchable(Touchable.disabled);
		uiStage.addActor(toolSelectionBox);		
		
		region = new TextureRegion(texture, 160, 192, 32, 32);
		mouseHoverBox = new SelectionBox(new NinePatch(region, 2, 2, 2, 2));
		mouseHoverBox.setZIndex(50000);
		mouseHoverBox.setTouchable(Touchable.disabled);
		roomRenderer.addActor(mouseHoverBox);
		
		setupTools();
		
		ClickListener toolSelectCallback = new ClickListener() {
			/* (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.utils.ClickListener#clicked(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float)
			 */
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (((ToolButton)event.getTarget()).isEnabled()) {
					event.cancel();
					String toolName = ((ToolButton)event.getTarget()).toolName;
					selectTool(toolName);
					ToolButton me = (ToolButton)event.getTarget();
					toolSelectionBox.setBounds(me.getX(), me.getY(), me.getWidth(), me.getHeight());
					super.clicked(event, x, y);
				}
			}			
		};
		InputListener toolTipCallback = new InputListener() {

			@Override
			public void enter(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				Actor listener = event.getListenerActor();
				if (listener instanceof ToolButton) {
					ToolButton button = (ToolButton)listener;
					String tipText = button.getToolTip();
//					Console.debug("setting tool tip to %s", tipText);
					GameTool tool = getTool(button.getToolName());
					int cost = (int)tool.getCost(); 
					if (cost > 0) {
						tipText = tipText + String.format(" $%d", cost);
					} else {
						tipText = tipText + "$depends on room";
					}
					getToolTipBox().setLabel( tipText);
					getToolTipBox().setVisible(true);
					if (getToolTipBox().getX() == 0 && getToolTipBox().getY() == 0) {
						getToolTipBox().setPosition(button.getX() + button.getWidth(), button.getY());
					} else {
						getToolTipBox().addAction(Actions.moveTo(button.getX() + button.getWidth(), button.getY() + button.getHeight()/2f, 0.075f));
					}
				}
				super.enter(event, x, y, pointer, fromActor);
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer,
					Actor toActor) {
				toolTipBox.setVisible(false);
				super.exit(event, x, y, pointer, toActor);
			}
			
		};
		
		ToolButton butt = new ToolButton(buttRegions, new TextureRegion(texture, 128, 96, 32, 32), "remove");
		butt.setBounds(10, 30, 64, 64);
		butt.addListener(toolSelectCallback);
		butt.addListener(toolTipCallback);
		butt.setToolTip("remove a room");
		sidePanel.addActor(butt);
				
		butt = new ToolButton(buttRegions, new TextureRegion(texture, 224, 64, 32, 32), "upgrade");
		butt.setBounds(10, 94, 64, 64);
		butt.addListener(toolSelectCallback);
		butt.addListener(toolTipCallback);
		butt.setToolTip("upgrade a room");
		sidePanel.addActor(butt);		

		butt = new ToolButton(buttRegions, new TextureRegion(texture, 160, 96, 32, 32), "gas");
		butt.setBounds(10, 158, 64, 64);
		butt.addListener(toolSelectCallback);
		butt.addListener(toolTipCallback);
		butt.setToolTip("gas chamber");
		sidePanel.addActor(butt);
		
		butt = new ToolButton(buttRegions, new TextureRegion(texture, 192, 96, 32, 32), "laser");
		butt.setBounds(10, 222, 64, 64);
		butt.addListener(toolSelectCallback);
		butt.addListener(toolTipCallback);
		butt.setToolTip("laser turret room");
		sidePanel.addActor(butt);		
		
		butt = new ToolButton(buttRegions, new TextureRegion(texture, 224, 96, 32, 32), "dart");
		butt.setBounds(10, 286, 64, 64);
		butt.addListener(toolSelectCallback);
		butt.addListener(toolTipCallback);
		butt.setToolTip("poison dart room");
		sidePanel.addActor(butt);		
		
		butt = new ToolButton(buttRegions, new TextureRegion(texture, 192, 64, 32, 32), "trapdoor");
		butt.setBounds(10, 350, 64, 64);
		butt.addListener(toolSelectCallback);
		butt.addListener(toolTipCallback);
		butt.setToolTip("trapdoor room");
		sidePanel.addActor(butt);

		butt = new ToolButton(buttRegions, new TextureRegion(texture, 160, 64, 32, 32), "basic");
		butt.setBounds(10, 414, 64, 64);
		butt.addListener(toolSelectCallback);
		butt.addListener(toolTipCallback);
		butt.setToolTip("basic room");
		sidePanel.addActor(butt);		
		
		setToolTipBox(new TextBox(this, "tooltip"));
		getToolTipBox().setVisible(false);
		uiStage.addActor(getToolTipBox());
		
	}
	public void setupTools() {
		GameTool tool = new GameTool("remove", this, 200) {
			@Override
			public void apply(int mx, int my) {
				if (canApply()) {
					getWorld().roomMap.remove(mx, my);
					getTileRenderer().updateFromMap();
					applyCost();
					deselectRoom();
				}
			}
			
		};
		tools.put(tool.getName(), tool);
	
		tool = new RoomCreationTool("basic", this);
		tools.put(tool.getName(), tool);			
		
		tool = new RoomCreationTool("gas", this);
		tools.put(tool.getName(), tool);	
		
		tool = new RoomCreationTool("laser", this);
		tools.put(tool.getName(), tool);					
		
		tool = new RoomCreationTool("dart", this);
		tools.put(tool.getName(), tool);
		
		tool = new RoomCreationTool("trapdoor", this);
		tools.put(tool.getName(), tool);		
		
		tool =  new RoomUpgradeTool("upgrade", this);
		tools.put(tool.getName(), tool);
	}
	public GameTool getTool(String toolName) {
//		Console.debug("tool select:%s", toolName);
		if (!tools.containsKey(toolName)) {
			Console.debug("no tool implemented for %s", toolName);
			return null;
		}
		return tools.get(toolName);
		
	}
	public void selectTool(String toolName) {
		GameTool tool = getTool(toolName);
		if (tool != null) {
			currentTool = tool;
		}
	}

	private ShaderProgram createShader() {
		// from SpriteBatch.java (with some tweaks)

		String vertexShader = "attribute vec4 " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
			+ "attribute vec4 " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
			+ "attribute vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
			+ "uniform mat4 u_projTrans;\n" //
			+ "uniform mat4 u_proj;\n" //
			+ "varying vec4 v_color;\n" //
			+ "varying vec2 v_texCoords;\n" //
			+ "\n" //
			+ "void main()\n" //
			+ "{\n" //
			+ "   v_color = " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
			+ "   v_texCoords = " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
			+ "   gl_Position =  u_projTrans * " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
			+ "}\n";
		String fragmentShader = "#ifdef GL_ES\n" //
			+ "#define LOWP lowp\n" //
			+ "precision mediump float;\n" //
			+ "#else\n" //
			+ "#define LOWP \n" //
			+ "#endif\n" //
			+ "varying LOWP vec4 v_color;\n" //
			+ "varying vec2 v_texCoords;\n" //
			+ "uniform sampler2D u_texture;\n" //
			+ "void main()\n"//
			+ "{\n" //			
			+ "  gl_FragColor = v_color * texture2D(u_texture, v_texCoords);\n" //
			+ "}";

		ShaderProgram shader = new ShaderProgram(vertexShader, fragmentShader);
		if (shader.isCompiled() == false) throw new IllegalArgumentException("couldn't compile shader: " + shader.getLog());
		Console.debug("compiled custom shader");
		
		return shader;
		
	}
	public void deselectRoom() {
		currentRoom = null;
		selectionBox.setVisible(false);
		roomInspector.setVisible(false);
	}
	public void selectRoom(int rx, int ry) {
		Room oldSelection = currentRoom;
		currentRoom = getWorld().roomMap.get(rx, ry);		
		
		if (currentRoom != null && currentRoom != oldSelection) {
			roomInspector.setRoom(currentRoom);
			roomInspector.setVisible(true);
			selectionBox.setVisible(true);
			float roomSize = TILE_SIZE * world.ROOM_SIZE;
			selectionBox.setBounds(rx * roomSize, ry * roomSize, TILE_SIZE * GameWorld.ROOM_SIZE, TILE_SIZE * GameWorld.ROOM_SIZE);
		} else {
			if (currentRoom == oldSelection && currentRoom != null) {
				//deselectRoom();
				currentRoom.activateTraps();
			}
		}
	}
	public void spawnActor(float x, float y) {
		// [@temp just adds an entity for now]
//		TextureRegion region = new TextureRegion(texture, 4 * 32, 5 * 32, 32, 32);
//		Actor actor = new BondEntity(region);
		if (Gdx.graphics.getFramesPerSecond() < 50) {
			Console.debug("framerate dropped at %d entities", entities.getChildren().size);
			for (int i=0;entities.getChildren().size > 0 && i<10;i++) {
				entities.getChildren().removeIndex(entities.getChildren().size-1);
			}
			return;
		}
		
		BondEntity actor = new BondEntity(texture, 44, 45);
		actor.setBounds(x, y, 32, 32);
		actor.setOrigin(16, 0);
		Room room = world.roomMap.getRoomAt(actor);
		if (room != null) {
			room.addEntity(actor);
			entities.addActor(actor);	
		}
			
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

		Vector2 pos = stage.stageToScreenCoordinates(new Vector2(0, 0));
		boolean fixed = false;
		
		// [@todo constrain screen scrolling maybe]
//		Console.debug("%s", pos);
//		if (pos.x > 0) { pos.x = -pos.x; fixed = true; }
//		if (pos.y > 0) { pos.y = -pos.y; fixed = true; }
//		if (fixed) {
//			cam.translate(stage.screenToStageCoordinates(pos));
//		}
//		Console.debug("  %s", cam.position);
		
		if (!isPaused()) {
			getWorld().tick(delta);
			stage.act(delta);
			uiStage.act(delta);
		}
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
		float minZoom = 0.25f;
		float maxZoom = 10;
		if (cam.zoom < minZoom) {
			cam.zoom = minZoom;
		} else if (cam.zoom > maxZoom) {
			cam.zoom = maxZoom;
		}
		return super.scrolled(amount);
	}
	
	/* (non-Javadoc)
	 * @see com.mutantamoeba.ld25.screens.BasicScreen#keyTyped(char)
	 */
	@Override
	public boolean keyTyped(char character) {
		switch (character) {
		case '2':
				getTileRenderer().setVisible(!getTileRenderer().isVisible());
				break;
		case '3':
			roomRenderer.setVisible(!roomRenderer.isVisible());
			break;
		case 'p':	// Pay me
				getWorld().getEconomy().credit(100000);
				break;
		case 'l':	// Lines / Debug
				LD25.DEBUG_MODE = false;
				return true;
		case 'r':	// raid
				getWorld().getSpawner().setSpawnFrequency(getWorld().getSpawner().getSpawnFrequency() * .25f);
				return true;
		case ' ':
			Console.debug("PAUSING");
			togglePaused();
			return true;
			
		}
		return super.keyTyped(character);
	}

	/**
	 * @param world the world to set
	 */
	public void setWorld(GameWorld world) {
		this.world = world;
	}

	/**
	 * @return the world
	 */
	public GameWorld getWorld() {
		return world;
	}

	/* (non-Javadoc)
	 * @see com.mutantamoeba.ld25.screens.BasicScreen#resize(int, int)
	 */
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		if (sidePanel != null) {
			sidePanel.setBounds(0, 0, SIDEPANEL_WIDTH, uiStage.getHeight());
		}
		
	}

	/* (non-Javadoc)
	 * @see com.mutantamoeba.ld25.engine.BasicInputProcessor#keyUp(int)
	 */
	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Input.Keys.F11){
			Console.debug("fullscreen toggle");
			toggleFullscreen();
		}
		return super.keyUp(keycode);
	}

	/* (non-Javadoc)
	 * @see com.mutantamoeba.ld25.screens.BasicScreen#render(float)
	 */
	@Override
	public void render(float delta) {		
		super.render(delta);
//	    particleEffect.draw(stage.getSpriteBatch(), delta);
	}

	/**
	 * @param tileRenderer the tileRenderer to set
	 */
	public void setTileRenderer(TileRenderer tileRenderer) {
		this.tileRenderer = tileRenderer;
	}

	/**
	 * @return the tileRenderer
	 */
	public TileRenderer getTileRenderer() {
		return tileRenderer;
	}

	public void removeEntity(GameEntity entity) {
		entities.removeActor(entity);
	}		
	public GameEntity addEntity(GameEntity entity) {
		entities.addActor(entity);
		return entity;
	}

	/**
	 * @param toolTipBox the toolTipBox to set
	 */
	public void setToolTipBox(TextBox toolTipBox) {
		this.toolTipBox = toolTipBox;
	}

	/**
	 * @return the toolTipBox
	 */
	public TextBox getToolTipBox() {
		return toolTipBox;
	}
}
