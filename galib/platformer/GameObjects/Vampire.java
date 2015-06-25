package galib.platformer.GameObjects;

import galib.platformer.animation.Animation;
import galib.platformer.gamestates.GameplayState;
import galib.platformer.main.Canvas;

import java.awt.Graphics2D;
import java.util.ArrayList;

import resources.ResourceLoader;

public class Vampire extends Enemy {

	protected long stateTime;
	private State state;

	public Vampire(float positionX, float positionY) {

		super(positionX, positionY);

		width = Canvas.UNIT_WIDTH / 2;
		height = Canvas.UNIT_HEIGHT;

		speedX = 2f;

		maxDistance = Canvas.UNIT_WIDTH * 2;

		init();
	}

	@Override
	public void init() {

		super.init();

		Animation run = new Animation();

		run.addFramesFromSpriteSheet(ResourceLoader.vampire_running, 1, 4, 400);
		run.setLooping(true);

		animationSet.addAnimation(State.RUN.toString(), run);

		Animation idle = new Animation();

		idle.addFramesFromSpriteSheet(ResourceLoader.vampire_idle, 1, 4, 400);
		idle.setLooping(true);

		animationSet.addAnimation(State.IDLE.toString(), idle);

		Animation attack = new Animation();

		attack.addFramesFromSpriteSheet(ResourceLoader.vampire_attack, 1, 4, 400);
		attack.setLooping(true);

		animationSet.addAnimation(State.ATTACK.toString(), attack);

		state = State.IDLE;

		stateTime = 0;
	}

	@Override
	public void update(long deltaTime, ArrayList<GameObject> objects) {

		super.update(deltaTime, objects);

		stateTime += deltaTime;

		// System.out.println("Statetime "+stateTime);

		if (stateTime >= 1000) {
			// System.out.println("Vampire "+state.ordinal());

			setState((state.ordinal() + 1) % 3);

			stateTime = 0;

		}

		animationSet.get(state.toString()).update(deltaTime);

		if (state == State.RUN)
			x += speedX;

		if (Math.abs(x - startX) >= maxDistance) {
			speedX *= -1;
		}

	}

	@Override
	public void draw(Graphics2D g) {

		super.draw(g);

		if (speedX > 0)
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

	public void setState(int s) {
		// System.out.println("Vampire "+s);

		if (s == 0)
			state = State.IDLE;

		else if (s == 1)
			state = State.ATTACK;

		else if (s == 2)
			state = State.RUN;
	}

	enum State {
		IDLE, ATTACK, RUN;
	}

}
