package net.jfabricationgames.gdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;

public class TronRacer {
	
	public static final Color COLOR_PLAYER_1 = new Color(1, 0, 0, 1);
	public static final Color COLOR_PLAYER_2 = new Color(0, 0, 1, 1);
	public static final float movementsPerSecond = 15f;
	
	public static final MovementKey[] MOVEMENT_KEYS_PLAYER_1 = new MovementKey[] {//
			new MovementKey(Input.Keys.W, MovementDirection.UP), //
			new MovementKey(Input.Keys.S, MovementDirection.DOWN), //
			new MovementKey(Input.Keys.A, MovementDirection.LEFT), //
			new MovementKey(Input.Keys.D, MovementDirection.RIGHT)};
	
	public static final MovementKey[] MOVEMENT_KEYS_PLAYER_2 = new MovementKey[] {//
			new MovementKey(Input.Keys.UP, MovementDirection.UP), //
			new MovementKey(Input.Keys.DOWN, MovementDirection.DOWN), //
			new MovementKey(Input.Keys.LEFT, MovementDirection.LEFT), //
			new MovementKey(Input.Keys.RIGHT, MovementDirection.RIGHT)};
	
	private static final MovementKey[][] MOVMENT_KEYS = new MovementKey[][] {MOVEMENT_KEYS_PLAYER_1, MOVEMENT_KEYS_PLAYER_2};
	
	private int player;
	private int x;
	private int y;
	private MovementDirection direction;
	
	private boolean hasMoved;
	
	private float movementTimeOffset;
	private float timeOffset;
	
	public static class MovementKey {
		
		private int key;
		private MovementDirection direction;
		
		public MovementKey(int key, MovementDirection direction) {
			this.key = key;
			this.direction = direction;
		}
		
		public int getKey() {
			return key;
		}
		
		public MovementDirection getDirection() {
			return direction;
		}
	}
	
	public TronRacer(int player, int initialX, int initialY, MovementDirection initialDirection) {
		if (player < 1 || player > 2) {
			throw new IllegalArgumentException("Unsuported player id (" + player + "). Choose either 1 or 2");
		}
		this.player = player;
		this.x = initialX;
		this.y = initialY;
		this.direction = initialDirection;
		
		movementTimeOffset = 1f / movementsPerSecond;
	}
	
	public void updateMovement(float delta) {
		timeOffset += delta;
		hasMoved = false;
		if (timeOffset >= movementTimeOffset) {
			move();
			timeOffset -= movementTimeOffset;
		}
	}
	
	private void move() {
		checkForDirectionChange();
		x += direction.offsetX;
		y += direction.offsetY;
		hasMoved = true;
	}
	
	public boolean hasMoved() {
		return hasMoved;
	}
	
	private void checkForDirectionChange() {
		for (MovementKey key : MOVMENT_KEYS[player - 1]) {
			if (Gdx.input.isKeyPressed(key.getKey())) {
				if (key.getDirection() != MovementDirection.oppositeOf(direction)) {
					direction = key.getDirection();
					//only one direction change is possible (otherwise it would be possible to change to the opposite direction)
					return;
				}
			}
		}
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
}
