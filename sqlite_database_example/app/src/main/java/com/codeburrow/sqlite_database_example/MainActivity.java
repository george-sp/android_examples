package com.codeburrow.sqlite_database_example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    // ListView to display the contact.
    private ListView mListView;
    // The returned ArrayList.
    private ArrayList<ContactDAO> mContactsList;
    // Database Helper to manage the SQLiteDatabase.
    private ContactsDBHelper contactsDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Show menu button.
        makeActionOverflowMenuShown();
        // Find ListView in the appropriate layout.
        mListView = (ListView) findViewById(R.id.list_view);
        // Initialize ContactsDBHelper.
        contactsDBHelper = new ContactsDBHelper(this);
        // Read All Contacts.
        mContactsList = contactsDBHelper.readAllContacts();
        // Initialize an ArrayList to store the contacts' names.
        ArrayList<String> contactsName = new ArrayList<>();
        // Fill the above ArrayList.
        for (ContactDAO contact : mContactsList) {
            contactsName.add(contact.getName());
        }
        // Set an ArrayAdapter to the ListView.
        mListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactsName));
        // Display the number of contacts to the user.
        Toast.makeText(MainActivity.this, "Number Of Contacts: " + contactsDBHelper.countContacts(), Toast.LENGTH_LONG).show();
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
                startActivity(new Intent(MainActivity.this, AddContactActivity.class));
                return true;
            case R.id.read_contact:
                // Start the ReadContact Activity.
                startActivity(new Intent(MainActivity.this, ReadContactActivity.class));
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
