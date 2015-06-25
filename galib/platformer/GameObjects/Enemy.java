package galib.platformer.GameObjects;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class Enemy extends GameObject {

	protected float maxDistance;
	protected float startX, startY;

	public Enemy(float positionX, float positionY) {

		super(positionX, positionY);

		startX = positionX;
		startY = positionY;

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

	}

}
