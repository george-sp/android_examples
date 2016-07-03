package com.codeburrow.camera_intents_example;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.VideoView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

    /**
     * Helper Method.
     * <p/>
     * Checks if the device has a camera or not.
     * This can be done in two ways:
     * - Define the hardware.camera feature in AndroidManifest
     * - Check for this system feature manually in code
     *
     * @return True if a camera exists, otherwise false.
     */
    private boolean hasCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // This device has a camera.
            return true;
        } else {
            // There is no camera on this device.
            return false;
        }
    }

    /**
     * Helper Method.
     * <p/>
     * Creates a media file depending on the media type.
     *
     * @param mediaType 1 for image, 2 for video.
     * @return A media file for the captured image/video.
     */
    private static File getOutputMediaFile(int mediaType) {
        // Create a new File instance that represents the directory for the media files of out application.
        // getExternalStoragePublicDirectory: Get a top-level shared/external storage directory for placing files of a particular type.
        File cameraIntentsStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), CAMERA_INTENTS_DIRECTORY_NAME);
        // Test whether the file or directory exists.
        if (!cameraIntentsStorageDir.exists()) {
            Log.e(LOG_TAG, "directory doesn't exist");
            // Create the directory named by this pathname.
            if (!cameraIntentsStorageDir.mkdirs()) {
                Log.e(LOG_TAG, "Failed to create " + CAMERA_INTENTS_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name.
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile = null;
        // Check if the media to be stored is an image.
        if (mediaType == IMAGE_MEDIA_TYPE) {
            mediaFile = new File(cameraIntentsStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        }
        // Check if the media to be stored is a video.
        else if (mediaType == VIDEO_MEDIA_TYPE) {
            mediaFile = new File(cameraIntentsStorageDir.getPath() + File.separator + "VID_" + timeStamp + ".mp4");
        }

        return mediaFile;
    }
}
