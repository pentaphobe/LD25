package com.mutantamoeba.ld25;

import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.mutantamoeba.ld25.engine.Console;

public class GameScoreKeeper {
	ObjectMap<String, Float>	scores = new ObjectMap<String, Float>();
	GameScoreKeeper() {
		
	}
	public void addScore(String name, float amount) {
		if (!scores.containsKey(name)) {
			scores.put(name, amount);
			return;
		}
		scores.put(name, scores.get(name) + amount);
		Console.debug("added score %s - total is now: %.0f", name, scores.get(name));
	}
	public String getScoreString() {
		String scoreString = "";
		for (Entry<String, Float> score:scores.entries()) {
			scoreString += String.format("  %s : %.0f\n", score.key, score.value);
		}
		return scoreString;
	}
}
