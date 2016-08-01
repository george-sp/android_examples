package com.codeburrow.custom_view_basics_example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private ShapeModifierView mShapeModifierView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mShapeModifierView = (ShapeModifierView) findViewById(R.id.shape_modifier_view);
    }

    public void onGetShapeButtonClicked(View view) {
        Toast.makeText(MainActivity.this, mShapeModifierView.getShapeName(), Toast.LENGTH_SHORT).show();
    }
}
