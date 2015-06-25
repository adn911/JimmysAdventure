package galib.platformer.GameObjects;

import galib.platformer.animation.Animation;
import galib.platformer.animation.AnimationSet;
import galib.platformer.gamestates.GameplayState;
import galib.platformer.main.Canvas;

import java.awt.Graphics2D;
import java.util.ArrayList;

import resources.ResourceLoader;

public class Fireball extends GameObject {

	public Fireball(float positionX, float positionY, float speedX) {

		super(positionX, positionY);

		width = Canvas.UNIT_WIDTH / 4;
		height = Canvas.UNIT_HEIGHT / 3;

		this.speedX = speedX;

		init();

		state = State.IDLE;
	}

	@Override
	public void init() {

		animationSet = new AnimationSet();

		Animation idle = new Animation();

		// fire.addFrames(ResourceLoader.fireball_sheet, 4, 8, 40);
		idle.addFrame(ResourceLoader.fireball_01, 50);
		idle.addFrame(ResourceLoader.fireball_02, 50);
		idle.addFrame(ResourceLoader.fireball_03, 50);
		idle.addFrame(ResourceLoader.fireball_04, 50);
		idle.addFrame(ResourceLoader.fireball_05, 50);
		idle.addFrame(ResourceLoader.fireball_06, 50);
		idle.addFrame(ResourceLoader.fireball_07, 50);
		idle.addFrame(ResourceLoader.fireball_08, 50);

		animationSet.addAnimation(State.IDLE.toString(), idle);

	}

	@Override
	public void update(long deltaTime, ArrayList<GameObject> objects) {

		super.update(deltaTime, objects);

		x += speedX;

		if (x < GameplayState.screenOffsetX
				|| x > GameplayState.screenOffsetX + Canvas.WIDTH) {
			destroy();
		}

	}

	@Override
	public void draw(Graphics2D g) {

		if (speedX >= 0)
			g.drawImage(animationSet.get(state.toString()).getImage(),
					(int) (x - GameplayState.screenOffsetX),
					(int) (y - GameplayState.screenOffsetY), null);

		else
			g.drawImage(
					animationSet.get(state.toString()).getMirroredImage(
							(int) width, (int) height),
					(int) (x - GameplayState.screenOffsetX),
					(int) (y - GameplayState.screenOffsetY), null);
	}

}