package net.jfabricationgames.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class GameScreen extends ScreenAdapter {
	
	private ShapeRenderer shapeRenderer;
	private Vector2 position;
	private float speed = 100f;
	
	public GameScreen() {
		shapeRenderer = new ShapeRenderer();
		position = new Vector2(400, 300);
	}
	
	@Override
	public void render(float delta) {
		//retrieve input
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			position.x -= delta * speed;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			position.x += delta * speed;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			position.y += delta * speed;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			position.y -= delta * speed;
		}
		
		//clear the screen (with a black screen)
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//draw shapes
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.PINK);
		shapeRenderer.circle(position.x, position.y, 100);
		shapeRenderer.end();
	}
	
	@Override
	public void hide() {
		dispose();
	}
	
	@Override
	public void dispose() {
		shapeRenderer.dispose();
	}
}
