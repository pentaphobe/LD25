package com.mutantamoeba.ld25;

public class GameEconomy {
	static final float DEFAULT_STARTING_BUDGET = 100 * 1000;
	private float	currentBudget;
	
	// [@temp just a constant increase]
	float	incomePerSecond;
	
	GameEconomy() {
		setBudget(DEFAULT_STARTING_BUDGET);
	}

	public void tick(float delta) {
		credit(incomePerSecond * delta);		
	}
	
	public void credit(float amount) {
		setBudget(budget() + amount);
	}

	/**
	 * @param currentBudget the currentBudget to set
	 */
	void setBudget(float currentBudget) {
		this.currentBudget = currentBudget;
	}

	/**
	 * @return the currentBudget
	 */
	public float budget() {
		return currentBudget;
	}
}
