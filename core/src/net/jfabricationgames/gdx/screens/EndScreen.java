package net.jfabricationgames.gdx.screens;

import java.util.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

import net.jfabricationgames.gdx.TronGdxGame;
import net.jfabricationgames.gdx.game.Winner;
import net.jfabricationgames.gdx.menu.items.MenuHeadline;

public class EndScreen extends ScreenAdapter {
	
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	
	private MenuHeadline headline;
	private MenuHeadline winnerMessage;
	
	public EndScreen(Winner winner) {
		Objects.requireNonNull(winner, "The winner can't be null");
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		
		createHeadline();
		createText(winner);
	}
	
	@Override
	public void render(float delta) {
		//clear the screen (with a black screen)
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//draw the menu
		shapeRenderer.begin(ShapeType.Filled);
		headline.renderBackground(shapeRenderer);
		winnerMessage.renderBackground(shapeRenderer);
		shapeRenderer.end();
		
		batch.begin();
		headline.renderText(batch);
		winnerMessage.renderText(batch);
		batch.end();
		
		//start a new game when space is pressed
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			TronGdxGame.getInstance().setScreen(new GameScreen());
		}
	}
	
	@Override
	public void hide() {
		dispose();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
		shapeRenderer.dispose();
		headline.dispose();
	}
	
	private void createHeadline() {
		final Vector2 screenSize = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		final Vector2 headlineSize = new Vector2(400, 100);
		final Vector2 headlinePosition = new Vector2((screenSize.x - headlineSize.x) / 2, screenSize.y - headlineSize.y - 50);
		
		headline = new MenuHeadline(headlinePosition, headlineSize, "GAME OVER", 3f);
	}
	
	private void createText(Winner winner) {
		final Vector2 screenSize = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		final Vector2 headlineSize = new Vector2(600, 300);
		final Vector2 headlinePosition = new Vector2((screenSize.x - headlineSize.x) / 2, screenSize.y - headlineSize.y - 200);
		
		String text;
		if (winner == Winner.PLAYER_1) {
			text = "Player 1 (red) won";
		}
		else if (winner == Winner.PLAYER_2) {
			text = "Player 2 (blue) won";
		}
		else {
			text = "Both players crashed";
		}
		text += "\n\nPress Space to start a new game.";
		winnerMessage = new MenuHeadline(headlinePosition, headlineSize, text, 2f);
		winnerMessage.setBgColor(Color.DARK_GRAY);
	}
}
