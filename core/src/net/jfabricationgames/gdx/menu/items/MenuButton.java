package net.jfabricationgames.gdx.menu.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class MenuButton {
	
	private BitmapFont font;
	
	private Runnable onClick;
	
	private Vector2 position;
	private Vector2 size;
	private Vector2 center;
	private String text;
	
	private float borderSize = 5f;
	private float textPadding = 50f;
	
	private Color borderColor = Color.MAROON;
	private Color textColor = Color.LIGHT_GRAY;
	private Color screenBgColor = Color.BLACK;
	private Color highlightedBgColor = Color.FIREBRICK;
	
	public MenuButton(Vector2 lowerLeftEdge, Vector2 size, String text, Runnable onClick) {
		this.position = lowerLeftEdge;
		this.size = size;
		this.text = text;
		this.onClick = onClick;
		
		font = new BitmapFont();
		font.getData().setScale(1.5f);
		
		center = new Vector2(position.x + (size.x / 2f), position.y + (size.y / 2f));
	}
	
	public void handleClickEvent() {
		if (isMouseHovering() && isMouseClicked()) {
			onClick.run();
		}
	}
	
	public void renderButtonBackground(ShapeRenderer shapeRenderer) {
		//draw the background
		shapeRenderer.setColor(borderColor);
		shapeRenderer.rect(position.x, position.y, size.x, size.y);
		if (isMouseHovering()) {
			shapeRenderer.setColor(highlightedBgColor);
		}
		else {
			shapeRenderer.setColor(screenBgColor);
		}
		shapeRenderer.rect(position.x + borderSize, position.y + borderSize, size.x - 2 * borderSize, size.y - 2 * borderSize);
	}
	
	public void renderButtonText(SpriteBatch batch) {
		final GlyphLayout layout = new GlyphLayout(font, text);
		
		//draw the text
		batch.setColor(textColor);
		font.draw(batch, layout, center.x - layout.width / 2, center.y + layout.height / 2);
	}
	
	private boolean isMouseHovering() {
		//read the mouse position
		final Vector2 cursorPosition = new Vector2(Gdx.input.getX(), Gdx.input.getY());
		
		//check whether the mouse is hovering over this button
		return position.x <= cursorPosition.x && position.x + size.x >= cursorPosition.x //
				&& position.y <= cursorPosition.y && position.y + size.y >= cursorPosition.y;
	}
	
	private boolean isMouseClicked() {
		return Gdx.input.isButtonPressed(Input.Buttons.LEFT);
	}
	
	public Color getBorderColor() {
		return borderColor;
	}
	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}
	
	public Color getTextColor() {
		return textColor;
	}
	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}
	
	public Color getScreenBgColor() {
		return screenBgColor;
	}
	public void setScreenBgColor(Color screenBgColor) {
		this.screenBgColor = screenBgColor;
	}
	
	public Color getHighlightedBgColor() {
		return highlightedBgColor;
	}
	public void setHighlightedBgColor(Color highlightedBgColor) {
		this.highlightedBgColor = highlightedBgColor;
	}
	
	public float getBorderSize() {
		return borderSize;
	}
	public void setBorderSize(float borderSize) {
		this.borderSize = borderSize;
	}
	
	public float getTextPadding() {
		return textPadding;
	}
	public void setTextPadding(float textPadding) {
		this.textPadding = textPadding;
	}
}
