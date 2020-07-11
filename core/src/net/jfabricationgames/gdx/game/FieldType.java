package net.jfabricationgames.gdx.game;

import com.badlogic.gdx.graphics.Color;

public enum FieldType {
	
	EMPTY(Color.BLACK), //
	PLAYER_1(new Color(TronRacer.COLOR_PLAYER_1.r, TronRacer.COLOR_PLAYER_1.g, TronRacer.COLOR_PLAYER_1.b, 0.8f)), //
	PLAYER_2(new Color(TronRacer.COLOR_PLAYER_2.r, TronRacer.COLOR_PLAYER_2.g, TronRacer.COLOR_PLAYER_2.b, 0.8f));
	
	private final Color fieldColor;
	
	private FieldType(Color fieldColor) {
		this.fieldColor = fieldColor;
	}
	
	public Color getFieldColor() {
		return fieldColor;
	}
}
