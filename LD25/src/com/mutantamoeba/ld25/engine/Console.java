package com.mutantamoeba.ld25.engine;

import com.badlogic.gdx.Gdx;
import com.mutantamoeba.ld25.LD25;
import com.mutantamoeba.ld25.utils.StringUtils;

/** Just a basic wrapper for console stuff
 * @author keili
 *
 */
public class Console {
	public static int stackTraceLevels = 0;
	public static void log(String fmt, Object... args) {
		Gdx.app.log(LD25.LOG, String.format(fmt, args));
	}
	public static void debug(String fmt, Object... args) {
		if (LD25.DEBUG_MODE) { 
			int idx = 2;
			StackTraceElement thr = Thread.currentThread().getStackTrace()[idx];
			Gdx.app.debug(LD25.LOG, String.format("%s(%d):", thr.getFileName(), thr.getLineNumber()) + String.format(fmt, args));
			for (int i=0; i<stackTraceLevels; i++) {
				thr = Thread.currentThread().getStackTrace()[idx+i];
				Gdx.app.debug(LD25.LOG, String.format("%scalled from: %s(%d)", StringUtils.repeat(' ', i*2), thr.getFileName(), thr.getLineNumber()));
			}
		}
	}
}
