package com.codeburrow.music_player_example;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MusicPlayerActivity extends AppCompatActivity implements OnCompletionListener, OnSeekBarChangeListener {

    private static final String LOG_TAG = MusicPlayerActivity.class.getSimpleName();

    // Player's Image Buttons
    private ImageButton mPlayButton;
    private ImageButton mForwardButton;
    private ImageButton mBackwardButton;
    private ImageButton mNextButton;
    private ImageButton mPreviousButton;
    private ImageButton mRepeatButton;
    private ImageButton mShuffleButton;
    private ImageButton mPlaylistButton;

    // Song Duration Seek Bar
    private SeekBar mSongSeekBar;

    // Player's Text Views
    private TextView mSongTitleTextView;
    private TextView mSongCurrentDurationTextView;
    private TextView mSongTotalDurationTextView;

    // Music Media Player
    private MediaPlayer mMediaPlayer;

    // Handler to update UI timer, seek bar, etc
    private Handler mHandler = new Handler();

    // Custom Songs Manager
    private SongsManager mSongsManager;

    // Class with Helper Methods
    private Utilities mUtilities;

    // Seek Forward and Backward Time
    private static final int SEEK_FORWARD_TIME = 5000;
    private static final int SEEK_BACKWARD_TIME = 5000;

    // Song's Index
    private int currentSongIndex = 0;

    // Flags for shuffling and repeating
    private boolean isShuffle = false;
    private boolean isRepeat = false;

    // List with Songs
    private ArrayList<HashMap<String, String>> mSongsList = new ArrayList<>();

    // Request Code for the Playlist Activity
    private static final int PLAYLIST_ACTIVITY_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        // Find all buttons from the layout.
        mPlayButton = (ImageButton) findViewById(R.id.play_button);
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if a Media Player exists.
                if (mMediaPlayer != null) {
                    // Check if Media Player is playing any song.
                    if (mMediaPlayer.isPlaying()) {
                        // If so, pause it.
                        mMediaPlayer.pause();
                        // Set the appropriate image resource to play button.
                        mPlayButton.setImageResource(R.drawable.btn_play);
                    } else {
                        // If it is not playing, resume the song.
                        mMediaPlayer.start();
                        // Set the appropriate image resource to play button.
                        mPlayButton.setImageResource(R.drawable.btn_pause);
                    }
                }
            }
        });

        mForwardButton = (ImageButton) findViewById(R.id.forward_button);
        mForwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get current song position.
                int currentTimePosition = mMediaPlayer.getCurrentPosition();
                // Calculate the new forward time position.
                int forwardTimePosition = currentTimePosition + SEEK_FORWARD_TIME;
                // Check if seek forward time is lesser than song's duration.
                if (forwardTimePosition <= mMediaPlayer.getDuration()) {
                    // Fast Forward the song to the forward time position.
                    mMediaPlayer.seekTo(currentTimePosition + SEEK_FORWARD_TIME);
                } else {
                    // Fast Forward to end position.
                    mMediaPlayer.seekTo(mMediaPlayer.getDuration());
                }
            }
        });

        mBackwardButton = (ImageButton) findViewById(R.id.backward_button);
        mBackwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get current song position.
                int currentTimePosition = mMediaPlayer.getCurrentPosition();
                // Calculate the new backward time position.
                int backwardTimePosition = currentTimePosition - SEEK_BACKWARD_TIME;
                // Check if seek backward time is greater than 0 sec.
                if (backwardTimePosition >= 0) {
                    // Rewind the song to the backward time position.
                    mMediaPlayer.seekTo(backwardTimePosition);
                } else {
                    // Rewind the song to the start of the duration - 0 sec.
                    mMediaPlayer.seekTo(0);
                }
            }
        });

        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if there is next song.
                if (currentSongIndex < (mSongsList.size() - 1)) {
                    // Play the next song.
                    playSong(currentSongIndex + 1);
                    // Move current song index.
                    currentSongIndex++;
                } else {
                    // Else start from the beginning.
                    // Play the first song.
                    playSong(0);
                    // Move current song index.
                    currentSongIndex = 0;
                }
            }
        });

        mPreviousButton = (ImageButton) findViewById(R.id.previous_button);
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if this is not the first song.
                if (currentSongIndex > 0) {
                    // Play the previous  song.
                    playSong(currentSongIndex - 1);
                    // Move current song index.
                    currentSongIndex--;
                } else {
                    // Else start from the end.
                    // Play the last song.
                    playSong(mSongsList.size() - 1);
                    // Move current song index.
                    currentSongIndex = mSongsList.size() - 1;
                }
            }
        });

        mPlaylistButton = (ImageButton) findViewById(R.id.playlist_button);
        mPlaylistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent playlistIntent = new Intent(MusicPlayerActivity.this, PlaylistActivity.class);
                startActivityForResult(playlistIntent, PLAYLIST_ACTIVITY_REQUEST_CODE);
            }
        });

        mRepeatButton = (ImageButton) findViewById(R.id.repeat_button);
        mRepeatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRepeat) {
                    // Change the image resource of the repeat button.
                    mRepeatButton.setImageResource(R.drawable.btn_repeat);
                    // Inform user with a Toast.
                    Toast.makeText(MusicPlayerActivity.this, "Repeat is OFF", Toast.LENGTH_SHORT).show();
                } else {
                    // Change the isShuffle flag to false.
                    isShuffle = false;
                    // Change the image resources of the repeat and shuffle buttons.
                    mRepeatButton.setImageResource(R.drawable.btn_repeat_pressed);
                    mShuffleButton.setImageResource(R.drawable.btn_shuffle);
                    // Inform user with a Toast.
                    Toast.makeText(MusicPlayerActivity.this, "Repeat is ON", Toast.LENGTH_SHORT).show();
                }

                // Change the isRepeat flag.
                isRepeat = !isRepeat;
            }
        });

        mShuffleButton = (ImageButton) findViewById(R.id.shuffle_button);
        mShuffleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShuffle) {
                    // Change the image resources of the shuffle button.
                    mShuffleButton.setImageResource(R.drawable.btn_shuffle);
                    // Inform user with a Toast.
                    Toast.makeText(MusicPlayerActivity.this, "Shuffle is OFF", Toast.LENGTH_SHORT).show();
                } else {
                    // Change the isShuffle flag to false.
                    isRepeat = false;
                    // Change the image resources of the repeat and shuffle buttons.
                    mShuffleButton.setImageResource(R.drawable.btn_shuffle_pressed);
                    mRepeatButton.setImageResource(R.drawable.btn_repeat);
                    // Inform user with a Toast.
                    Toast.makeText(MusicPlayerActivity.this, "Shuffle is ON", Toast.LENGTH_SHORT).show();
                }

                // Change the isShuffle flag.
                isShuffle = !isShuffle;
            }
        });

        mSongSeekBar = (SeekBar) findViewById(R.id.song_seek_bar);
        mSongTitleTextView = (TextView) findViewById(R.id.song_title_textview);
        mSongCurrentDurationTextView = (TextView) findViewById(R.id.song_current_duration_textview);
        mSongTotalDurationTextView = (TextView) findViewById(R.id.song_total_duration_textview);

        // Initialize MediaPlayer, SongsManager, Utilities
        mMediaPlayer = new MediaPlayer();
        mSongsManager = new SongsManager();
        mUtilities = new Utilities();

        // Get a list of all songs
        mSongsList = mSongsManager.getPlayList();

        // Listeners
        mMediaPlayer.setOnCompletionListener(this);
        mSongSeekBar.setOnSeekBarChangeListener(this);

        // By default play the first song.
        playSong(0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == PLAYLIST_ACTIVITY_REQUEST_CODE) {
            currentSongIndex = data.getExtras().getInt("songIndex");
            // Play the selected song.
            playSong(currentSongIndex);
        }
    }

    /**
     * Play the selected song.
     *
     * @param songIndex - Index of song in playlist
     */
    public void playSong(int songIndex) {
        try {
            // Play song via Media Player.
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(mSongsList.get(songIndex).get("songPath"));
            mMediaPlayer.prepare();
            mMediaPlayer.start();

            // Display song's title.
            String songTitle = mSongsList.get(songIndex).get("songTitle");
            Log.e(LOG_TAG, "Song's title: " + songTitle);
            mSongTitleTextView.setText(songTitle);

            // Change button's image to pause image.
            mPlayButton.setImageResource(R.drawable.btn_pause);

            // Set seek bar max and progress values.
            mSongSeekBar.setProgress(0);
            mSongSeekBar.setMax(100);

            // Update seek bar.
            updateSeekBarProgress();
        } catch (IllegalArgumentException e) {
            Log.e(LOG_TAG, e.getMessage());
        } catch (IllegalStateException e) {
            Log.e(LOG_TAG, e.getMessage());
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage());
        }
    }

    /**
     * Update timer / progress on seek bar
     */
    private void updateSeekBarProgress() {
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }

    /**
     * A Runnable background thread
     * <p/>
     * Runs every 100 milliseconds to update the seek bar progress
     */
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            // Get current and total durations.
            long totalDuration = mMediaPlayer.getDuration();
            long currentDuration = mMediaPlayer.getCurrentPosition();

            // Display total duration time.
            mSongTotalDurationTextView.setText(mUtilities.milliSecondsToTimer(totalDuration));

            // Display current time - time played.
            mSongCurrentDurationTextView.setText(mUtilities.milliSecondsToTimer(currentDuration));

            // Update seek bar's progress.
            int progress = mUtilities.getProgressPercentage(currentDuration, totalDuration);
            mSongSeekBar.setProgress(progress);

            // Schedule to run this runnable after 100 milliseconds.
            mHandler.postDelayed(this, 100);
        }
    };

    /**
     * When the song is completed:
     * - If Repeat is ON play the same song again
     * - If Shuffle is ON play a random song
     *
     * @param mp - MediaPlayer
     */
    @Override
    public void onCompletion(MediaPlayer mp) {
        if (isRepeat) {
            // Repeat is ON - play the same song again.
        } else if (isShuffle) {
            // Shuffle is ON - play a random song.
            Random random = new Random();
            currentSongIndex = random.nextInt(mSongsList.size());
        } else {
            // Neither repeat nor shuffle are ON - play the next song.
            if (currentSongIndex < (mSongsList.size() - 1)) {
                // If the current song is not the last one, play next.
                currentSongIndex++;
            } else {
                // If it is the last song, play the first one.
                currentSongIndex = 0;
            }
        }

        // Play the song at this index.
        playSong(currentSongIndex);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    /**
     * When user starts moving the progress handler.
     */
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // Remove Runnable from the handler.
        mHandler.removeCallbacks(mUpdateTimeTask);
    }

    /**
     * When user stops moving the progress handler.
     */
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // Remove Runnable from the handler.
        mHandler.removeCallbacks(mUpdateTimeTask);

        // Get total duration and current position.
        int totalDuration = mMediaPlayer.getDuration();
        int currentPosition = mUtilities.progressToTimer(seekBar.getProgress(), totalDuration);

        // Move forward or backward to the new current position.
        mMediaPlayer.seekTo(currentPosition);

        // Update seek bar progress - timer.
        updateSeekBarProgress();
    }

}