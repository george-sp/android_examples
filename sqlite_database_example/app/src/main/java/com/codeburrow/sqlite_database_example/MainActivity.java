package com.codeburrow.sqlite_database_example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Show menu button.
        makeActionOverflowMenuShown();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.add_contact:
                // Start the AddContact Activity.
                return true;
            default:
                return false;
        }
    }

    /**
     * HELPER METHOD
     * <p/>
     * Show the 3-dots overflow menu button,
     * irrespective of device's physical menu button.
     */
    private void makeActionOverflowMenuShown() {
        // Devices with hardware menu button don't show action overflow menu.
        try {
            ViewConfiguration viewConfiguration = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(viewConfiguration, false);
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getLocalizedMessage());
        }
    }
}
