package com.codeburrow.transitions_example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TransitionActivity extends AppCompatActivity {

    private static final String LOG_TAG = TransitionActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new TransitionFragment())
                    .commit();
        }
    }
}
