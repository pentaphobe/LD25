package com.mutantamoeba.ld25;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "No, Mister Bond...";
		cfg.useGL20 = true;
		cfg.width = 1024;
		cfg.height = 768;	
		cfg.fullscreen = false;
		cfg.resizable = false;
//		cfg.vSyncEnabled = true;
//		cfg.useCPUSynch = true;
		new LwjglApplication(LD25.instance(), cfg);
	}
}
