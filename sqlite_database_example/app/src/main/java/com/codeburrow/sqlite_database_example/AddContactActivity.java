package com.codeburrow.sqlite_database_example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddContactActivity extends AppCompatActivity {

    private static final String LOG_TAG = AddContactActivity.class.getSimpleName();

    // EditTexts
    private EditText mNameEditText;
    private EditText mPhoneNumberEditText;
    // Database Helper
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
        // Get contact's Name and Phone Number from the EditTexts.
        String contactName = mNameEditText.getText().toString();
        String contactPhoneNumber = mPhoneNumberEditText.getText().toString();
        // Create ContactDAO object.
        ContactDAO contact = new ContactDAO(contactName, contactPhoneNumber);
        // Insert into Database through ContactsDBHelper.
        contactsDBHelper.createContact(contact);
    }
}
