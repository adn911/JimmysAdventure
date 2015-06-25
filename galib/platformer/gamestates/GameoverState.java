package galib.platformer.gamestates;

import galib.platformer.main.Canvas;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class GameoverState extends GameState {

	private String title = "GAME OVER";

	private Font titleFont = new Font("Georgia", Font.BOLD, 50);

	private Color titleColor = Color.WHITE;

	private Font optionsFont = new Font("Georgia", Font.PLAIN, 30);

	private String[] options = { "PLAY AGAIN", "MAIN MENU" };

	private Color backgroundColor = new Color(0f, 0f, 0f);

	private Color optionsColor = Color.WHITE;

	private Font currentoptionsFont = new Font("Georgia", Font.BOLD, 30);

	private Color currentOptionColor = new Color(0.2f, 0.4f, 0.6f);

	private int currentOptionIndex = 0;

	public GameoverState(GameStateManager gsm) {

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

		// g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha));
		g.fillRect(0, 0, Canvas.WIDTH, Canvas.HEIGHT);

		g.setColor(titleColor);
		g.setFont(titleFont);
		FontMetrics metrics = g.getFontMetrics(titleFont);

		g.drawString(title, (Canvas.WIDTH - metrics.stringWidth(title)) / 2,
				Canvas.HEIGHT * 0.4f);

		metrics = g.getFontMetrics(optionsFont);

		for (int i = 0; i < options.length; i++) {
			if (i == currentOptionIndex) {
				g.setFont(currentoptionsFont);
				g.setColor(currentOptionColor);

			} else {
				g.setFont(optionsFont);
				g.setColor(optionsColor);
			}

			g.drawString(options[i],
					(Canvas.WIDTH - metrics.stringWidth(options[i])) / 2,
					Canvas.HEIGHT * 0.55f + metrics.getHeight() * i);

		}

		/*
		 * metrics = g.getFontMetrics(footerFont);
		 * 
		 * g.setFont(footerFont); g.setColor(footerColor);
		 * 
		 * g.drawString(footer, (Canvas.WIDTH - metrics.stringWidth(footer)) /
		 * 2, (Canvas.HEIGHT - metrics.getHeight()));
		 */
	}

	@Override
	public void keyPressed(int k) {

		if (k == KeyEvent.VK_ENTER) {
			selectOption();

		}

		else if (k == KeyEvent.VK_UP) {
			currentOptionIndex = currentOptionIndex - 1;

			if (currentOptionIndex < 0)
				currentOptionIndex = options.length - 1;
		}

		else if (k == KeyEvent.VK_DOWN) {
			currentOptionIndex = (currentOptionIndex + 1) % options.length;

		}

	}

	private void selectOption() {

		if (currentOptionIndex == 0) {

			gsm.states.pop();
			gsm.states.pop();

			gsm.states.push(new GameplayState(gsm));

		} else if (currentOptionIndex == 1) {

			gsm.states.pop();
			gsm.states.pop();

		}

	}

	@Override
	public void keyReleased(int k) {

	}

}
