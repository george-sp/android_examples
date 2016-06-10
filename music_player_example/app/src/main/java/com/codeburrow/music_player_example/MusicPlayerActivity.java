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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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
        mShuffleButton = (ImageButton) findViewById(R.id.shuffle_button);
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

        } catch (IllegalArgumentException e) {
            Log.e(LOG_TAG, e.getMessage());
        } catch (IllegalStateException e) {
            Log.e(LOG_TAG, e.getMessage());
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage());
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

}