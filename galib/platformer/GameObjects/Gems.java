package galib.platformer.GameObjects;

import galib.platformer.gamestates.GameplayState;
import galib.platformer.main.Canvas;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;

import resources.ResourceLoader;

public class Gems extends GameObject {

	Image gemsImageSheet;
	int gemsColor;

	public Gems(float positionX, float positionY) {

		super(positionX, positionY);

		width = Canvas.UNIT_WIDTH / 3;
		height = Canvas.UNIT_HEIGHT / 3;

		gemsImageSheet = ResourceLoader.gems;

		gemsColor = (new Random().nextInt(5));

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

		g.drawImage(gemsImageSheet, (int) (x - GameplayState.screenOffsetX),
				(int) (y - GameplayState.screenOffsetY), (int) (x
						- GameplayState.screenOffsetX + width), (int) (y
						- GameplayState.screenOffsetY + height),
				(int) (gemsColor * width), 0,
				(int) (gemsColor * width + width), (int) height, null);

	}

}
