package com.mutantamoeba.ld25.screens;

import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.mutantamoeba.ld25.engine.Console;

public class TestScreen extends BasicScreen {
	static Random rand = new Random();
	public TestScreen(Game game) {
		super(game);
		
	}

	public class TestActor extends Actor {
		private float angle;
		private Color color;
		
		public TestActor() {
			super();			
			angle = rand.nextInt(200) - 100;
			color = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), 1);
			System.out.println(color);
			System.out.println(rand.nextInt(255));
		}
		@Override
		public void draw(SpriteBatch batch, float parentAlpha) {
			// TODO Auto-generated method stub
			super.draw(batch, parentAlpha);
			getFont().setColor(color);
			getFont().draw(getStage().getSpriteBatch(), "hello", getX(), getY());						
		}

		@Override
		public void act(float delta) {
			// TODO Auto-generated method stub
			super.act(delta);
			updatePosition();
			angle += delta * 5.0f;
		}
		
		public void updatePosition() {
			this.setPosition(Gdx.graphics.getWidth() / 2.0f + 200f * ((float)Math.sin(angle / 7f) + (float)Math.cos(angle / 13f)), 
					Gdx.graphics.getHeight() / 2.0f + 100f * ((float)Math.cos(angle / 3f) + (float)Math.sin(angle / 17f)));

		}

		@Override
		public boolean fire(Event event) {
			// TODO Auto-generated method stub
			Console.debug("fire:%s", event);							
			return super.fire(event);
		}

		@Override
		public boolean notify(Event event, boolean capture) {
			// TODO Auto-generated method stub			
			Console.debug("notify:%s", event);							
			return super.notify(event, capture);
		}

		@Override
		public Actor hit(float x, float y, boolean touchable) {
			// TODO Auto-generated method stub
			Console.debug("hit:%f, %f, %s", x, y, touchable ? "true" : "false");							
			return super.hit(x, y, touchable);
		}		
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		for (int i=0;i<50;i++) 
			getStage().addActor(new TestActor());
	}

}
