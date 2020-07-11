package net.jfabricationgames.gdx.game;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import net.jfabricationgames.gdx.TronGdxGame;
import net.jfabricationgames.gdx.screens.EndScreen;

public class GameField {
	
	public static final Color borderColor = Color.GRAY;
	
	private final int numFieldsX;
	private final int numFieldsY;
	private final int borderFields;
	
	private final float fieldWidth;
	private final float fieldHeight;
	
	private final FieldType[][] field;
	
	private TronRacer player1;
	private TronRacer player2;
	
	private ShapeRenderer shapeRenderer;
	
	private final int keepScreenActive = 2;
	private float screenActiveAfterGameOver;
	private boolean gameOver = false;
	
	public GameField() {
		numFieldsX = 150;
		numFieldsY = 100;
		borderFields = 3;
		field = new FieldType[numFieldsY][numFieldsX];
		
		fieldWidth = ((float) Gdx.graphics.getWidth()) / (numFieldsX + 2 * borderFields);
		fieldHeight = ((float) Gdx.graphics.getHeight()) / (numFieldsY + 2 * borderFields);
		
		shapeRenderer = new ShapeRenderer();
		
		initializeField();
		initializePlayers();
	}
	
	private void initializeField() {
		for (int y = 0; y < field.length; y++) {
			for (int x = 0; x < field[y].length; x++) {
				field[y][x] = FieldType.EMPTY;
			}
		}
	}
	
	private void initializePlayers() {
		Random random = new Random();
		
		final int offset = 10;//10 fields from border or other players
		
		int initialX = random.nextInt(numFieldsX - 2 * offset) + offset;
		int initialY = random.nextInt(numFieldsY - 2 * offset) + offset;
		MovementDirection initialDirection = chooseStartingDirection(initialX, initialY);
		player1 = new TronRacer(1, initialX, initialY, initialDirection);
		
		initialX = random.nextInt(numFieldsX - 4 * offset) + offset;
		initialY = random.nextInt(numFieldsY - 4 * offset) + offset;
		//offset from other player
		if (Math.abs(initialX - player1.getX()) < offset) {
			initialX += 2 * offset;
		}
		if (Math.abs(initialY - player1.getY()) < offset) {
			initialY += 2 * offset;
		}
		initialDirection = chooseStartingDirection(initialX, initialY);
		player2 = new TronRacer(2, initialX, initialY, initialDirection);
	}
	
	private MovementDirection chooseStartingDirection(int x, int y) {
		class WallDistance implements Comparable<WallDistance> {
			
			public WallDistance(MovementDirection wall, int distance) {
				this.wallDirection = wall;
				this.distance = distance;
			}
			
			public MovementDirection wallDirection;
			public int distance;
			
			@Override
			public int compareTo(WallDistance other) {
				return Integer.compare(distance, other.distance);
			}
		}
		
		List<WallDistance> distances = Arrays.asList(new WallDistance(MovementDirection.UP, numFieldsY - y), //
				new WallDistance(MovementDirection.DOWN, y), //
				new WallDistance(MovementDirection.LEFT, x), //
				new WallDistance(MovementDirection.RIGHT, numFieldsX - x));
		
		Collections.sort(distances);
		
		return MovementDirection.oppositeOf(distances.get(0).wallDirection);
	}
	
	public void render(float delta) {
		if (!gameOver) {
			//update the racers if the game is not over
			int player1LastX = player1.getX();
			int player1LastY = player1.getY();
			int player2LastX = player2.getX();
			int player2LastY = player2.getY();
			player1.updateMovement(delta);
			player2.updateMovement(delta);
			if (player1.hasMoved() && !isPlayerCrashed(player1)) {
				field[player1LastY][player1LastX] = FieldType.PLAYER_1;
			}
			if (player2.hasMoved() && !isPlayerCrashed(player2)) {
				field[player2LastY][player2LastX] = FieldType.PLAYER_2;
			}
		}
		
		shapeRenderer.begin(ShapeType.Filled);
		
		drawBorder();
		drawField();
		drawPlayers(delta);
		
		shapeRenderer.end();
		
		if (isGameOver()) {
			gameOver = true;
		}
		
		if (gameOver) {
			//wait a short time before changing to the end screen
			screenActiveAfterGameOver += delta;
			if (screenActiveAfterGameOver > keepScreenActive) {
				TronGdxGame.getInstance().setScreen(new EndScreen(getWinner()));
			}
		}
	}
	
	private void drawBorder() {
		shapeRenderer.setColor(borderColor);
		shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), fieldHeight * borderFields);
		shapeRenderer.rect(0, 0, fieldWidth * borderFields, Gdx.graphics.getHeight());
		shapeRenderer.rect(0, Gdx.graphics.getHeight() - fieldHeight * borderFields, Gdx.graphics.getWidth(), fieldHeight * borderFields);
		shapeRenderer.rect(Gdx.graphics.getWidth() - fieldWidth * borderFields, 0, fieldWidth * borderFields, Gdx.graphics.getHeight());
	}
	
	private void drawField() {
		for (int y = 0; y < field.length; y++) {
			for (int x = 0; x < field[y].length; x++) {
				drawField(field[y][x], x, y);
			}
		}
	}
	
	private void drawField(FieldType fieldType, int x, int y) {
		shapeRenderer.setColor(fieldType.getFieldColor());
		shapeRenderer.rect(getFieldXOnScreen(x), getFieldYOnScreen(y), fieldWidth, fieldHeight);
	}
	
	private void drawPlayers(float delta) {
		shapeRenderer.setColor(TronRacer.COLOR_PLAYER_1);
		shapeRenderer.ellipse(getFieldXOnScreen(player1.getX()), getFieldYOnScreen(player1.getY()), fieldWidth, fieldHeight);
		shapeRenderer.setColor(TronRacer.COLOR_PLAYER_2);
		shapeRenderer.ellipse(getFieldXOnScreen(player2.getX()), getFieldYOnScreen(player2.getY()), fieldWidth, fieldHeight);
	}
	
	public boolean isGameOver() {
		return isPlayerCrashed(player1) || isPlayerCrashed(player2);
	}
	
	public Winner getWinner() {
		boolean player1Crashed = isPlayerCrashed(player1);
		boolean player2Crashed = isPlayerCrashed(player2);
		if (player1Crashed && player2Crashed) {
			return Winner.NONE;
		}
		else if (player1Crashed) {
			return Winner.PLAYER_2;
		}
		else if (player2Crashed) {
			return Winner.PLAYER_1;
		}
		throw new IllegalStateException("The game is not over yet");
	}
	
	private boolean isPlayerCrashed(TronRacer player) {
		return hasLeftField(player) || field[player.getY()][player.getX()] != FieldType.EMPTY;
	}
	
	public boolean hasLeftField(TronRacer player) {
		return player.getX() < 0 || player.getX() >= getFieldSizeX() || player.getY() < 0 || player.getY() >= getFieldSizeY();
	}
	
	public void dispose() {
		shapeRenderer.dispose();
	}
	
	public float getFieldXOnScreen(int xCoordinate) {
		return fieldWidth * (borderFields + xCoordinate);
	}
	public float getFieldYOnScreen(int yCoordinate) {
		return fieldHeight * (borderFields + yCoordinate);
	}
	
	public int getFieldSizeX() {
		return numFieldsX;
	}
	public int getFieldSizeY() {
		return numFieldsX;
	}
}
