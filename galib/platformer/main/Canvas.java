package galib.platformer.main;

import galib.platformer.gamestates.GameStateManager;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class Canvas extends JPanel implements Runnable, KeyListener {

	// dimensions
	public static int WIDTH =  Toolkit.getDefaultToolkit().getScreenSize().width;
									
	public static int HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
									// ;

	public static int UNIT_HEIGHT = HEIGHT / 10;
	public static int UNIT_WIDTH = WIDTH / 10;

	// gameThread
	private Thread gameThread;
	private boolean isRunning;

	// refresh time
	private int FPS = 60;
	private int frameTime = 1000 / FPS;

	// Game State Manager
	private GameStateManager gsm;

	public Canvas() {

		gsm = new GameStateManager();

		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		addKeyListener(this);
		setFocusable(true);
		setDoubleBuffered(true);

		System.out.println(Canvas.WIDTH);
		System.out.println(Canvas.HEIGHT);
	}

	@Override
	public void addNotify() {

		super.addNotify();

		if (gameThread == null) {
			gameThread = new Thread(this);
		}

		gameThread.start();

		isRunning = true;
	}

	@Override
	public void run() {

		long start, elapsed = 0, wait = 0;

		while (isRunning) {

			start = System.nanoTime() / 1000000;

			System.out.println(elapsed + wait);

			update(elapsed + wait);
			repaint();

			elapsed = System.nanoTime() / 1000000 - start;

			wait = frameTime - elapsed;

			if (wait <= 0)
				wait = 5;

			try {

				Thread.sleep(wait);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	public void update(long deltaTime) {
		gsm.update(deltaTime);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.clearRect(0, 0, WIDTH, HEIGHT);

		gsm.draw(g);
	}

	@Override
	public void keyPressed(KeyEvent e) {

		gsm.keyPressed(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {

		gsm.keyReleased(e.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}
