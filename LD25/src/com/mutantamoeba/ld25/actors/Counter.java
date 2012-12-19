package com.mutantamoeba.ld25.actors;

public class Counter {
	float frequency;
	float counter = 0;
	public Counter(float frequency) {
		this.frequency = frequency;
		reset();
	}
	public void reset() {
		counter = 0;
	}
	public boolean ready() {
		return counter >= frequency;
	}
	public void update(float delta) {
		counter += delta;
	}
	
}
