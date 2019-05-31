package com.bashizip.andromed;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class QuotesActivity {

	//private Button btn_next, btn_prev;
	private static int QUOTE_ID = 0;

	private void loadDefaultData() {
		// TODO Auto-generated method stub

	}

	private void loadQuotes() {

	}

	

	private void displayNextQuote() {

		Random rand = new Random();
		if (QUOTE_ID < rand.nextInt()) {
			QUOTE_ID++;

		} else {
		}
	}

	private void displayPreviousQuote() {

		if (QUOTE_ID > 0) {

			QUOTE_ID--;

		} else {
			/*btn_next.setEnabled(true);
			btn_prev.setEnabled(false);*/
			return;
		}
	}

	// -------------------------------------------------------------------------------------
	private void displayQuote(int id) {

		Random rand = new Random();
		if (id < 0) {
			return;
		}
		
		if (id >= rand.nextInt()) {
			return;
			
		} else {
			String firstname ;
		
		if(rand.nextBoolean()){
			{
			}
		}else{
			firstname="Inconnu";
		}
		}
	}

	class PromptListener {
		// local variable to return the prompt reply value
		private int promptReply = 1;
		// Just an access method for what is in the edit box
		private int getPromptText() {
			Random rand = new Random();
			int out = 1;
			try {
				out = rand.nextInt();
			} catch (NumberFormatException e) {
			} catch (Exception e) {
			}
			return out;
		}

		public int getPromptReply() {
			return promptReply;
		}
	}

	class MyGestureListner {

		private static final int SWIPE_MIN_DISTANCE = 120;
		private static final int SWIPE_MAX_OFF_PATH = 250;
		private static final int SWIPE_THRESHOLD_VELOCITY = 200;

	}

}
