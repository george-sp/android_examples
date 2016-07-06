package com.codeburrow.camera_basics_example;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements PictureCallback {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String STATE_CAMERA_ID = "CameraID";
    private static final String STORAGE_DIRECTORY_NAME = "Camera Basics Dir";
    private Camera mCamera;
    private CameraView mCameraView;
    private ImageButton mSwitchCameraImageButton;
    private int mCameraId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(LOG_TAG, "----- onCreate -----");

        setContentView(R.layout.activity_main);

        /*
         * Retrieve the current Window for the Activity or null if it is not visual.
         * Set Window Flag: as long as this window is visible to the user,
         *                  keep the device's screen turned on and bright.
         */
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Find mSwitchCameraImageButton in the activity_main layout.
        mSwitchCameraImageButton = (ImageButton) findViewById(R.id.switch_camera_image_button);
        // Set mCameraId to the back camera id by default.
        switchCameraId();
        // Find the FrameLayout in the activity's layout.
        FrameLayout mCameraViewFrameLayout = (FrameLayout) findViewById(R.id.camera_view_frame_layout);
        // Create a SurfaceView to show camera data.
        mCameraView = new CameraView(this, mCamera);
        // Add the SurfaceView to the FrameLayout.
        mCameraViewFrameLayout.addView(mCameraView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(LOG_TAG, "----- onResume -----");

        // Check if the device has a camera.
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Toast.makeText(MainActivity.this, "Your device doesn't have a camera.", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Check if the device has a front camera.
        if (getFrontCameraId() < 0) {
            Toast.makeText(MainActivity.this, "Your device doesn't have a front camera.", Toast.LENGTH_SHORT).show();
            mSwitchCameraImageButton.setVisibility(View.GONE);
        }

        // If an open camera doesn't exist.
        if (mCamera == null) {
            // Open a camera object and start preview.
            openCameraAndPreview();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(LOG_TAG, "----- onPause -----");

        releaseCamera();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.e(LOG_TAG, "----- onSaveInstanceState -----");

        savedInstanceState.putInt(STATE_CAMERA_ID, mCameraId);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e(LOG_TAG, "----- onRestoreInstanceState -----");

        mCameraId = savedInstanceState.getInt(STATE_CAMERA_ID, mCameraId);
    }

    /**
     * Helper Method.
     * <p>
     * Opens a camera based on camera id and
     * and starts/refreshes the preview.
     */
    private void openCameraAndPreview() {
        try {
            Log.e(LOG_TAG, "back camera id: " + getBackCameraId());
            // You can use open(int) to use different cameras.
            mCamera = Camera.open(mCameraId);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Failed to get camera: " + e.getMessage());
        }
        mCameraView.refreshCamera(mCamera);
        setCameraDisplayOrientation(this, mCameraId, mCamera);
    }

    /**
     * Heleper Method.
     * <p>
     * Gets the front camera's id, if it exists.
     *
     * @return The camera id of the front facing camera.
     */
    private int getFrontCameraId() {
        int frontCameraId = -1;
        // Get the number of physical cameras available on this device.
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int cameraId = 0; cameraId < numberOfCameras; cameraId++) {
            // CameraInfo class can carry information about a camera.
            CameraInfo cameraInfo = new CameraInfo();
            // Get information about a particular camera.
            Camera.getCameraInfo(cameraId, cameraInfo);
            // Check the direction that the camera faces.
            if (cameraInfo.facing == CameraInfo.CAMERA_FACING_FRONT) {
                // Set the cameraId to the front camera id.
                frontCameraId = cameraId;
                break;
            }
        }
        // Return the cameraId.
        return frontCameraId;
    }

    /**
     * Helper Method.
     * <p>
     * Gets the back camera's id, if it exists.
     *
     * @return The camera id of the back facing camera.
     */
    private int getBackCameraId() {
        int backCameraId = -1;
        // Get the number of physical cameras available on this device.
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int cameraId = 0; cameraId < numberOfCameras; cameraId++) {
            // CameraInfo class can carry information about a camera.
            CameraInfo cameraInfo = new CameraInfo();
            // Get information about a particular camera.
            Camera.getCameraInfo(cameraId, cameraInfo);
            // Check the direction that the camera faces.
            if (cameraInfo.facing == CameraInfo.CAMERA_FACING_BACK) {
                // Set the cameraId to the front camera id.
                backCameraId = cameraId;
                break;
            }
        }
        // Return the cameraId.
        return backCameraId;
    }

    /**
     * Helper Method.
     * <p>
     * Ensures the correct orientation of the camera preview.
     *
     * @param activity The activity the camera object belongs to.
     * @param cameraId The id of the camera (0 or 1 / back or front).
     * @param camera   The camera object used for the preview.
     */
    private void setCameraDisplayOrientation(Activity activity, int cameraId, Camera camera) {
        // CameraInfo class can carry information about a camera.
        CameraInfo cameraInfo = new CameraInfo();
        // Get information about a particular camera.
        Camera.getCameraInfo(cameraId, cameraInfo);

        /*
         * getWindowManager:     Retrieve the WindowManager for showing custom windows.
         * getDefaultDisplay:    Returns the Display upon which this WindowManager instance
         *                       will create new windows.
         * getRotation:          Returns the rotation of the screen
         *                       from its "natural" orientation.
         */
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();

        // Get the degrees of the current Surface rotation.
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        /*
         * int resultDegrees:
         *              The angle that the picture will be rotated clockwise.
         * Valid Values: 0, 90, 180, 270
         */
        int resultDegrees;
        // If the front-facing camera is used.
        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            resultDegrees = (cameraInfo.orientation + degrees) % 360;
            // Compensate the mirror.
            resultDegrees = (360 - resultDegrees) % 360;
        }
        // If the back-facing camera is used.
        else {
            resultDegrees = (cameraInfo.orientation - degrees + 360) % 360;
        }
        // Set the clockwise rotation of preview display in degrees.
        camera.setDisplayOrientation(resultDegrees);
    }

    public void switchCameraClicked(View view) {
        // Get the number of physical cameras available on this device.
        int numberOfCameras = Camera.getNumberOfCameras();
        if (numberOfCameras > 1) {
            // Release the old camera.
            releaseCamera();
            // Switch camera.
            switchCameraId();
            // Open new camera.
            openCameraAndPreview();
        } else {
            Toast.makeText(MainActivity.this, "Your device has only one camera.", Toast.LENGTH_SHORT).show();
        }
    }

    public void takePictureClicked(View view) {
        mCamera.takePicture(null, null, this);
    }

    /**
     * Helper Method.
     * <p>
     * Sets the camera id to 0, if mCameraId = null.
     * Otherwise, switches camera id between 0 and 1 (back and front).
     */
    private void switchCameraId() {
        switch (mCameraId) {
            case 0:
                mCameraId = getFrontCameraId();
                break;
            case 1:
                mCameraId = getBackCameraId();
                break;
            default:
                mCameraId = getBackCameraId();
        }
    }

    /**
     * Helper Method.
     * <p>
     * Releases camera and sets mCamera object to null.
     */
    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }

    /**
     * Called when image data is available after a picture is taken.
     * The format of the data depends on:
     * - the context of the callback
     * - Camera.Parameters settings
     *
     * @param bytes A byte array of the picture data.
     * @param camera The Camera service object.
     */
    @Override
    public void onPictureTaken(byte[] bytes, Camera camera) {
        // Get a File to save the taken picture.
        File imageFile = getOutputMediaFile();
        // Check if the image file was created.
        if (imageFile == null) {
            Toast.makeText(MainActivity.this, "Failed to create an Image File.", Toast.LENGTH_LONG).show();
            return;
        }
        try {
            // Write the image data into the file.
            FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
            fileOutputStream.write(bytes);
            fileOutputStream.close();
            Toast.makeText(MainActivity.this, "Image saved: " + imageFile.getName(), Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            Log.e(LOG_TAG, e.getMessage());
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage());
        }
        // Refresh camera to continue preview.
        mCameraView.refreshCamera(mCamera);
    }

    /**
     * Helper Method.
     * <p>
     * Create a media File for saving an image.
     *
     * @return The file that the captured image will be saved to.
     */
    private File getOutputMediaFile() {
        // This location works best if you want the created images to be shared between applications.
        File cameraBasicsStorageDir = new File(Environment.getExternalStorageDirectory(), STORAGE_DIRECTORY_NAME);
        // Create the storage directory if it does not exist.
        if (!cameraBasicsStorageDir.exists()) {
            if (!cameraBasicsStorageDir.mkdir()) {
                Log.e(LOG_TAG, "Failed to create directory: " + STORAGE_DIRECTORY_NAME);
                return null;
            }
        }
        // Create a media file name: take the current timeStamp.
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        // Create a new File instance.
        File mediaFile = new File(cameraBasicsStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");

        return mediaFile;
    }
}
