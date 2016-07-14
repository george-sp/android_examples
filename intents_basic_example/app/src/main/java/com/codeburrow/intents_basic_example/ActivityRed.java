package com.codeburrow.intents_basic_example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * @author George Spiridakis <george@codeburrow.com>
 * @since Jul/15/2016.
 * ===================================================
 * ---------->    http://codeburrow.com    <----------
 * ===================================================
 */
public class ActivityRed extends AppCompatActivity{

    private static final String LOG_TAG = ActivityRed.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red);
    }
}
