package com.codeburrow.audio_player_example;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private MediaPlayer mMediaPlayer;

    private double timeElapsed = 0;
    private double finalTime = 0;
    private int forwardTime = 2000;
    private int backwardTime = 2000;

    private Handler durationHandler = new Handler();

    private TextView songNameTextView;
    private TextView durationTextView;

    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        durationTextView = (TextView) findViewById(R.id.song_duration_textview);
        songNameTextView = (TextView) findViewById(R.id.song_name_textview);
        songNameTextView.setText("Sample Song.mp3");

        // Create a Media Player for the given resource ID.
        mMediaPlayer = MediaPlayer.create(this, R.raw.sample_song);
        // Get the duration of the file
        finalTime = mMediaPlayer.getDuration();

        seekBar = (SeekBar) findViewById(R.id.seekbar);
        seekBar.setMax((int) finalTime);
        seekBar.setClickable(false);
    }

    public void play(View view) {
        // Start or resume playback.
        mMediaPlayer.start();
        // Get the current playback position.
        timeElapsed = mMediaPlayer.getCurrentPosition();
        // Set SeekBar progress.
        seekBar.setProgress((int) timeElapsed);

        // Use a Handler in order to continuously update the SeekBar progress and the time remaining duration.
        durationHandler.postDelayed(updateSeekBarTime, 100);
    }

    private Runnable updateSeekBarTime = new Runnable() {
        public void run() {
            // Get the current playback position.
            timeElapsed = mMediaPlayer.getCurrentPosition();
            // Set SeekBar progress.
            seekBar.setProgress((int) timeElapsed);
            // Set time remaining.
            double timeRemaining = finalTime - timeElapsed;
            durationTextView.setText(String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining), TimeUnit.MILLISECONDS.toSeconds((long) timeRemaining) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining))));

            // Repeat that again in 100 milliseconds.
            durationHandler.postDelayed(this, 100);
        }
    };

    public void pause(View view) {
        // Pause the playback.
        mMediaPlayer.pause();
    }

    public void forward(View view) {
        // Check if we can go forward at forwardTime seconds before song ends.
        if ((timeElapsed + forwardTime) < finalTime) {
            timeElapsed = timeElapsed + forwardTime;

            // Seek to the exact second of the track.
            mMediaPlayer.seekTo((int) timeElapsed);
        }
    }

    public void rewind(View view) {
        // Check if we can go backward at backwardTime seconds.
        if ((timeElapsed - backwardTime) >= 0) {
            timeElapsed = timeElapsed - backwardTime;

            // Seek to the exact second of the track.
            mMediaPlayer.seekTo((int) timeElapsed);
        }
    }
}
