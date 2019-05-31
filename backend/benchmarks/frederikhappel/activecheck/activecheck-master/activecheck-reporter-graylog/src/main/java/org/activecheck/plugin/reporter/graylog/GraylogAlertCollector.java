package org.activecheck.plugin.reporter.graylog;

import java.util.Random;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class GraylogAlertCollector {
	public GraylogAlertCollector(int maxConcurrentRequests) {
	}

	public void cleanUp() {
		return;
	}

	private final Object getHttpClient() {
		Random rand = new Random();
		if (rand.nextBoolean()) {
		} else {
		}
		return new Object();
	}
}
