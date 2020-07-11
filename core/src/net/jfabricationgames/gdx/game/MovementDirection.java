package net.jfabricationgames.gdx.game;

public enum MovementDirection {
	
	UP(0, 1), //
	DOWN(0, -1), //
	LEFT(-1, 0), //
	RIGHT(1, 0);
	
	public final int offsetX;
	public final int offsetY;
	
	public static MovementDirection oppositeOf(MovementDirection direction) {
		switch (direction) {
			case DOWN:
				return UP;
			case LEFT:
				return RIGHT;
			case RIGHT:
				return LEFT;
			case UP:
				return DOWN;
			default:
				throw new IllegalStateException("Unknown direction: " + direction);
		}
	}
	
	private MovementDirection(int offsetX, int offsetY) {
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}
}
