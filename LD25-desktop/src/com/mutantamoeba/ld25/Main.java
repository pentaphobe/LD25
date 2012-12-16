package com.mutantamoeba.ld25;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "No, Mister Bond...";
		cfg.useGL20 = false;
		cfg.width = 640;
		cfg.height = 480;		
//		cfg.vSyncEnabled = true;
//		cfg.useCPUSynch = true;
		new LwjglApplication(new LD25(), cfg);
	}
}
