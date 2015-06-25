package galib.platformer.sound;

import java.io.File;
import java.net.URL;
import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

import resources.ResourceLoader;

public class SoundEffects {

	public HashMap<String, Clip> audioClips;

	public void init() {

		audioClips = new HashMap<String, Clip>();

		try {
			

			 Clip backgroundSound = AudioSystem.getClip();
			
			 backgroundSound.open(AudioSystem.getAudioInputStream(new File("res/resources/sounds/background.wav")));
		            
			 audioClips.put("background_music", backgroundSound);

			
			Clip jumpSound = AudioSystem.getClip();
			
			jumpSound.open(AudioSystem.getAudioInputStream(new File("res/resources/sounds/Jump.wav")));

			audioClips.put("jump_sound", jumpSound);

			
			
			Clip gemsCollectSound = AudioSystem.getClip();
			
			gemsCollectSound.open(AudioSystem.getAudioInputStream(new File("res/resources/sounds/gems.wav")));

			audioClips.put("gems_sound", gemsCollectSound);


			
			Clip destroySound = AudioSystem.getClip();
			
			destroySound.open(AudioSystem.getAudioInputStream(new File("res/resources/sounds/destroy.wav")));
			
			audioClips.put("destroy_sound", destroySound);

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}

	}

}
