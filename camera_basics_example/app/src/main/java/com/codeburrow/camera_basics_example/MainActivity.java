package com.codeburrow.camera_basics_example;

import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private Camera mCamera;
    private CameraView mCameraView;
    private FrameLayout mCameraViewFrameLayout;
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
        mCameraViewFrameLayout = (FrameLayout) findViewById(R.id.camera_view_frame_layout);
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

    /**
     * Helper Method.
     * <p/>
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
    }

    /**
     * Heleper Method.
     * <p/>
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
     * <p/>
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

    /**
     * Helper Method.
     * <p/>
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
     * <p/>
     * Releases camera and sets mCamera object to null.
     */
    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }
}
