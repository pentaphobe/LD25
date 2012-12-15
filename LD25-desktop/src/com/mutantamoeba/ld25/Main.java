package com.mutantamoeba.ld25;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "LD25";
		cfg.useGL20 = true;
		cfg.width = 480;
		cfg.height = 320;		
//		cfg.vSyncEnabled = true;
//		cfg.useCPUSynch = true;
		new LwjglApplication(new LD25(), cfg);
	}
}
