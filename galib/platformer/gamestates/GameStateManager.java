package galib.platformer.gamestates;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Stack;

public class GameStateManager {

	public Stack<GameState> states;

	public GameStateManager() {
		states = new Stack<GameState>();
		states.push(new SplashscreenState(this));
	}

	public void update(long deltaTime) {
		if (!states.isEmpty())
			states.peek().update(deltaTime);
	}

	public void draw(Graphics g) {
		if (!states.isEmpty())
			states.peek().draw((Graphics2D) g);
	}

	public void keyPressed(int k) {
		if (!states.isEmpty())
			states.peek().keyPressed(k);
	}

	public void keyReleased(int k) {
		if (!states.isEmpty())
			states.peek().keyReleased(k);
	}

}
