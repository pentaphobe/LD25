package com.mutantamoeba.ld25.utils;

public abstract class Singleton<T> {
	T	_instance;
	public abstract T create();
	public T instance() {
		if (_instance == null) {
			_instance = create();
		}
		return _instance;
	}
}
