package com.codeburrow.sqlite_database_example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddContactActivity extends AppCompatActivity {

    private static final String LOG_TAG = AddContactActivity.class.getSimpleName();

    // EditText to get the contact's information.
    private EditText mNameEditText;
    private EditText mPhoneNumberEditText;
    // Database Helper to manage the SQLiteDatabase.
    private ContactsDBHelper contactsDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        // Find EditTexts in the appropriate layout.
        mNameEditText = (EditText) findViewById(R.id.name_edit_text);
        mPhoneNumberEditText = (EditText) findViewById(R.id.phone_number_edit_text);

        // Initialize ContactsDBHelper.
        contactsDBHelper = new ContactsDBHelper(this);
    }

    public void addContact(View view) {
        try {
            // Get contact's Name and Phone Number from the EditTexts.
            String contactName = mNameEditText.getText().toString();
            String contactPhoneNumber = mPhoneNumberEditText.getText().toString();
            // Create ContactDAO object.
            ContactDAO contact = new ContactDAO(contactName, contactPhoneNumber);
            // Insert into Database through ContactsDBHelper.
            long insertResult = contactsDBHelper.createContact(contact);
            // Create a Toast to notify the user about the Database Insert.
            Toast.makeText(AddContactActivity.this, insertResult + "", Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            Toast.makeText(AddContactActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e(LOG_TAG, e.getMessage());
        }
    }
}
