package com.codeburrow.intents_basic_example;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

/**
 * @author George Spiridakis <george@codeburrow.com>
 * @since Jul/15/2016.
 * ===================================================
 * ---------->    http://codeburrow.com    <----------
 * ===================================================
 */
public class ActivityRed extends AppCompatActivity {

    private static final String LOG_TAG = ActivityRed.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red);

        // Get the extra data in a Bundle object.
        Bundle extras = getIntent().getExtras();
        // Check if there are extra data.
        if (extras != null) {
            String transferredText = extras.getString(ActivityBlue.USER_TEXT_KEY);
            Toast.makeText(this, transferredText, Toast.LENGTH_SHORT).show();
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
}
