package com.mutantamoeba.ld25;

import com.mutantamoeba.ld25.engine.Console;

public class GameEconomy {
	static final float DEFAULT_STARTING_BUDGET = 10 * 1000;
	private float	currentBudget;
	
	// [@temp just a constant increase]
	float	incomePerSecond = 20f;
	
	GameEconomy() {
		setBudget(DEFAULT_STARTING_BUDGET);
	}

	public void tick(float delta) {		
		credit(incomePerSecond * GameWorld.instance().roomMap.entryRooms.size * delta);		
	}
	
	public void credit(float amount) {
		setBudget(budget() + amount);
	}

	/**
	 * @param currentBudget the currentBudget to set
	 */
	void setBudget(float currentBudget) {
		this.currentBudget = currentBudget;
//		Console.debug("new budget:%.2f", currentBudget);
	}

	/**
	 * @return the currentBudget
	 */
	public float budget() {
		return currentBudget;
	}

	public void debit(float amount) {
//		Console.debug("debiting %f", amount);
//		Console.debug("  budget before: %f", budget());
		setBudget(budget() - amount);		
//		Console.debug("  budget after : %f", budget());
	}
}
