package com.example.george.nfc_tag_reader_example;

import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * @author George Spiridakis <george@codeburrow.com>
 * @since May/18/2016.
 * ===================================================
 * ---------->    http://codeburrow.com    <----------
 * ===================================================
 */
public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private TextView mNfcStatusTextView;
    private TextView mMessageTextView;

    private NfcAdapter mNfcAdapter;
    private String mNfcStatus = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the text views.
        mNfcStatusTextView = (TextView) findViewById(R.id.nfc_status_text_view);
        mMessageTextView = (TextView) findViewById(R.id.message_text_view);

        // Get the  NFC Adapter.
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        // Check if the device has an NFC Adapter.
        if (null == mNfcAdapter) {
            mNfcStatus += getString(R.string.nfc_status_not_supported);
            return;
        } else {
            mNfcStatus += getString(R.string.nfc_status_supported);
        }

        if (mNfcAdapter.isEnabled()) {
            mNfcStatus += "\n" + getString(R.string.nfc_status_enabled);
        } else {
            mNfcStatus += "\n" + getString(R.string.nfc_status_disabled);
        }

        mNfcStatusTextView.setText(mNfcStatus);
    }

}
