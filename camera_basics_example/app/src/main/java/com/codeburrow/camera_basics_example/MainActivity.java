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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         * Retrieve the current Window for the Activity or null if it is not visual.
         * Set Window Flag: as long as this window is visible to the user,
         *                  keep the device's screen turned on and bright.
         */
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        try{
            // You can use open(int) to use different cameras.
            mCamera = Camera.open();
        } catch (Exception e){
            Log.e(LOG_TAG, "Failed to get camera: " + e.getMessage());
        }

        if(mCamera != null) {
            // Create a SurfaceView to show camera data.
            mCameraView = new CameraView(this, mCamera);
            // Find the FrameLayout in the activity's layout.
            FrameLayout frameLayout = (FrameLayout)findViewById(R.id.camera_view_frame_layout);
            // Add the SurfaceView to the FrameLayout.
            frameLayout.addView(mCameraView);
        } else {
            Log.e(LOG_TAG, "Failed to create the SurfaceView");
        }

    }
}
