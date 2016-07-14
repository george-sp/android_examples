package com.codeburrow.intents_basic_example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ActivityBlue extends AppCompatActivity {

    private static final String LOG_TAG = ActivityBlue.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue);
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
}
