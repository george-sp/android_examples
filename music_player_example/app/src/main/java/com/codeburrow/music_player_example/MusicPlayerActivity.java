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
    private static final int SEEK_BACKWARD_TIE = 5000;

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
        mBackwardButton = (ImageButton) findViewById(R.id.backward_button);
        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mPreviousButton = (ImageButton) findViewById(R.id.previous_button);

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
        mSongTitleTextView = (TextView) findViewById(R.id.songTitle);
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
            Log.e(LOG_TAG, "Current Song Index: " + String.valueOf(currentSongIndex));
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