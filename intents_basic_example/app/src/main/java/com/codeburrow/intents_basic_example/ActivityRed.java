package com.codeburrow.intents_basic_example;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

/**
 * @author George Spiridakis <george@codeburrow.com>
 * @since Jul/15/2016.
 * ===================================================
 * ---------->    http://codeburrow.com    <----------
 * ===================================================
 */
public class ActivityRed extends AppCompatActivity {

    private static final String LOG_TAG = ActivityRed.class.getSimpleName();
    public static String ACTIVITY_FOR_RESULT_KEY;
    // Request code to be returned in onActivityResult()
    private static final int ACTIVITY_FOR_RESULT_REQUEST_CODE = 1234;
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red);

        mEditText = (EditText) findViewById(R.id.edit_text);

        // Get the extra data in a Bundle object.
        Bundle extras = getIntent().getExtras();
        // Check if there are extra data.
        if (extras != null) {
            String transferredText = extras.getString(ActivityBlue.USER_TEXT_KEY);
            Toast.makeText(ActivityRed.this, transferredText, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ACTIVITY_FOR_RESULT_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String resultString = data.getExtras().getString(ActivityBlue.ACTIVITY_RESULT_KEY);
                Toast.makeText(ActivityRed.this, "RESULT: " + resultString, Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(ActivityRed.this, "RESULT: CANCELED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void launchActivityBlue(View view) {
        /*
         * Create an empty Intent.
         * Explicitly set the component to handle the intent (Usually Optional).
         * - setComponent(ComponentName component)
         * - setClass(Context packageContext, Class<?> class)
         * - setClassName(String packageName, String className)
         * - setClassName(Context packageContext, String className)
         */
        // Create an empty Intent, using the empty constructor.
        Intent launchActivityBlueIntent = new Intent();
        /*
         * setComponent
         * Use setComponent() - explicitly set the component to handle the intent.
         */
        // Create a new component identifier from a Context and class name.
        ComponentName activityBlueComponentName = new ComponentName(this, ActivityBlue.class);
        launchActivityBlueIntent.setComponent(activityBlueComponentName);
        /*
         * setClass
         * Use setClass() - convenience for calling setComponent() with the name returned by a Class object.
         */
//        launchActivityBlueIntent.setClass(this, ActivityBlue.class);
        /*
         * setClassName
         * Use setClassName() - convenience for calling setComponent() with an explicit application package name and class name.
         */
//        launchActivityBlueIntent.setClassName("com.codeburrow.intents_basic_example", "com.codeburrow.intents_basic_example.ActivityBlue");
        // Launch ActivityRed activity.
        startActivity(launchActivityBlueIntent);
    }

    public void startCustomService(View view) {
        // Description of the service to be started.
        Intent customServiceIntent = new Intent(ActivityRed.this, CustomService.class);
        // Request that a given application service be started.
        startService(customServiceIntent);
    }

    public void launchActivityBlueForResult(View view) {
        Intent launchActivityBlueForResultIntent = new Intent(ActivityRed.this, ActivityBlue.class);
        launchActivityBlueForResultIntent.putExtra(ACTIVITY_FOR_RESULT_KEY, true);
        // Launch an Activity for which you would like a result when it finished.
        startActivityForResult(launchActivityBlueForResultIntent, ACTIVITY_FOR_RESULT_REQUEST_CODE);
    }

    public void performActionView(View view) {
        /*
         * Implicit Intent:
         *                  An application does not name the target component,
         *                  but instead declare a general action to perform,
         *                  which allows a component from another app to handle it.
         *
         * An implicit intent is most commonly used when specifying the action to be performed and optionally some data required for that action.
         */
        // Create an intent with a given action and for the given data url.
        Intent actionViewIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://developer.android.com"));
        startActivity(actionViewIntent);
    }

    public void performActionSend(View view) {
        // Create an intent with a given action.
        Intent actionSendIntent = new Intent(Intent.ACTION_SEND);
        // Set an explicit MIME data type.
        /* Multi-Purpose Intent Mail Extensions */
        actionSendIntent.setType("text/plain");
        // Add extended data to the intent.
        actionSendIntent.putExtra(Intent.EXTRA_TEXT, mEditText.getText().toString());
        // Create one more intent. an ACTION_CHOOSER intent.
        Intent chooserIntent = Intent.createChooser(actionSendIntent, "Custom Chooser");
        isIntentSafe(actionSendIntent);
        /* resolveActivity(): returns the Activity component that should be used to handle this intent. */
        ComponentName componentName = actionSendIntent.resolveActivity(getPackageManager());
        Log.e(LOG_TAG, "resolveActivity(): " + componentName.getClassName());
        // Check if there is at an Activity to perform this intent.
        if (actionSendIntent.resolveActivity(getPackageManager()) != null) {
            // Start the chooser activity.
            startActivity(chooserIntent);
        }
    }

    /**
     * Helper Method.
     * <p/>
     * Checks if any activity can be performed from the specified intent.
     *
     * @param intent Intent to be checked.
     * @return boolean: True if any activity was found. Otherwise return False.
     */
    public boolean isIntentSafe(Intent intent) {
        // Get PackageManager instance to find global package information.
        final PackageManager packageManager = getPackageManager();
        // Retrieve all activities that can be performed for the given intent.
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        // Log these activities.
        for (int i = 0; i < activities.size(); i++) {
            String activity = activities.get(i).toString();
            Log.e(LOG_TAG, activity);
        }
        // Return true if one or more activities were found.
        return activities.size() > 0;
    }
}
