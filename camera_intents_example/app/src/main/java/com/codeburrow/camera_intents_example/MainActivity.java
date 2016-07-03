package com.codeburrow.camera_intents_example;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    // The request code for the take picture intent.
    private static final int TAKE_PICTURE_REQUEST_CODE = 100;
    // The request code for the record video intent.
    private static final int RECORD_VIDEO_REQUEST_CODE = 200;
    // An integer that represents image media type.
    public static final int IMAGE_MEDIA_TYPE = 1;
    // An integer that represents video media type.
    public static final int VIDEO_MEDIA_TYPE = 2;
    // Directory name to store captured images and videos.
    private static final String CAMERA_INTENTS_DIRECTORY_NAME = "Camera Intents Dir";
    // The URI of the image/video to be saved.
    private Uri mFileUri;
    // ImageView to display captured image.
    private ImageView mImageView;
    // VideoView to preview recorded video.
    private VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = (ImageView) findViewById(R.id.display_picture_image_view);
        mVideoView = (VideoView) findViewById(R.id.preview_video_video_view);
    }
}
