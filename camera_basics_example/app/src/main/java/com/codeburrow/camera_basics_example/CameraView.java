package com.codeburrow.camera_basics_example;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * @author George Spiridakis <george@codeburrow.com>
 * @since Jul/04/2016.
 * ===================================================
 * ---------->    http://codeburrow.com    <----------
 * ===================================================
 */
/*
 * CameraView receives camera data and display them.
 */
public class CameraView extends SurfaceView implements SurfaceHolder.Callback {

    private static final String LOG_TAG = CameraView.class.getSimpleName();

    /*
     * SurfaceHolder: Abstract interface to someone holding a display surface.
     * Allows you to control:
     * - the surface size and format
     * - edit the pixels in the surface
     * - monitor changes to the surface
     * This interface is typically available through the SurfaceView class.
     */
    private SurfaceHolder mSurfaceHolder;
    /*
     * The Camera class is used to:
     * - set image capture settings
     * - start/stop preview
     * - snap pictures
     * - retrieve frames for encoding for video
     * This class is a client for the Camera service,
     * which manages the actual camera hardware.
     */
    private Camera mCamera;

    public CameraView(Context context, Camera camera) {
        super(context);
        mCamera = camera;
        // Get the SurfaceHolder providing access
        // and control over this SurfaceView's underlying surface.
        mSurfaceHolder = getHolder();
        // Add a callback interface for this holder.
        mSurfaceHolder.addCallback(this);
        // Deprecated setting, but required on Android versions prior to 3.0.
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    /**
     * This is called immediately after the surface is first created.
     * Implementations of this should start up whatever rendering code they desire.
     * <p/>
     * Note:
     * Only one thread can ever draw into a Surface,
     * so you should not draw into the Surface here
     * if your normal rendering will be in another thread.
     *
     * @param surfaceHolder The SurfaceHolder whose surface is being created.
     */
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Log.e(LOG_TAG, "===== surfaceCreated =====");
        try {
            // Set the Surface to be used for live preview.
            mCamera.setPreviewDisplay(surfaceHolder);
            // Start capturing and drawing preview frames to the screen.
            mCamera.startPreview();
        } catch (IOException e) {
            Log.e(LOG_TAG, "CameraView Error on surfaceCreated:\n" + e.getMessage());
        }
    }

    /**
     * This is called immediately after any structural changes(format or size)
     * have been made to the surface.
     * You should at this point update the imagery in the surface.
     * This method is always called at least once, after surfaceCreated(SurfaceHolder).
     *
     * @param surfaceHolder The SurfaceHolder whose surface has changed.
     * @param format        The new PixelFormat of the surface.
     * @param width         The new width of the surface.
     * @param height        The new height of the surface.
     */
    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
        Log.e(LOG_TAG, "===== surfaceChanged =====");
        Log.e(LOG_TAG, "format: " + String.valueOf(format) + ", " + "width: " + String.valueOf(width) + ", " + "height: " + String.valueOf(height));

        refreshCamera(mCamera, surfaceHolder, width, height);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    }

    private void refreshCamera(Camera camera, SurfaceHolder surfaceHolder, int width, int height) {
        // Check if preview surface exists.
        if (surfaceHolder.getSurface() == null) return;

        try {
            // Stop capturing and drawing preview frames to the surface,
            // and resets the camera for a future call to startPreview().
            mCamera.stopPreview();
        } catch (Exception e) {
            Log.e(LOG_TAG, "Tried to stop a non-existed preview");
        }

        try {
            // Set the Surface to be used for live preview.
            mCamera.setPreviewDisplay(surfaceHolder);
            // Start capturing and drawing preview frames to the screen.
            mCamera.startPreview();
        } catch (IOException e) {
            Log.e(LOG_TAG, "CameraView Error on surfaceCreated:\n" + e.getMessage());
        }
    }
}
