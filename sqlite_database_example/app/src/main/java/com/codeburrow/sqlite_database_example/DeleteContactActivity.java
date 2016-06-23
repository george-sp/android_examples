package com.codeburrow.sqlite_database_example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class DeleteContactActivity extends AppCompatActivity {

    private static final String LOG_TAG = DeleteContactActivity.class.getSimpleName();

    // EditText to get the contact's ID.
    private EditText mIdEditText;
    // Database Helper to manage the SQLiteDatabase.
    private ContactsDBHelper contactsDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_contact);

        // Find EditText in the appropriate layout.
        mIdEditText = (EditText) findViewById(R.id.id_edit_text);
        // Initialize ContactsDBHelper.
        contactsDBHelper = new ContactsDBHelper(this);
    }

    public void deleteContact(View view) {
        try {
            // Get contact's ID from the EditText.
            int contactId = Integer.parseInt(mIdEditText.getText().toString());
            // Query the Database for that contact ID.
            int deleteResult = contactsDBHelper.deleteContact(contactId);
            // Create a Toast to notify the user about the Database Insert.
            Toast.makeText(DeleteContactActivity.this, deleteResult + "", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
            Toast.makeText(DeleteContactActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
