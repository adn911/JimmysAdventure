package galib.platformer.animation;

import java.util.HashMap;

public class AnimationSet {

	private HashMap<String, Animation> animations;

	public AnimationSet() {
		animations = new HashMap<String, Animation>();

	}

	public void addAnimation(String name, Animation anim) {
		animations.put(name, anim);
	}

	public Animation get(String name) {
		return animations.get(name);
	}

}
