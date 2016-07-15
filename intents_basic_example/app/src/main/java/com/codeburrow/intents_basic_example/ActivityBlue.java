package com.codeburrow.intents_basic_example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class ActivityBlue extends AppCompatActivity {

    private static final String LOG_TAG = ActivityBlue.class.getSimpleName();
    private EditText mEditText;
    public static String USER_TEXT_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue);

        mEditText = (EditText) findViewById(R.id.edit_text);
    }

    public void launchActivityRed(View view) {
        /*
         * Explicit Intent:
         *
             *              An application can define the target component
         *                  directly in the intent.
         *
         * An explicit intent is most commonly used when launching an activity within the same application.
         */
        // The intent to start.
        Intent launchActivityRedIntent = new Intent(ActivityBlue.this, ActivityRed.class);
        // Launch ActivityRed activity.
        startActivity(launchActivityRedIntent);
    }

    public void stopCustomService(View view) {
        // Request that a given application service be stopped.
        // If the service is not running, nothing happens.
        stopService(new Intent(ActivityBlue.this, CustomService.class));
    }

    public void launchActivityRedDataTransfer(View view) {
        Intent launchActivityRedIntent = new Intent(ActivityBlue.this, ActivityRed.class);
        /*
         * Add extended data to the intent.
         *
         */
        launchActivityRedIntent.putExtra(USER_TEXT_KEY, mEditText.getText().toString());
        startActivity(launchActivityRedIntent);
    }
}
