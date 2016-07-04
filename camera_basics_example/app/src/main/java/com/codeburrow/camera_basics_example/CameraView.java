package com.codeburrow.camera_basics_example;

import android.content.Context;
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

    public CameraView(Context context) {
        super(context);
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
