package com.codeburrow.camera_basics_example;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * @author George Spiridakis <george@codeburrow.com>
 * @since Jul/04/2016.
 * ===================================================
 * ---------->    http://codeburrow.com    <----------
 * ===================================================
 */
/*
 * CameraView will receive camera data and display them.
 */
public class CameraView extends SurfaceView implements SurfaceHolder.Callback{

    private static final String LOG_TAG = CameraView.class.getSimpleName();

    /*
     * SurfaceHolder: Abstract interface to someone holding a display surface.
     * Allows you to control:
     * - the surface size and format
     * - edit the pixels in the surface
     * - monitor changes to the surface
     * This interface is typically available through the SurfaceView class.
     */
    private SurfaceHolder mHolder;
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
        mHolder = getHolder();
        // Add a callback interface for this holder.
        mHolder.addCallback(this);
        // Deprecated setting, but required on Android versions prior to 3.0.
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
}
