package galib.platformer.gamestates;

import galib.platformer.GameObjects.GameObject;
import galib.platformer.GameObjects.Gems;
import galib.platformer.GameObjects.Ghost;
import galib.platformer.GameObjects.Mommy;
import galib.platformer.GameObjects.Player;
import galib.platformer.GameObjects.Tile;
import galib.platformer.GameObjects.Vampire;
import galib.platformer.input.Input;
import galib.platformer.level.LevelDesign;
import galib.platformer.main.Canvas;
import galib.platformer.sound.SoundEffects;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import resources.ResourceLoader;

public class GameplayState extends GameState {

	public static float screenOffsetX, screenOffsetY;

	private Image background;

	private float backgroundX;

	private Player player;

	private ArrayList<GameObject> gameObjects;

	private Font guiFont = new Font("Georgia", Font.BOLD,
			Canvas.UNIT_HEIGHT / 3);
	private Color guiTextColor = new Color(0.2f, 0.4f, 0.7f);
	private Color guiTextColor2 = new Color(0.7f, 0.2f, 0.1f);
	private float alpha = 0.1f;

	public static SoundEffects soundEffects ;

	public GameplayState(GameStateManager gsm) {
		super(gsm);

		init();
	}

	@Override
	public void init() {

		ResourceLoader.init();
		LevelDesign.init();

		soundEffects = MenuState.sound;
//		soundEffects.init();

		background = ResourceLoader.background_forest;
		
		gameObjects = new ArrayList<GameObject>();

		// gameObjects.add(player);

		for (int i = 0; i < LevelDesign.level.length; i++) {

			for (int j = 0; j < LevelDesign.level[i].length; j++) {

				if (LevelDesign.level[i][j] == 'p') {
					player = new Player((j - 2) * Canvas.UNIT_WIDTH, i
							* Canvas.UNIT_HEIGHT);
					gameObjects.add(player);
				}

				if (LevelDesign.level[i][j] == '#') {
					gameObjects.add(new Tile((j - 2) * Canvas.UNIT_WIDTH, i
							* Canvas.UNIT_HEIGHT));

				} else if (LevelDesign.level[i][j] == 'g') {
					gameObjects.add(new Gems((j - 2) * Canvas.UNIT_WIDTH, i
							* Canvas.UNIT_HEIGHT));

				} else if (LevelDesign.level[i][j] == 'G') {
					gameObjects.add(new Ghost((j - 2) * Canvas.UNIT_WIDTH, i
							* Canvas.UNIT_HEIGHT + Canvas.UNIT_HEIGHT / 5));

				} else if (LevelDesign.level[i][j] == 'M') {
					gameObjects.add(new Mommy((j - 2) * Canvas.UNIT_WIDTH, i
							* Canvas.UNIT_HEIGHT));

				} else if (LevelDesign.level[i][j] == 'V') {
					gameObjects.add(new Vampire((j - 2) * Canvas.UNIT_WIDTH, i
							* Canvas.UNIT_HEIGHT));

				}

			}

		}

		alpha = 0.1f;

	}

	@Override
	public void update(long deltaTime) {

		if (alpha + 0.01f <= 1)
			alpha += 0.01f;

		if (player.isAlive()) {

			for (int i = 0; i < gameObjects.size(); i++) {

				if (gameObjects.get(i).isAlive()) {

					gameObjects.get(i).update(deltaTime, gameObjects);

				} else {

					gameObjects.remove(i);

				}

			}

			if (backgroundX < -Canvas.WIDTH)
				backgroundX = 0;

		} else {

			if (soundEffects.audioClips.get("background_music").isRunning())
				soundEffects.audioClips.get("background_music").stop();

			gsm.states.push(new GameoverState(gsm));

		}

	}

	@Override
	public void draw(Graphics2D g) {

		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				alpha));

		g.drawImage(background, (int) backgroundX, 0, null);

		// player.draw(g);

		for (int i = 0; i < gameObjects.size(); i++) {

			if (gameObjects.get(i).isVisible())
				gameObjects.get(i).draw(g);
		}

		// showing GUI texts score/gems collection/life

		g.setFont(guiFont);

		g.setColor(guiTextColor);

		g.drawString("POINTS " + player.points, Canvas.UNIT_WIDTH / 2,
				Canvas.UNIT_HEIGHT);

		g.setColor(guiTextColor2);

		g.drawString("GEMS " + player.gemsTotal, Canvas.UNIT_WIDTH / 2,
				Canvas.UNIT_HEIGHT * 3 / 2);

		// g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
		// alpha));

		// g.drawImage(background, 0, 0, null);

		g.dispose();

	}

	@Override
	public void keyPressed(int k) {

		if (k == KeyEvent.VK_ESCAPE) {
			
			gsm.states.pop();

		} else {
			Input.key[k] = true;
		}

	}

	@Override
	public void keyReleased(int k) {

		Input.key[k] = false;

	}

	// public static void main(String[])

}
