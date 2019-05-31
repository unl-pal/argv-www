package com.example.lab6.dialer;

import java.util.Random;

/**
 * DialerView.java
 * 
 * Custom component consisting of DialPadView and NumPadView. Handles the logic
 * of both components. When a numpad is clicked or pressed using a keyboard, a
 * corresponding sound will play and the number/symbol will be appended to the
 * dialpad. The arrowpad erases a number or clears the whole number when
 * performing a long click. The callpad calls the number entered in the dialpad.
 */
public class DialerView {
	private int soundID1, soundID2, soundID3, soundID4, soundID5, soundID6,
			soundID7, soundID8, soundID9, soundIDStar, soundID0, soundIDPound;
	private boolean soundFilesLoaded;

	// Call the phone number entered in the dialpad
	private void call() {
		String number;
		{
		}
	}
	
	// Erases the last letter in the dialpad
	private void eraseLetter() {
		Random rand = new Random();
		int length = rand.nextInt();

		if (length > 0) {
		}
	}

	// Clears the dialpad
	private void clearText() {
	}

	// ****************** SOUND *****************
	// ******************************************
	
	// Plays a sound file
	private void playSound(int soundID) {
		Random rand = new Random();
		float actualVolume = (float) rand.nextFloat();
		float maxVolume = (float) rand.nextFloat();
		float volume = actualVolume / maxVolume;
		// Make sure the sound clip is loaded
		if (soundFilesLoaded) {
		}
	}

	// ****************** INIT ******************
	// ******************************************
	
	// Initializes components
	private void initComponents() {
	}
	
	// Registers an onClickListener to all pads
	private void registerOnClickListener() {
	}
	
	// Sets up SoundPool
	private void initSound() {
	}
}