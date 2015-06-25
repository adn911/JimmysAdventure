package galib.platformer.level;


import galib.platformer.animation.Animation;


import java.io.BufferedReader;
import java.io.IOException;

import resources.ResourceLoader;

public class LevelDesign {

	public static char[][] level;
	public static Animation startingAnimation;

	public static void init() {

		String line;

		BufferedReader reader = new BufferedReader(ResourceLoader.map_file);

		level = new char[10][1000];

		int i = 1;

		try {

			while ((line = reader.readLine()) != null) {

				level[10 - i] = line.toCharArray();
				System.out.println(level[10 - i]);

				i++;

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
