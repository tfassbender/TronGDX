package net.jfabricationgames.gdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;

import net.jfabricationgames.gdx.game.GameField;

public class GameScreen extends ScreenAdapter {
	
	private GameField gameField;
	
	public GameScreen() {
		gameField = new GameField();
	}
	
	@Override
	public void render(float delta) {
		//clear the screen (with a black screen)
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//draw the game field
		gameField.render(delta);
	}
	
	@Override
	public void hide() {
		dispose();
	}
	
	@Override
	public void dispose() {
		gameField.dispose();
	}
}
