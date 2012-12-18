package com.mutantamoeba.ld25;

import com.mutantamoeba.ld25.engine.Console;

public class RoomConfig {
	String type;
	private int level;
	float health;
	private RoomTemplate template;
	public RoomConfig(RoomTemplate template) {
		this.template(template);
		this.level(0);
		this.type = template.name;
		this.health = 100;
	}
	public boolean canUpgrade() {
		return this.level() < this.template().maxLevel-1;
	}
	public void upgrade() {
		if (canUpgrade()) {
			template().upgradeConfig(this);
		}
	}
	/**
	 * @param level the level to set
	 */
	public void level(int level) {
		this.level = level;
	}
	/**
	 * @return the level
	 */
	public int level() {
		return level;
	}
	/**
	 * @param template the template to set
	 */
	public void template(RoomTemplate template) {
		this.template = template;
	}
	/**
	 * @return the template
	 */
	public RoomTemplate template() {
		return template;
	}
	public float getUpgradeCost() {		
		if (canUpgrade()) {
			return this.template.cost[level()+1];
		}
		Console.debug("Can't upgrade");
		return 0;
	}
}
