package com.codeburrow.music_player_example;

/**
 * @author George Spiridakis <george@codeburrow.com>
 * @since Jun/10/2016.
 * ===================================================
 * ---------->    http://codeburrow.com    <----------
 * ===================================================
 */
public class Utilities {

    private static final String LOG_TAG = Utilities.class.getSimpleName();

    /**
     * Function to convert milliseconds time to Timer Format.
     * Hours : Minutes : Seconds
     *
     * @param milliseconds
     */
    public String milliSecondsToTimer(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time.
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);

        // Add hours if there.
        if (hours > 0) {
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit.
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        // Return timer string.
        return finalTimerString;
    }

    /**
     * Function to get Progress percentage.
     *
     * @param currentDuration
     * @param totalDuration
     */
    public int getProgressPercentage(long currentDuration, long totalDuration) {
        Double percentage = (double) 0;

        long currentSeconds = (int) (currentDuration / 1000);
        long totalSeconds = (int) (totalDuration / 1000);

        // Calculate percentage.
        percentage = (((double) currentSeconds) / totalSeconds) * 100;

        // Return percentage.
        return percentage.intValue();
    }

    /**
     * Function to change progress to timer.
     *
     * @param progress      -
     * @param totalDuration returns current duration in milliseconds
     */
    public int progressToTimer(int progress, int totalDuration) {
        int currentDuration = 0;
        totalDuration = (int) (totalDuration / 1000);
        currentDuration = (int) ((((double) progress) / 100) * totalDuration);

        // Return current duration in milliseconds
        return currentDuration * 1000;
    }
}