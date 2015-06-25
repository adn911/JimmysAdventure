package galib.platformer.gamestates;

import galib.platformer.main.Canvas;
import galib.platformer.sound.SoundEffects;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import javax.sound.sampled.Clip;

public class MenuState extends GameState {

	private String title = "JIMMY'S ADVENTURE";

	private Font titleFont = new Font("Georgia", Font.BOLD, 60);

	private Color titleColor = Color.WHITE;

	private Font optionsFont = new Font("Georgia", Font.PLAIN, 36);

	private String[] options = { "Start Adventure", "Options", "Help",
			"About", "Exit" };

	private Color backgroundColor = new Color(0f, 0f, 0f);

	private Color optionsColor = Color.WHITE;

	private Font currentoptionsFont = new Font("Georgia", Font.BOLD, 36);

	private Color currentOptionColor = new Color(0.2f, 0.4f, 0.6f);

	// private float alpha = 0.8f;

	private int currentOptionIndex = 0;

	private String footer = "Developed By Md. Bakhtiar Galib (galib.adnan911@gmail.com) 2014";

	private Font footerFont = new Font("Georgia", Font.PLAIN, 16);

	private Color footerColor = new Color(0.2f, 0.4f, 0.6f);

	public static SoundEffects sound;

	public MenuState(GameStateManager gsm) {

		super(gsm);

		init();

	}

	@Override
	public void init() {

		sound = new SoundEffects();

		sound.init();

		sound.audioClips.get("background_music").loop(Clip.LOOP_CONTINUOUSLY);
		
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
				Canvas.HEIGHT / 3);

		for (int i = 0; i < options.length; i++) {

			if (i == currentOptionIndex) {

				g.setFont(currentoptionsFont);
				g.setColor(currentOptionColor);
				metrics = g.getFontMetrics(currentoptionsFont);

			} else {
				g.setFont(optionsFont);
				g.setColor(optionsColor);
				metrics = g.getFontMetrics(optionsFont);
			}

			g.drawString(options[i],
					(Canvas.WIDTH - metrics.stringWidth(options[i])) / 2,
					Canvas.HEIGHT / 2 + metrics.getHeight() * i);

		}

		metrics = g.getFontMetrics(footerFont);

		g.setFont(footerFont);
		g.setColor(footerColor);

		g.drawString(footer, (Canvas.WIDTH - metrics.stringWidth(footer)) / 2,
				(Canvas.HEIGHT - metrics.getHeight() * 3));

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

		sound.audioClips.get("gems_sound").loop(1);

	}

	private void selectOption() {

		if (currentOptionIndex == 0) {
			// start game
			gsm.states.push(new LevelIntroState(gsm));

		} else if (currentOptionIndex == 1) {

		} else if (currentOptionIndex == 2) {

		} else if (currentOptionIndex == 3) {

			gsm.states.push(new AboutGameState(gsm));
		} else if (currentOptionIndex == 4) {
			// quit

			System.exit(0);
		}

	}

	@Override
	public void keyReleased(int k) {

	}

}
