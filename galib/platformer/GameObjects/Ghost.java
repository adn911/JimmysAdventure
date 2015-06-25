package galib.platformer.GameObjects;

import galib.platformer.animation.Animation;
import galib.platformer.gamestates.GameplayState;
import galib.platformer.main.Canvas;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import resources.ResourceLoader;

public class Ghost extends Enemy {

	private long stateTime;
	private float alpha = 0.5f;

	public Ghost(float positionX, float positionY) {

		super(positionX, positionY);

		width = Canvas.UNIT_WIDTH / 2;
		height = Canvas.UNIT_HEIGHT;

		speedX = 0.8f;
		
		maxDistance = Canvas.UNIT_WIDTH * 2;

		if (new Random().nextInt() % 2 == 1)
			speedX *= -1;

		init();

	}

	@Override
	public void init() {

		super.init();

		Animation walk = new Animation();

		walk.addFrame(ResourceLoader.ghost_walking_01, 40);
		walk.addFrame(ResourceLoader.ghost_walking_02, 40);
		walk.addFrame(ResourceLoader.ghost_walking_03, 40);
		walk.addFrame(ResourceLoader.ghost_walking_04, 40);
		walk.addFrame(ResourceLoader.ghost_walking_05, 40);
		walk.addFrame(ResourceLoader.ghost_walking_06, 40);

		walk.addFrame(ResourceLoader.ghost_walking_07, 40);
		walk.addFrame(ResourceLoader.ghost_walking_08, 40);
		walk.addFrame(ResourceLoader.ghost_walking_09, 40);
		walk.addFrame(ResourceLoader.ghost_walking_10, 40);

		walk.addFrame(ResourceLoader.ghost_walking_11, 40);
		walk.addFrame(ResourceLoader.ghost_walking_12, 40);
		walk.addFrame(ResourceLoader.ghost_walking_13, 40);
		walk.addFrame(ResourceLoader.ghost_walking_14, 40);
		walk.addFrame(ResourceLoader.ghost_walking_15, 40);
		walk.addFrame(ResourceLoader.ghost_walking_16, 40);

		walk.addFrame(ResourceLoader.ghost_walking_17, 40);
		walk.addFrame(ResourceLoader.ghost_walking_18, 40);
		walk.addFrame(ResourceLoader.ghost_walking_19, 40);
		walk.addFrame(ResourceLoader.ghost_walking_20, 40);

		walk.setLooping(true);

		animationSet.addAnimation(State.WALK.toString(), walk);

		state = State.WALK;
	}

	@Override
	public void update(long deltaTime, ArrayList<GameObject> objects) {

		// super.update(deltaTime, objects);

		animationSet.get(state.toString()).update(deltaTime);

		stateTime += deltaTime;

		// System.out.println("Statetime "+stateTime);

		if ( stateTime >= nextState ) {
			// System.out.println("Vampire "+state.ordinal());

			if (isVisible) {
	
				isVisible = false;
				nextState = 2000;
			
			} else {
				
				isVisible = true;
				nextState = 4000;
				
			}

			stateTime = 0;

		}

		x += speedX;

		if (Math.abs(x - startX) >= maxDistance) {
			speedX *= -1;
		}

	}

	@Override
	public void draw(Graphics2D g) {

		super.draw(g);

		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				alpha));

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

		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

	}

	enum State { WALK };
	
	
	
	private int nextState; 

	private int visibilityState;
	
	private State state;

}
