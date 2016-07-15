package com.codeburrow.intents_basic_example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ActivityBlue extends AppCompatActivity {

    private static final String LOG_TAG = ActivityBlue.class.getSimpleName();
    private EditText mEditText;
    public static String USER_TEXT_KEY;
    public static String ACTIVITY_RESULT_KEY;
    private LinearLayout mResultButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue);

        mEditText = (EditText) findViewById(R.id.edit_text);
        mResultButtons = (LinearLayout) findViewById(R.id.result_buttons_linear_layout);
        mResultButtons.setVisibility(View.INVISIBLE);

        // Get the extra data in a Bundle object.
        Bundle extras = getIntent().getExtras();
        // Check if there are extra data.
        if (extras != null) {
            boolean startedForResult = extras.getBoolean(ActivityRed.ACTIVITY_FOR_RESULT_KEY);
            Toast.makeText(this, "Started For Result: " + startedForResult, Toast.LENGTH_SHORT).show();
            // If Activity has to return a result, show the options - buttons.
            if (startedForResult) mResultButtons.setVisibility(View.VISIBLE);
        }
    }

    public void launchActivityRed(View view) {
        /*
         * Explicit Intent:
         *                  An application can define the target component
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

    public void okResult(View view) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(ACTIVITY_RESULT_KEY, "OK");
        // Set the result that ActivityBlue will return to its caller.
        // RESULT_OK: Standard activity result: operation succeeded.
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    public void cancelResult(View view) {
        Intent resultIntent = new Intent();
        // Set the result that ActivityBlue will return to its caller.
        // RESULT_CANCELED: Standard activity result: operation canceled.
        setResult(RESULT_CANCELED, resultIntent);
        finish();
    }
}
