package com.jeremyfeinstein.slidingmenu.example.fragments;

public class BirdGridFragment {

	private int mPos = -1;
	private int mImgRes;
	
	public BirdGridFragment() { }
	public BirdGridFragment(int pos) {
		mPos = pos;
	}
	
	private class GridAdapter {

		public int getCount() {
			return 30;
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return position;
		}
		
	}
}
