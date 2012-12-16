package com.mutantamoeba.ld25.actors;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.mutantamoeba.ld25.GameWorld;

public class EntityGroup extends Group {
	GameWorld world;
	public EntityGroup(GameWorld world) {
		this.world = world;
	}
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Group#drawChildren(com.badlogic.gdx.graphics.g2d.SpriteBatch, float)
	 */
	@Override
	protected void drawChildren(SpriteBatch batch, float parentAlpha) {
//		for (Actor act:this.getChildren()) {
//			Console.debug("%s", act);
//		}
//		Gdx.gl.glEnable(GL11.GL_TEXTURE_2D);
		super.drawChildren(batch, parentAlpha);
	}

}
