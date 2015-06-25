package galib.platformer.gamestates;

import galib.platformer.main.Canvas;

import java.awt.*;
import java.awt.event.KeyEvent;

public class AboutGameState extends GameState {

	private String title = "About This Game";

	private String about = "This is just a fun project, \n made by Md. Bakhtiar Galib@2014 (galib.adnan911@gmail.com) ";

	private Font titleFont = new Font("Georgia", Font.BOLD, 66);

	private Font aboutFont = new Font("Georgia", Font.PLAIN, 22);

	private Color titleColor = Color.WHITE;

	private Color aboutColor = new Color(0.2f, 0.4f, 0.6f);

	private Color backgroundColor = new Color(0f, 0f, 0f);

	private float alpha = 1f;

	public AboutGameState(GameStateManager gsm) {

		super(gsm);

		init();

	}

	@Override
	public void init() {
	}

	@Override
	public void update(long deltaTime) {

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

		metrics = g.getFontMetrics(aboutFont);

		g.setColor(aboutColor);
		g.setFont(aboutFont);

		g.drawString(about, (Canvas.WIDTH - metrics.stringWidth(about)) / 2,
				(Canvas.HEIGHT - metrics.getHeight()) * 3 / 5);
	}

	@Override
	public void keyPressed(int k) {

		if (k == KeyEvent.VK_ESCAPE
				|| k == KeyEvent.VK_ENTER) {

			gsm.states.pop();

		}

	}

	@Override
	public void keyReleased(int k) {

	}

}
