package galib.platformer.GameObjects;

import galib.platformer.animation.Animation;

import galib.platformer.gamestates.GameplayState;
import galib.platformer.input.Input;
import galib.platformer.main.Canvas;


import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import galib.platformer.sound.SoundEffects;
import resources.ResourceLoader;

public class Player extends GameObject {

	private final float SCREEN_POSITION_X = Canvas.WIDTH / 5;
	private final float SCREEN_POSITION_Y = Canvas.HEIGHT * 3.5f / 5;

	private final float ACCELARATION = Canvas.UNIT_WIDTH / 200.0f;
	private final float GRAVITY = Canvas.UNIT_HEIGHT / 115.0f;
	private final float FRICTION = Canvas.UNIT_WIDTH / 270.0f;

	private final float MAX_MOVE_SPEED = Canvas.UNIT_WIDTH / 15f;
	private final float JUMP_SPEED = Canvas.UNIT_HEIGHT / 5f;

	private boolean blockDown, blockUp, blockRight, blockLeft;

	private State state;

	public int points, gemsTotal;

	// constructor
	public Player(float x, float y) {
		super(x, y);

		this.width = Canvas.UNIT_WIDTH / 2;
		this.height = Canvas.UNIT_HEIGHT - 2;

		initAnimations();

	}

	private void initAnimations() {

		Animation wakingUp = new Animation();

		wakingUp.addFrame(ResourceLoader.player_wakingup_01, 2000);
		wakingUp.addFrame(ResourceLoader.player_wakingup_02, 500);
		wakingUp.addFrame(ResourceLoader.player_wakingup_03, 500);
		wakingUp.addFrame(ResourceLoader.player_wakingup_02, 500);
		wakingUp.addFrame(ResourceLoader.player_wakingup_03, 500);
		wakingUp.addFrame(ResourceLoader.player_wakingup_02, 500);
		wakingUp.addFrame(ResourceLoader.player_wakingup_03, 500);
		wakingUp.addFrame(ResourceLoader.player_wakingup_04, 200);

		wakingUp.setLooping(false);

		Animation idle = new Animation();

		idle.addFrame(ResourceLoader.player_idle, 100);
		idle.setLooping(true);

		Animation walk = new Animation();

		// walk.addFrame(ResourceLoader.player_walking_01, 60);
		walk.addFrame(ResourceLoader.player_walking_02, 100);
		walk.addFrame(ResourceLoader.player_walking_01, 100);
		walk.addFrame(ResourceLoader.player_walking_03, 100);
		walk.addFrame(ResourceLoader.player_walking_04, 100);

		walk.setLooping(true);

		Animation jump = new Animation();

		jump.addFrame(ResourceLoader.player_jumping, 100);
		jump.addFrame(ResourceLoader.player_jumping, 100);
		jump.setLooping(true);

		Animation die = new Animation();

		die.addFrame(ResourceLoader.player_dying_01, 500);
		die.addFrame(ResourceLoader.player_dying_02, 500);
		die.setLooping(false);

		animationSet.addAnimation(State.WAKING_UP.toString(), wakingUp);
		animationSet.addAnimation(State.IDLE.toString(), idle);
		animationSet.addAnimation(State.WALK.toString(), walk);
		animationSet.addAnimation(State.JUMP.toString(), jump);
		animationSet.addAnimation(State.DIE.toString(), die);

		state = State.WAKING_UP;
	}

	@Override
	public void init() {

		this.x = 0;
		this.y = 0;

		this.speedX = 0;
		this.speedY = 0;

		points = 0;
		gemsTotal = 0;

		blockUp = blockDown = blockLeft = blockRight = false;

		state = State.WAKING_UP;
	}

	@Override
	public void update(long deltaTime, ArrayList<GameObject> objects) {
		// Check Key press

		if (isAlive && state != State.DIE && state != State.WAKING_UP) {

			if (Input.key[KeyEvent.VK_RIGHT] == true
					&& speedX + ACCELARATION <= MAX_MOVE_SPEED)
				speedX += ACCELARATION;

			else if (Input.key[KeyEvent.VK_LEFT] == true
					&& speedX - ACCELARATION >= -MAX_MOVE_SPEED)
				speedX -= ACCELARATION;

			if (Math.abs(speedX) >= FRICTION) {

				state = State.WALK;

				if (speedX >= FRICTION) {

					direction = 1;
					speedX -= FRICTION;

				} else if (speedX <= -FRICTION) {

					direction = -1;
					speedX += FRICTION;

				}

			} else {

				speedX = 0;
				state = State.IDLE;

			}

			if (Input.key[KeyEvent.VK_UP] == true && blockDown) {

//				GameplayState.soundEffects.init();

 				GameplayState.soundEffects.audioClips.get("jump_sound").loop(1);

				blockDown = false;

				animationSet.get(State.JUMP.toString()).init();

				speedY = -JUMP_SPEED;

			}

			if (blockDown == false) {
				speedY += GRAVITY;

				if (Math.abs(speedY) > 1) {
					state = State.JUMP;
					// currentAnimation = "jump";
				}

			}

			checkCollision(objects);

			if ((blockLeft && speedX < 0) || (blockRight && speedX > 0)) {
				speedX = 0;

			}

			if ((blockDown && speedY > 0) || (blockUp && speedY < 0)) {
				speedY = 0;
			}

			if (x + speedX >= 0)
				x += speedX;

			y += speedY;

		}

		if (state == State.WAKING_UP
				&& animationSet.get(state.toString()).animComplete) {
			state = State.IDLE;
		}

		if ((state == State.DIE && animationSet.get(state.toString()).animComplete)
				|| (y >= Canvas.HEIGHT * 2)) {
			destroy();
		}

		GameplayState.screenOffsetX = x - SCREEN_POSITION_X;

		// GameplayState.screenOffsetY = y - SCREEN_POSITION_Y;

		System.out.println(state);

		animationSet.get(state.toString()).update(deltaTime);

	}

	public void checkCollision(ArrayList<GameObject> objects) {

		blockUp = false;
		blockDown = false;
		blockLeft = false;
		blockRight = false;

		Rectangle2D upadtedBound = (Rectangle2D) this.getBounds2D().clone();

		for (int i = 0; i < objects.size(); i++) {

			if (objects.get(i).getClass() == Gems.class) {
				Gems gems = (Gems) objects.get(i);

				if (gems.isVisible && this.intersects(gems)) {

					GameplayState.soundEffects.audioClips.get("gems_sound").loop(1);

					gems.setAlive(false);

					gemsTotal++;

					points += 50;

				}

			}

			else if ((objects.get(i) instanceof Enemy || objects.get(i) instanceof Fireball)
					&& objects.get(i).isVisible) {

				if (this.intersects(objects.get(i))) {

					if (speedY > 0
							&& y + height <= objects.get(i).y
									+ objects.get(i).height / 3) {

						GameplayState.soundEffects.audioClips.get(
								"destroy_sound").loop(1);

						System.out.println("Kill Enemy!");

						speedY *= -0.5f;

						objects.get(i).destroy();

						points += 100;

					} else {

						if (state != State.DIE) {

							state = State.DIE;

							if (objects.get(i) instanceof Fireball) {
								objects.get(i).destroy();
							}

							animationSet.get(state.toString()).init();
						}

					}

				}

			}

			else if (objects.get(i).getClass() == Tile.class) {

				Tile tile = (Tile) objects.get(i);

				if (speedX > 0) {
					// its moving right ; check for any blocks to the right

					upadtedBound.setRect(x + speedX + width, y + GRAVITY, 1,
							height);

					if (upadtedBound.intersects(tile))
						blockRight = true;

					// System.out.print(blockRight+" ");
				}

				if (speedX < 0) {

					upadtedBound.setRect(x + speedX, y + GRAVITY, 1, height);
					if (upadtedBound.intersects(tile))
						blockLeft = true;

					// System.out.print(blockLeft+" ");
				}

				if (speedY > 0) {

					upadtedBound.setRect(x, y + speedY + GRAVITY + height - 1,
							width, 1);
					if (upadtedBound.intersects(tile))
						blockDown = true;

					// System.out.print(""blockDown+" ");
				}

				if (speedY < 0) {

					upadtedBound.setRect(x, y + speedY + GRAVITY + 1, width, 1);
					if (upadtedBound.intersects(tile))
						blockUp = true;

					// System.out.print(blockUp+" ");
				}

			}

		}

		// System.out.println(blockDown+" "+blockUp+" "+blockLeft+" "+blockRight);

	}

	@Override
	public void draw(Graphics2D g) {

		if (animationSet.get(state.toString()) != null) {

			if (direction == 1)
				g.drawImage(animationSet.get(state.toString()).getImage(),
						(int) (x - GameplayState.screenOffsetX),
						(int) (y - GameplayState.screenOffsetY), null);

			else {

				g.drawImage(animationSet.get(state.toString())
						.getMirroredImage((int) width, (int) height),
						(int) (x - GameplayState.screenOffsetX),
						(int) (y - GameplayState.screenOffsetY), null);

			}

		}

	}

	private enum State {
		IDLE, WALK, JUMP, ATTACK, DIE, WAKING_UP
	};

}
