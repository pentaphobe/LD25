package com.mutantamoeba.ld25;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Entry;

public class SoundWrapper implements Disposable {
	ObjectMap<String, Sound>	audioResources = new ObjectMap<String, Sound>();

	public void loadSound(String key, String localPath) {
		loadSound(key, Gdx.files.local(localPath));
	}
	
	public void loadSound(String key, FileHandle fileHandle) {
		Sound sound = Gdx.audio.newSound(fileHandle);
		audioResources.put(key, sound);
	}
	
	public void trigger(String key, float volume) {
		if (!audioResources.containsKey(key)) {
			return;
		}
		Sound snd = audioResources.get(key);
		snd.play(volume);
	}
	
	@Override
	public void dispose() {
		for (Entry<String, Sound> res:audioResources.entries()) {
			Sound snd = res.value;
			if (snd instanceof Disposable) {
				snd.dispose();
			} 
			audioResources.remove(res.key);
		}		
		
	}
	
}
