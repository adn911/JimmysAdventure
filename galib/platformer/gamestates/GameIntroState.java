package galib.platformer.gamestates;

import galib.platformer.main.Canvas;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class GameIntroState extends GameState {

	private String title[] = {

	"ONCE UPON A TIME .. ", "THERE WAS A LITTLE BOY NAMED JIMMY! ",
			"WHO LOVED ADVENTURES!", "EVERY NIGHT HE WOULD GO AN ADVENTURES" };

	private Font titleFont = new Font("Georgia", Font.BOLD, 32);

	private Color titleColor = new Color(0.2f, 0.4f, 0.6f);

	private Color backgroundColor = new Color(0f, 0f, 0f);

	private long elapsedTime = 0;

	private float alpha = 1.0f;

	private int titleCount;

	public GameIntroState(GameStateManager gsm) {

		super(gsm);

		init();

	}

	@Override
	public void init() {
	}

	@Override
	public void update(long deltaTime) {

		elapsedTime += deltaTime;

		if (elapsedTime >= (titleCount + 1) * 2000) {

			alpha -= 0.005f;

			if (alpha <= 0.6f) {
				titleCount++;
				alpha = 1f;
			}

			if (titleCount >= title.length) {
				gsm.states.pop();
				gsm.states.push(new LevelIntroState(gsm));
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

		g.drawString(title[titleCount],
				(Canvas.WIDTH - metrics.stringWidth(title[titleCount])) / 2,
				(Canvas.HEIGHT - metrics.getHeight()) / 2);

	}

	@Override
	public void keyPressed(int k) {

		if (k == KeyEvent.VK_ENTER) {

			gsm.states.pop();
			gsm.states.push(new GameplayState(gsm));

		}

	}

	@Override
	public void keyReleased(int k) {

	}

}
