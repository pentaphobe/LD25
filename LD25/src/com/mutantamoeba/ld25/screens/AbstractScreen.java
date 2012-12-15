package com.mutantamoeba.ld25.screens;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public abstract class AbstractScreen implements Screen {
	protected final Game game;
	
	public AbstractScreen(Game game) {
		this.game = game;
	}

}
