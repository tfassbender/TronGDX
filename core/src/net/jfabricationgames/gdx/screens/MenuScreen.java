package net.jfabricationgames.gdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

import net.jfabricationgames.gdx.TronGdxGame;
import net.jfabricationgames.gdx.menu.items.MenuButton;
import net.jfabricationgames.gdx.menu.items.MenuHeadline;

public class MenuScreen extends ScreenAdapter {
	
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	
	private Vector2 screenSize;

	private MenuHeadline headline;
	private MenuButton startButton;
	
	public MenuScreen() {
		screenSize = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();

		createHeadline();
		createButtons();
	}

	@Override
	public void render(float delta) {
		//draw the menu
		shapeRenderer.begin(ShapeType.Filled);
		startButton.renderBackground(shapeRenderer);
		headline.renderBackground(shapeRenderer);
		shapeRenderer.end();
		
		batch.begin();
		startButton.renderText(batch);
		headline.renderText(batch);
		batch.end();
		
		//handle button clicks
		startButton.handleClickEvent();
	}
	
	@Override
	public void hide() {
		dispose();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
		shapeRenderer.dispose();
	}
	
	private void createHeadline() {
		final Vector2 headlineSize = new Vector2(400, 100);
		final Vector2 headlinePosition = new Vector2((screenSize.x - headlineSize.x) / 2, screenSize.y - headlineSize.y - 50);
		
		headline = new MenuHeadline(headlinePosition, headlineSize, "TronGDX", 3f);
	}
	
	private void createButtons() {
		final Vector2 buttonSize = new Vector2(300, 100);
		final Vector2 startButtonPosition = new Vector2((screenSize.x - buttonSize.x) / 2, (screenSize.y - buttonSize.y) / 2);
		final Runnable startButtonRunnable = () -> TronGdxGame.getInstance().setScreen(new GameScreen());
		
		startButton = new MenuButton(startButtonPosition, buttonSize, "Start Game", startButtonRunnable);
	}
}
