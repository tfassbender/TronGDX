package net.jfabricationgames.gdx.menu.items;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public abstract class AbstractMenuTextItem {
	
	protected BitmapFont font;
	
	protected Vector2 position;
	protected Vector2 size;
	protected Vector2 center;
	protected String text;
	
	protected Color textColor = Color.LIGHT_GRAY;
	protected Color bgColor = Color.FIREBRICK;
	
	public AbstractMenuTextItem(Vector2 lowerLeftEdge, Vector2 size, String text, float textScale) {
		this.position = lowerLeftEdge;
		this.size = size;
		this.text = text;
		
		font = new BitmapFont();
		font.getData().setScale(textScale);
		
		center = new Vector2(position.x + (size.x / 2f), position.y + (size.y / 2f));
	}
	
	public void renderBackground(ShapeRenderer shapeRenderer) {
		//draw the background
		shapeRenderer.setColor(bgColor);
		shapeRenderer.rect(position.x, position.y, size.x, size.y);
	}
	
	public void renderText(SpriteBatch batch) {
		final GlyphLayout layout = new GlyphLayout(font, text);
		
		//draw the text
		batch.setColor(textColor);
		font.draw(batch, layout, center.x - layout.width / 2, center.y + layout.height / 2);
	}
	
	public void dispose() {
		font.dispose();
	}
	
	public void setTextScale(float textScale) {
		this.font.getData().setScale(textScale);
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public Color getTextColor() {
		return textColor;
	}
	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}
	
	public Color getBgColor() {
		return bgColor;
	}
	public void setBgColor(Color bgColor) {
		this.bgColor = bgColor;
	}
}
