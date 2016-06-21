package com.codeburrow.sqlite_database_example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class AddContactActivity extends AppCompatActivity {

    private static final String LOG_TAG = AddContactActivity.class.getSimpleName();

    // EditTexts
    private EditText mIdEditText;
    private EditText mNameEditText;
    private EditText mPhoneNumberEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        // Find EditTexts in the appropriate layout.
        mIdEditText = (EditText) findViewById(R.id.id_edit_text);
        mNameEditText = (EditText) findViewById(R.id.name_edit_text);
        mPhoneNumberEditText = (EditText) findViewById(R.id.phone_number_edit_text);
    }
}
