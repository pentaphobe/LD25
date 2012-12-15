package com.mutantamoeba.ld25.engine;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.mutantamoeba.ld25.LD25;

/** Just a basic wrapper for console stuff
 * @author keili
 *
 */
public class Console {
	public static void log(String fmt, Object... args) {
		Gdx.app.log(LD25.LOG, String.format(fmt, args));
	}
	public static void debug(String fmt, Object... args) {
		if (LD25.DEBUG_MODE) { 
			Gdx.app.debug(LD25.LOG, String.format(fmt, args));
		}
	}
}
