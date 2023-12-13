package com.tcc.main;

import java.applet.Applet;
import java.applet.AudioClip;

@SuppressWarnings("removal")
public class Sound {

	private AudioClip clip; 
	
	public static final Sound musicBackground = new Sound("/horror01.mp3");
	public static final Sound hurtEffect = new Sound("/Hit_Hurt.wav");
	public static final Sound gunEffect = new Sound("/gun.wav");
	
	@SuppressWarnings("deprecation")
	private Sound(String name) {
		try {
			clip = Applet.newAudioClip(Sound.class.getResource(name));
		}catch(Throwable e) {
			
		}
	}
	
	public void play() {
		try {
			new Thread() {
				@SuppressWarnings("deprecation")
				public void run() {
					clip.play();
				}
			}.start();
			
		}catch(Throwable e) {
			
		}
	} 
	public void loop() {
		try {
			new Thread() {
				@SuppressWarnings("deprecation")
				public void run() {
					clip.loop();
				}
			}.start();
			
		}catch(Throwable e) {
			
		}
	}
}
