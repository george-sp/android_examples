package com.codeburrow.camera_intents_example;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check if there is a result from the take picture intent.
        if (requestCode == TAKE_PICTURE_REQUEST_CODE) {
            // Check if an image is successfully captured.
            if (resultCode == RESULT_OK) {
                // Display the captured image.
                displayCapturedImage();
            }
            // Check if image capture is cancelled by the user.
            else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(MainActivity.this, "Image Capture Cancelled", Toast.LENGTH_SHORT).show();
            }
            // The image capture failed.
            else {
                Toast.makeText(MainActivity.this, "Image Capture Failed", Toast.LENGTH_SHORT).show();
            }
        }
        // Check if there is a result from the record video intent.
        else if (requestCode == RECORD_VIDEO_REQUEST_CODE) {
            // Check if a video is successfully recorded.
            if (resultCode == RESULT_OK) {
                // Preview the recorded video.
                previewRecordedVideo();
            }
            // Check if video record is cancelled by the user.
            else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(MainActivity.this, "Video Record Cancelled", Toast.LENGTH_SHORT).show();
            }
            // The video record failed.
            else {
                Toast.makeText(MainActivity.this, "Video Record Failed", Toast.LENGTH_SHORT).show();
            }
        }
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

    public void takePicture(View view) {
        // An intent that request the in-build camera app to take a picture.
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Get the Uri of the image file.
        mFileUri = Uri.fromFile(getOutputMediaFile(IMAGE_MEDIA_TYPE));
        // Specify a path where the image will be stored.
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mFileUri);
        // Start the intent.
        startActivityForResult(takePictureIntent, TAKE_PICTURE_REQUEST_CODE);
    }

    public void recordVideo(View view) {
        // An intent that request the in-build camera app to record a video.
        Intent recordVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        /*
         * Set Video Quality.
         *
         * This is an integer property.
         * Currently value 0 means low quality, suitable for MMS messages,
         * and value 1 means high quality.
         */
        recordVideoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        // Get the Uri of the image file.
        mFileUri = Uri.fromFile(getOutputMediaFile(VIDEO_MEDIA_TYPE));
        // Specify a path where the image will be stored.
        recordVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, mFileUri);
        // Start the intent.
        startActivityForResult(recordVideoIntent, RECORD_VIDEO_REQUEST_CODE);
    }

    /**
     * Helper Method.
     * <p/>
     * Displays an image from a file path to the image view.
     */
    private void displayCapturedImage() {
        try {
            // Hide the video view.
            mVideoView.setVisibility(View.GONE);
            // Show the image view.
            mImageView.setVisibility(View.VISIBLE);
            /*
             * Create a default Options object,
             * which if left unchanged will give the same result from the decoder
             * as if null were passed.
             */
            BitmapFactory.Options options = new BitmapFactory.Options();
            /*
             * Downsizing image as it throws OutOfMemory Exception for larger images.
             *
             * inSampleSize:
             *              If set to a value > 1,
             *              requests the decoder to subsample the original image,
             *              returning a smaller image to save memory.
             *
             * The sample size is the number of pixels in either dimension
             * that correspond to a single pixel in the decoded bitmap.
             * For example, inSampleSize == 4 returns an image that is
             * 1/4 the width/height of the original, and 1/16 the number of pixels.
             * Any value <= 1 is treated the same as 1.
             *
             * Note:
             *      the decoder uses a final value based on powers of 2,
             *      any other value will be rounded down to the nearest power of 2.
             */
            options.inSampleSize = 8;
            // Decode the file path into a bitmap.
            final Bitmap bitmap = BitmapFactory.decodeFile(mFileUri.getPath(), options);
            // Set the bitmap as the content of the image view.
            mImageView.setImageBitmap(bitmap);
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        }
    }

    private void previewRecordedVideo() {
        try {
            // Hide the image view.
            mImageView.setVisibility(View.GONE);
            // Show the video view.
            mVideoView.setVisibility(View.VISIBLE);
            // Set video path.
            mVideoView.setVideoPath(mFileUri.getPath());
            // Start playing the recorded view.
            mVideoView.start();
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
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
