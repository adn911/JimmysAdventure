package galib.platformer.GameObjects;

import galib.platformer.animation.Animation;
import galib.platformer.gamestates.GameplayState;
import galib.platformer.main.Canvas;

import java.awt.Graphics2D;
import java.util.ArrayList;

import resources.ResourceLoader;

public class Mommy extends Enemy {

	protected long lastAttackTime;

	public Mommy(float positionX, float positionY) {

		super(positionX, positionY);

		width = Canvas.UNIT_WIDTH / 2;
		height = Canvas.UNIT_HEIGHT;

		speedX = 1f;

		maxDistance = Canvas.UNIT_WIDTH * 2;

		init();
	}

	@Override
	public void init() {

		super.init();

		Animation walk = new Animation();

		walk.addFrame(ResourceLoader.mommy_walking_01, 200);
		walk.addFrame(ResourceLoader.mommy_walking_02, 200);
		walk.addFrame(ResourceLoader.mommy_walking_03, 200);
		walk.addFrame(ResourceLoader.mommy_walking_04, 200);
		walk.addFrame(ResourceLoader.mommy_walking_05, 200);
		walk.addFrame(ResourceLoader.mommy_walking_06, 200);

		walk.setLooping(true);

		animationSet.addAnimation(State.WALK.toString(), walk);

		state = State.WALK;

		lastAttackTime = System.currentTimeMillis();
	}

	@Override
	public void update(long deltaTime, ArrayList<GameObject> objects) {

		super.update(deltaTime, objects);

		animationSet.get(state.toString()).update(deltaTime);

		x += speedX;

		if (Math.abs(x - startX) >= maxDistance) {
			speedX *= -1;
		}

		if (System.currentTimeMillis() - lastAttackTime >= 2000)
			for (int i = 0; i < objects.size(); i++) {
				if (objects.get(i) instanceof Player) {

					Player player = (Player) objects.get(i);

					// System.out.println("FOUND PLAYER");

					// check if the player is front of you if so then fire
					// fireball
					if (player.getY() + height >= y
							&& player.getY() <= y + height
							&& Math.abs(player.getX() - x) < Canvas.UNIT_WIDTH * 3
							&& Math.abs(player.getX() - x) > Canvas.UNIT_WIDTH / 2
							&& ((player.getX() < x && speedX < 0) || (player
									.getX() > x && speedX > 0))) {

						System.out.println("FIRE");
						objects.add(new Fireball(x - 2, y, speedX * 3));
						lastAttackTime = System.currentTimeMillis();

					}

				}

			}

	}

	@Override
	public void draw(Graphics2D g) {

		super.draw(g);

		if (speedX < 0)
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
