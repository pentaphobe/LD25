package com.mutantamoeba.ld25.screens;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.mutantamoeba.ld25.engine.BasicInputProcessor;

public abstract class AbstractScreen extends BasicInputProcessor implements Screen {
	protected final Game game;
	
	public AbstractScreen(Game game) {
		this.game = game;
	}

}
