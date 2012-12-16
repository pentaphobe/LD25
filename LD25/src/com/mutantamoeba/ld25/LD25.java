package com.mutantamoeba.ld25;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.mutantamoeba.ld25.engine.Console;
import com.mutantamoeba.ld25.screens.MainMenuScreen;

public class LD25 extends Game {
	public static boolean DEBUG_MODE = true;
	public static final String VERSION = "0.0.2";
	public static final String LOG = "NoMisterBond";
	
	static LD25 instance;
	
	@Override
	public void create() {	
		if (DEBUG_MODE) {
			Gdx.app.setLogLevel(Gdx.app.LOG_DEBUG);
		}
		Console.debug("create");
//		if (DEBUG_MODE) {
			// skip the splash screen if we're in debug mode
			setScreen(new MainMenuScreen(this));
//		} else {
//			setScreen(new SplashScreen(this));		
//		}	
//		setScreen(new GameScreen(this));
//		setScreen(new TileTestScreen(this));
	}
	
	@Override
	public void dispose() {
		Console.debug("got to dispose");
		super.dispose();
	}
	
	public static LD25 instance() {
		if (instance == null) {
			instance = new LD25();
		}
		return instance;
	}
}
