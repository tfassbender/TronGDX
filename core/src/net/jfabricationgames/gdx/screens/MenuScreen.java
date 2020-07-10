package net.jfabricationgames.gdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

import net.jfabricationgames.gdx.TronGdxGame;
import net.jfabricationgames.gdx.menu.items.MenuButton;

public class MenuScreen extends ScreenAdapter {
	
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	
	private Vector2 screenSize;
	
	private MenuButton startButton;
	
	public MenuScreen() {
		screenSize = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		
		createButtons();
	}

	@Override
	public void render(float delta) {
		//draw the menu
		shapeRenderer.begin(ShapeType.Filled);
		startButton.renderButtonBackground(shapeRenderer);
		shapeRenderer.end();
		
		batch.begin();
		startButton.renderButtonText(batch);
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
	
	private void createButtons() {
		final Vector2 buttonSize = new Vector2(300, 100);
		final Vector2 startButtonPosition = new Vector2((screenSize.x - buttonSize.x) / 2, (screenSize.y - buttonSize.y) / 2);
		final Runnable startButtonRunnable = () -> TronGdxGame.getInstance().setScreen(new GameScreen());
		
		startButton = new MenuButton(startButtonPosition, buttonSize, "Start Game", startButtonRunnable);
	}
}
