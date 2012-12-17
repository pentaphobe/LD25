package com.mutantamoeba.ld25;

import com.mutantamoeba.ld25.screens.GameScreen;

public abstract class GameTool {
	private GameScreen gameScreen;
	private String name;
	private float cost;
	
	public GameTool(String name, GameScreen gameScreen, float cost) {
		this.gameScreen = gameScreen;
		this.cost = cost;
		this.setName(name);
	}
	public void applyCost() {
		gameScreen.getWorld().getEconomy().debit(cost);
	}
	public boolean canApply() {
		return gameScreen.getWorld().getEconomy().budget() >= cost;
	}
	public abstract void apply(int mx, int my);
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
}
