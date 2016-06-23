package com.codeburrow.sqlite_database_example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ReadContactActivity extends AppCompatActivity {

    private static final String LOG_TAG = ReadContactActivity.class.getSimpleName();

    // EditText to get the contact's ID.
    private EditText mIdEditText;
    // EditTexts to show the contact's information.
    private EditText mNameEditText;
    private EditText mPhoneNumberEditText;
    // The returned Contact object.
    private ContactDAO mContactDAO;
    // Database Helper to manage the SQLiteDatabase.
    private ContactsDBHelper contactsDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_contact);

        // Find EditTexts in the appropriate layout.
        mIdEditText = (EditText) findViewById(R.id.id_edit_text);
        mNameEditText = (EditText) findViewById(R.id.name_edit_text);
        mPhoneNumberEditText = (EditText) findViewById(R.id.phone_number_edit_text);

        // Initialize ContactsDBHelper.
        contactsDBHelper = new ContactsDBHelper(this);
    }

    public void readContact(View view) {
        try {
            // Get contact's ID from the EditText.
            int contactId = Integer.parseInt(mIdEditText.getText().toString());
            // Query the Database for that contact ID.
            mContactDAO = contactsDBHelper.readContact(contactId);
            // Display contact's information on screen.
            mNameEditText.setText(mContactDAO.getName());
            mPhoneNumberEditText.setText(mContactDAO.getPhoneNumber());
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
            Toast.makeText(ReadContactActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
