package com.codeburrow.camera_basics_example;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
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
    private int mRotation;

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
        setCameraDisplayOrientation(this, mCameraId, mCamera);
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

    /**
     * Helper Method.
     * <p/>
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
        mRotation = activity.getWindowManager().getDefaultDisplay().getRotation();

        // Get the degrees of the current Surface rotation.
        int degrees = 0;
        switch (mRotation) {
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

    /**
     * Called when image data is available after a picture is taken.
     * The format of the data depends on:
     * - the context of the callback
     * - Camera.Parameters settings
     *
     * @param data   A byte array of the picture data.
     * @param camera The Camera service object.
     */
    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        // Get a File to save the taken picture.
        File imageFile = getOutputMediaFile();
        // Check if the image file was created.
        if (imageFile == null) {
            Toast.makeText(MainActivity.this, "Failed to create an Image File.", Toast.LENGTH_LONG).show();
            return;
        }
        try {
            /*
             * A file output stream is an output stream for writing data
             * to a File or to a FileDescriptor.
             *
             * FileOutputStream is meant for writing streams of raw bytes such as image data.
             */
            // Creates a file output stream to write to the file represented by the specified File object.
            FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
            // Get the absolute width of the available display size in pixels.
            int screenWidth = getResources().getDisplayMetrics().widthPixels;
            // Get the absolute width of the available display size in pixels.
            int screenHeight = getResources().getDisplayMetrics().heightPixels;
            /*
             * BitmapFactory: Creates Bitmap objects from various sources,
             * including: files, streams, and byte-arrays.
             */
            // Decode an immutable bitmap from the specified byte array.
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            // Rotate the decoded bitmap before save it.
            bitmap = rotateTakenPicture(bitmap, screenWidth, screenHeight);
            // Write a compressed version of the bitmap to the specified output stream.
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            // Close this file output stream and releases any system resources associated with this stream.
            fileOutputStream.close();
            // Inform the user.
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
     * <p/>
     * Rotates the taken picture.
     *
     * @param bitmap       The bitmap to be rotated.
     * @param screenWidth  The screen's width.
     * @param screenHeight The screen's height.
     * @return
     */
    private Bitmap rotateTakenPicture(Bitmap bitmap, int screenWidth, int screenHeight) {
        // Check the overall orientation of the screen.
        // PORTRAIT MODE
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            /*
             * Note: Width and Height are reversed.
             */
            // Create a new bitmap, scaled from an existing bitmap, when possible.
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, screenHeight, screenWidth, true);
            // Return the bitmap's width.
            int scaledWidth = scaledBitmap.getWidth();
            // Return the bitmap's height.
            int scaledHeight = scaledBitmap.getHeight();
            /*
             * The Matrix class holds a 3x3 matrix for transforming coordinates.
             */
            // Create an identity matrix.
            Matrix matrix = new Matrix();
            // Check which camera (back or front facing) is used.
            if (mCameraId == 0) {
                // Postconcat the matrix with the specified rotation - 90 degrees.
                matrix.postRotate(90);
            } else if (mCameraId == 1) {
                matrix.postRotate(270);
            }
            // Return an immutable rotated bitmap from the specified subset of the source bitmap.
            bitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledWidth, scaledHeight, matrix, true);
        }
        // LANDSCAPE MODE
        else {
            // There is no need to reverse width and height, in landscape mode.
            if (mRotation == Surface.ROTATION_270) {
                // Create an identity matrix.
                Matrix matrix = new Matrix();
                // Postconcat the matrix with the specified rotation - 180 degrees.
                matrix.postRotate(180);
                // Return an immutable rotated bitmap from the specified subset of the source bitmap.
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, screenWidth, screenHeight, matrix, true);
            } else {
                // Create a new bitmap, scaled from an existing bitmap, when possible.
                bitmap = Bitmap.createScaledBitmap(bitmap, screenWidth, screenHeight, true);
            }
        }
        // Return the rotated bitmap.
        return bitmap;
    }

    /**
     * Helper Method.
     * <p/>
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
