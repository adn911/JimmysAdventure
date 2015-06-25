package galib.platformer.GameObjects;

import galib.platformer.animation.AnimationSet;
import galib.platformer.gamestates.GameplayState;
import galib.platformer.main.Canvas;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public abstract class GameObject extends Rectangle2D.Float {


	protected enum State {
		IDLE, WALK, JUMP, ATTACK, DIE
	};

	protected float speedX, speedY;
	protected boolean isVisible;
	protected boolean isAlive;
	protected State state;

	protected AnimationSet animationSet;

	protected int direction;

	public GameObject(float positionX, float positionY) {

		this.x = positionX;
		this.y = positionY;
		animationSet = new AnimationSet();
		isVisible = true;
		isAlive = true;
		direction = 1;

		state = State.IDLE;
	}

	// abstract methods to be overridden

	public abstract void init();

	public void update(long deltaTime, ArrayList<GameObject> objects) {

		if (x >= GameplayState.screenOffsetX - Canvas.UNIT_WIDTH
				&& x <= GameplayState.screenOffsetX + Canvas.WIDTH
						+ Canvas.UNIT_WIDTH && y >= GameplayState.screenOffsetY
				&& y <= GameplayState.screenOffsetY + Canvas.HEIGHT) {

			isVisible = true;

		} else {

			isVisible = false;
		}

	}

	public abstract void draw(Graphics2D g);

	// Getters and Setters

	public boolean isVisible() {

		return isVisible;
	}

	public void setVisible(boolean isVisible) {

		this.isVisible = isVisible;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public void destroy() {
		isAlive = false;
		isVisible = false;
	}


	public float getSpeedX() {
		return speedX;
	}

	public void setSpeedX(float speedX) {
		this.speedX = speedX;
	}

	public float getSpeedY() {
		return speedY;
	}

	public void setSpeedY(float speedY) {
		this.speedY = speedY;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public AnimationSet getAnimationSet() {
		return animationSet;
	}

	public void setAnimationSet(AnimationSet animationSet) {
		this.animationSet = animationSet;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

}
