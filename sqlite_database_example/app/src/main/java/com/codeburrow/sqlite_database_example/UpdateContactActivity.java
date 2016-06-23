package com.codeburrow.sqlite_database_example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateContactActivity extends AppCompatActivity {

    private static final String LOG_TAG = UpdateContactActivity.class.getSimpleName();

    // EditText to display the contact's ID.
    private EditText mIdEditText;
    // EditTexts to manipulate the contact's information.
    private EditText mNameEditText;
    private EditText mPhoneNumberEditText;
    // The returned Contact object.
    private ContactDAO mContactDAO;
    // Database Helper to manage the SQLiteDatabase.
    private ContactsDBHelper contactsDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contact);

        // Retrieve the ContactDAO object from the intent.
        mContactDAO = (ContactDAO) getIntent().getSerializableExtra(MainActivity.CONTACT_EXTRA_KEY);

        // Find EditTexts in the appropriate layout.
        mIdEditText = (EditText) findViewById(R.id.id_edit_text);
        mNameEditText = (EditText) findViewById(R.id.name_edit_text);
        mPhoneNumberEditText = (EditText) findViewById(R.id.phone_number_edit_text);

        // Update EditTexts to display Contact's info to the user.
        mIdEditText.setText(String.valueOf(mContactDAO.getId()));
        mNameEditText.setText(mContactDAO.getName());
        mPhoneNumberEditText.setText(mContactDAO.getPhoneNumber());

        // Initialize ContactsDBHelper.
        contactsDBHelper = new ContactsDBHelper(this);
    }

    public void editContact(View view) {
        try {
            // Get contact's ID, Name & PhoneNumber from the EditTexts.
            int contactId = Integer.parseInt(mIdEditText.getText().toString());
            String contactName = mNameEditText.getText().toString();
            String contactPhoneNumber = mPhoneNumberEditText.getText().toString();
            // Create ContactDAO object.
            mContactDAO = new ContactDAO(contactId, contactName, contactPhoneNumber);
            // Update this contact in the Database.
            int updateResult = contactsDBHelper.updateContact(mContactDAO);
            // Create a Toast to notify the user about the Database Insert.
            Toast.makeText(UpdateContactActivity.this, updateResult + "", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
            Toast.makeText(UpdateContactActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
