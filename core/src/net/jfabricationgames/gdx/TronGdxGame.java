package net.jfabricationgames.gdx;

import com.badlogic.gdx.Game;

public class TronGdxGame extends Game {
	
	private static TronGdxGame instance;
	
	public static synchronized TronGdxGame getInstance() {
		if (instance == null) {
			instance = new TronGdxGame();
		}
		return instance;
	}
	
	private	TronGdxGame() {
		
	}
	
	@Override
	public void create() {
		setScreen(new GameScreen());
	}
}
