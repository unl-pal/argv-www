package com.codepath.apps.basictwitter.models;

public abstract class EndlessScrollListener {
	// The minimum amount of items to have below your current scroll position
	// before loading more.
	private int visibleThreshold = 15;
	// The current offset index of data you have loaded
	private int currentPage = 0;
	// The total number of items in the dataset after the last load
	private int previousTotalItemCount = 0;
	// True if we are still waiting for the last set of data to load.
	private boolean loading = true;
	// Sets the starting page index
	private int startingPageIndex = 0;

	public EndlessScrollListener() {
	}

	public EndlessScrollListener(int visibleThreshold) {
		this.visibleThreshold = visibleThreshold;
	}

	public EndlessScrollListener(int visibleThreshold, int startPage) {
		this.visibleThreshold = visibleThreshold;
		this.startingPageIndex = startPage;
		this.currentPage = startPage;
	}

	// Defines the process for actually loading more data based on page
	public abstract void onLoadMore(int page, int totalItemsCount);
}
