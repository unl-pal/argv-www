/**
 * 
 */
package org.devel.jfxcontrols.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.concurrent.Task;

/**
 * This task can be used as base for running {@link Task}s in a synchronized
 * way. For this purpose it provides {@link #runSync(Runnable)} 2 run a
 * {@link Runnable} inside the UI thread and subsequently wait and
 * {@link #rideOn()}
 * 
 * @author stefan.illgen
 * 
 */
public abstract class UITask<V> extends Task<V> {

	private static final long DEFAULT_TIME_OUT = 20;
	private static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.SECONDS;
	
	private CountDownLatch latch;
	private long timeout;
	private TimeUnit timeUnit;
	
	public UITask() {
		this(DEFAULT_TIME_OUT, DEFAULT_TIME_UNIT);
	}

	public UITask(int timeout) {
		this(timeout, DEFAULT_TIME_UNIT);
	}
	
	public UITask(long timeout, TimeUnit timeUnit) {
		this.timeout = timeout;
		this.timeUnit = timeUnit;
	}

	/**
	 * Equivalent 2 Display.syncExec(..) known from SWT.
	 * 
	 * @param runnable
	 * @return true, if the runnable was run successfully inside the UI thread,
	 *         otherwise false
	 */
	protected boolean runSync(Runnable runnable) {
		latch = new CountDownLatch(1);
		if (!Platform.isFxApplicationThread())
			Platform.runLater(runnable);
		else
			runnable.run();
		try {
			return latch.await(timeout, timeUnit);
		} catch (InterruptedException e) {
			return false;
		}
	}

	/**
	 * Trigger success of scheduled {@link Runnable} from inside the UI thread
	 * to ride on asynchronously (i.e. in an non blocking way).
	 */
	protected void rideOn() {
		latch.countDown();
	}

}
