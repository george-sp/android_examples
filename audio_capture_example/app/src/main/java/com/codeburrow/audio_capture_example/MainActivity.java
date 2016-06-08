package com.codeburrow.audio_capture_example;

import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private Button recordButton;
    private Button stopButton;
    private Button playButton;

    private MediaRecorder mMediaRecorder;

    private String outputFile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recordButton = (Button) findViewById(R.id.record_button);
        stopButton = (Button) findViewById(R.id.stop_button);
        playButton = (Button) findViewById(R.id.play_button);

        // Disable play and stop buttons.
        playButton.setEnabled(false);
        stopButton.setEnabled(false);

        // The pathname to use.
        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";

        // Create a new instance of MediaRecorder.
        mMediaRecorder = new MediaRecorder();
        // Sets the audio source to be used for recording.
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        // Sets the format of the output file produced during recording.
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        // Sets the audio encoder to be used for recording.
        mMediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        // Sets the path of the output file to be produced.
        mMediaRecorder.setOutputFile(outputFile);
    }

    public void recordAudio(View view) {
        try {
            // Prepare the recorder to begin capturing and encoding data.
            // This method must be called after setting up the desired audio and video sources, encoders, file format, etc., but before start().
            mMediaRecorder.prepare();
            // Begins capturing and encoding data to the file specified with setOutputFile().
            // Call this after prepare().
            mMediaRecorder.start();
        } catch (IllegalStateException e) {
            // Throws this exception if it is called after start() or before setOutputFormat().
            Log.e(LOG_TAG, e.getMessage());
        } catch (IOException e) {
            // Throws this exception if prepare fails otherwise.
            Log.e(LOG_TAG, e.getMessage());
        }

        recordButton.setEnabled(false);
        stopButton.setEnabled(true);

        Toast.makeText(MainActivity.this, "Recording started", Toast.LENGTH_SHORT).show();
    }


    public void stopAudio(View view) {
        // Stops recording.
        mMediaRecorder.stop();
        // Releases resources associated with this MediaRecorder object.
        mMediaRecorder.release();
        mMediaRecorder = null;

        stopButton.setEnabled(false);
        playButton.setEnabled(true);

        Toast.makeText(MainActivity.this, "Audio recorded successfully", Toast.LENGTH_SHORT).show();
    }

    public void playAudio(View view) {
    }
}
