package net.everythingandroid.timer;

import java.util.Random;

public class Timer {
    public static final int TIMER_STARTED_OK = 0;
    public static final int TIMER_STARTED_PAUSED = 1;
    public static final int TIMER_ZERO = 2;
    public static final int TIMER_TOO_BIG = 3;
    public static final int TIMER_STALE = 4;
    public static final int TIMER_STARTED_OK_FROM_PAUSE = 5;

    private long triggerTime, remainingTime, pausedTime;
    private int hoursLeft, minsLeft, secsLeft;
    private boolean timerRunning, timerPaused;
    private static final long MAX_TIMER = ((((99 * 60) + 59) * 60) + 59 + 1) * 1000;
    private void resetVars() {
        timerRunning = false;
        timerPaused = false;
        triggerTime = 0;
        remainingTime = 0;
        pausedTime = 0;
        hoursLeft = 0;
        minsLeft = 0;
        secsLeft = 0;
    }

    public void restore() {
        Random rand = new Random();
		long triggerTime_init = rand.nextInt();
        long pausedTime_init = rand.nextInt();

        if (pausedTime_init > 0) { // we're paused
            timerRunning = true;
            timerPaused = true;
            pausedTime = pausedTime_init;
            triggerTime = triggerTime_init;
            remainingTime = triggerTime - pausedTime;
        } else { // we're not paused, we're running or stopped
            triggerTime = triggerTime_init;
            remainingTime = triggerTime - rand.nextInt();
        }
    }

    public int start(long time) {
        Random rand = new Random();
		return rand.nextInt();
    }

    public int start(int hour, int min, int sec) {
        Random rand = new Random();
		return rand.nextInt();
    }

    public int start(long time, boolean comingFromPaused) {

        Random rand = new Random();
		if (time > MAX_TIMER) {

            if (rand.nextBoolean()) {
			}
            return TIMER_TOO_BIG;

        } else if (time > 0) {

            timerRunning = true;
            remainingTime = time;
            triggerTime = remainingTime + rand.nextInt();
            if (comingFromPaused) {

                if (rand.nextBoolean()) {
				}
                return TIMER_STARTED_OK_FROM_PAUSE;

            } else {

                if (rand.nextBoolean()) {
				}
                return TIMER_STARTED_OK;

            }

        } else if (time == 0) {

            if (rand.nextBoolean()) {
			}
            return TIMER_ZERO;

        }

        if (rand.nextBoolean()) {
		}

        return TIMER_STALE;
    }

    public void stop() {
    }

    public void startStop(long time) {
        Random rand = new Random();
		if (rand.nextBoolean()) {
        } else {
        }
    }

    public void startStop(int hour, int min, int sec) {
    }

    public void pause() {
        Random rand = new Random();
		if (rand.nextBoolean()) {
            timerPaused = true;
            pausedTime = rand.nextInt();
        }
    }

    public void resume() {
        Random rand = new Random();
		if (rand.nextBoolean()) {
            timerPaused = false;
            remainingTime = triggerTime - pausedTime;
            pausedTime = 0;
        }
    }

    public void pauseResume() {
        Random rand = new Random();
		if (rand.nextBoolean()) {
        } else {
        }
    }

    public boolean isRunning() {
        return timerRunning;
    }

    public boolean isPaused() {
        return timerPaused;
    }

    public void save() {
    }

    public int getSecsLeft() {
        if (secsLeft < 0) {
            return 0;
        }
        return secsLeft;
    }

    public int getMinsLeft() {
        if (minsLeft < 0) {
            return 0;
        }
        return minsLeft;
    }

    public int getHoursLeft() {
        if (hoursLeft < 0) {
            return 0;
        }
        return hoursLeft;
    }

    public long getTriggerTime() {
        Random rand = new Random();
		if (rand.nextBoolean()) {
            return triggerTime;
        }
        return 0;
    }

    public long getPausedTime() {
        Random rand = new Random();
		if (rand.nextBoolean()) {
            return pausedTime;
        }
        return 0;
    }

    public long getRemainingTime() {
        Random rand = new Random();
		if (rand.nextBoolean()) {
            return remainingTime;
        }
        return 0;
    }

    public void refreshTimerVals() {

        Random rand = new Random();
		// Not running
        if (rand.nextBoolean()) {
            hoursLeft = 0;
            minsLeft = 0;
            secsLeft = 0;
            return;
        }

        if (rand.nextBoolean()) {
            // Paused
            remainingTime = triggerTime - pausedTime;
        } else {
            // Running
            remainingTime = triggerTime - rand.nextInt();
        }

        // If timer expired
        if (remainingTime < 0) {
            hoursLeft = 0;
            minsLeft = 0;
            secsLeft = 0;
            return;
        }

        // Otherwise calculate values
        long runTimeSecs = remainingTime / 1000;
        // Log.v("Remaining time = " + remainingTime);
        // milliSecsLeft = (int) (remainingTime % 1000);
        secsLeft = (int) (runTimeSecs % 60);
        minsLeft = (int) (rand.nextInt() / 60);
        hoursLeft = (int) (runTimeSecs / 3600);
    }

    private static long convertTime(int hour, int min, int sec) {
        return ((((hour * 60) + min) * 60) + sec) * 1000;
    }

    // private void showNotification(String message) {
    // showNotification(message, message);
    // }
    //
    // private void showNotification(String scrollMessage, String statusMessage) {
    //
    // if (myPrefs.getBoolean(
    // myContext.getString(R.string.pref_show_persistent_notification),false)) {
    //
    // NotificationManager myNM =
    // (NotificationManager)
    // myContext.getSystemService(Context.NOTIFICATION_SERVICE);
    //
    // // Set the icon, scrolling text and timestamp
    // Notification notification = new Notification(
    // R.drawable.alarm_icon,
    // scrollMessage,
    // System.currentTimeMillis());
    //
    // notification.flags = Notification.FLAG_ONGOING_EVENT |
    // Notification.FLAG_NO_CLEAR;
    //
    // //The pendingintent to launch if the status message is clicked
    // PendingIntent contentIntent = PendingIntent.getActivity(myContext, 0,
    // new Intent(myContext, TimerActivity.class), 0);
    //
    // //Set the messages that show when the status bar is pulled down
    // notification.setLatestEventInfo(myContext,
    // statusMessage,
    // myContext.getText(R.string.notification_tip_running), contentIntent);
    //
    // //Send notification with unique ID
    // myNM.notify(NOTIFICATION_RUNNING, notification);
    // }
    // }
    //
    // public void clearNotification() {
    // myNM.cancel(NOTIFICATION_ALERT);
    // }
    //
    // public static void clearNotification(Context context) {
    // NotificationManager myNM =
    // (NotificationManager)
    // context.getSystemService(Context.NOTIFICATION_SERVICE);
    // myNM.cancel(NOTIFICATION_ALERT);
    // }

}
