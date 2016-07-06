package com.codeburrow.camera_basics_example;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private Camera mCamera;
    private CameraView mCameraView;
    private FrameLayout mCameraViewFrameLayout;

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

        if (mCamera == null) {
            try {
                // You can use open(int) to use different cameras.
                mCamera = Camera.open(0);
            } catch (Exception e) {
                Log.e(LOG_TAG, "Failed to get camera: " + e.getMessage());
            }
            mCameraView.refreshCamera(mCamera);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(LOG_TAG, "----- onPause -----");

        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }
}
