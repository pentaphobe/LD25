package com.mutantamoeba.ld25;

import com.mutantamoeba.ld25.screens.GameScreen;

public abstract class GameTool {
	protected GameScreen gameScreen;
	protected String name;
	protected float cost;
	
	public GameTool(String name, GameScreen gameScreen, float cost) {
		this.gameScreen = gameScreen;
		this.cost = cost;
		this.setName(name);
	}
	public void applyCost() {
		applyCost(cost);
	}
	public void applyCost(float val) {
		gameScreen.getWorld().getEconomy().debit(val);
	}
	public boolean canApply() {
		return gameScreen.getWorld().getEconomy().budget() >= cost;
	}
	public boolean canApply(float dynamicCost) {
		return gameScreen.getWorld().getEconomy().budget() >= dynamicCost;
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
