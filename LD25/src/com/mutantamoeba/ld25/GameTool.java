package com.mutantamoeba.ld25;

import com.mutantamoeba.ld25.screens.GameScreen;

public abstract class GameTool {
	protected GameScreen gameScreen;
	protected String name;
	private float cost;
	
	public GameTool(String name, GameScreen gameScreen, float cost) {
		this.gameScreen = gameScreen;
		this.setCost(cost);
		this.setName(name);
	}
	public void applyCost() {
		applyCost(getCost());
	}
	public void applyCost(float val) {
		gameScreen.getWorld().getEconomy().debit(val);
	}
	public boolean canApply() {
		return gameScreen.getWorld().getEconomy().budget() >= getCost();
	}
	public boolean canApply(float dynamicCost) {
		return gameScreen.getWorld().getEconomy().budget() >= dynamicCost;
	}	
	
	/**
	 * @param mx the map position X
	 * @param my map position Y
	 * @return true for successfully applied, false if bailed out
	 */
	public abstract boolean apply(int mx, int my);
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
	/**
	 * @param cost the cost to set
	 */
	public void setCost(float cost) {
		this.cost = cost;
	}
	/**
	 * @return the cost
	 */
	public float getCost() {
		return cost;
	}
}
