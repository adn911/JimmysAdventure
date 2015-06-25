package galib.platformer.GameObjects;

import galib.platformer.gamestates.GameplayState;
import galib.platformer.main.Canvas;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;

import resources.ResourceLoader;

public class Tile extends GameObject {

	private Image tileImage;
	private Color color = Color.BLACK;

	public Tile(float x, float y) {
		super(x, y);

		this.width = Canvas.UNIT_WIDTH;
		this.height = Canvas.UNIT_HEIGHT;

		this.tileImage = ResourceLoader.tile;

	}

	@Override
	public void init() {
	}

	@Override
	public void update(long deltaTime, ArrayList<GameObject> objects) {

		super.update(deltaTime, objects);

	}

	@Override
	public void draw(Graphics2D g) {

		g.drawImage(tileImage, (int) (x - GameplayState.screenOffsetX),
				(int) (y - GameplayState.screenOffsetY), null);

	}

}
