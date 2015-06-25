package galib.platformer.gamestates;

import java.awt.Graphics2D;

public abstract class GameState {

	protected GameStateManager gsm;

	public GameState(GameStateManager gsm) {
		this.gsm = gsm;
		init();

	}

	public abstract void init();

	public abstract void update(long deltaTime);

	public abstract void draw(Graphics2D g);

	public abstract void keyPressed(int k);

	public abstract void keyReleased(int k);
}
