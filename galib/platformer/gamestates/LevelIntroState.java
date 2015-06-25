package galib.platformer.gamestates;

import galib.platformer.main.Canvas;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class LevelIntroState extends GameState {

	private String title = "LEVEL 01";

	private String title2 = "THE HUNTED FOREST";

	private Font titleFont = new Font("Georgia", Font.BOLD, 66);

	private Font title2Font = new Font("Georgia", Font.PLAIN, 36);

	private Color titleColor = Color.WHITE;

	private Color title2Color = new Color(0.2f, 0.4f, 0.6f);

	private Color backgroundColor = new Color(0f, 0f, 0f);

	private long totalTime = 2000;

	private long elapsedTime = 0;

	private float alpha = 1.0f;

	public LevelIntroState(GameStateManager gsm) {

		super(gsm);

		init();

	}

	@Override
	public void init() {
	}

	@Override
	public void update(long deltaTime) {

		// System.out.println(deltaTime);

		elapsedTime += deltaTime;

		// System.out.println(elapsedTime);

		if (elapsedTime >= totalTime) {

			alpha -= 0.005f;

			if (alpha < 0.6) {

				gsm.states.pop();
				gsm.states.push(new GameplayState(gsm));
			}

		}

	}

	@Override
	public void draw(Graphics2D g) {

		g.setColor(backgroundColor);

		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				alpha));

		g.fillRect(0, 0, Canvas.WIDTH, Canvas.HEIGHT);

		FontMetrics metrics = g.getFontMetrics(titleFont);

		g.setColor(titleColor);
		g.setFont(titleFont);

		g.drawString(title, (Canvas.WIDTH - metrics.stringWidth(title)) / 2,
				(Canvas.HEIGHT - metrics.getHeight()) / 2);

		metrics = g.getFontMetrics(title2Font);

		g.setColor(title2Color);
		g.setFont(title2Font);

		g.drawString(title2, (Canvas.WIDTH - metrics.stringWidth(title2)) / 2,
				(Canvas.HEIGHT - metrics.getHeight()) * 3 / 5);

		/*
		 * metrics = g.getFontMetrics(footerFont);
		 * 
		 * g.setFont(footerFont); g.setColor(footerColor);
		 * 
		 * g.drawString(footer, (Canvas.WIDTH - metrics.stringWidth(footer)) /
		 * 2, (Canvas.HEIGHT - metrics.getHeight()) * 4 / 5);
		 */

	}

	@Override
	public void keyPressed(int k) {

		if (k == KeyEvent.VK_ENTER) {

			GameplayState gamePlayState = new GameplayState(gsm);
			gsm.states.pop();
			gsm.states.push(gamePlayState);

		}

	}

	@Override
	public void keyReleased(int k) {

	}

}
