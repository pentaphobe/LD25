package com.mutantamoeba.ld25;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.mutantamoeba.ld25.engine.Console;
import com.mutantamoeba.ld25.screens.TileTestScreen;

public class LD25 extends Game {
	public static final boolean DEBUG_MODE = true;
	public static final String VERSION = "0.0.1";
	public static final String LOG = "LD25: ";
			
	@Override
	public void create() {	
		Gdx.app.setLogLevel(Gdx.app.LOG_DEBUG);
		Console.debug("create");
//		if (DEBUG_MODE) {
			// skip the splash screen if we're in debug mode
//			setScreen(new MainMenu(this));
//		} else {
//			setScreen(new SplashScreen(this));		
//		}	
		setScreen(new TileTestScreen(this));
	}
	
	@Override
	public void dispose() {
		Console.debug("got to dispose");
		super.dispose();
	}
	
	
}
