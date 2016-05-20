package com.example.george.nfc_tag_writer_example;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author George Spiridakis <george@codeburrow.com>
 * @since May/19/2016.
 * ===================================================
 * ---------->    http://codeburrow.com    <----------
 * ===================================================
 */
public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    EditText mEditMessage;

    NfcAdapter mNfcAdapter;
    boolean mWriteMode;
    PendingIntent mPendingIntent;
    IntentFilter mIntentFilters[];
    Tag mTag;
    int mCount = 0;

    Ndef mNdef;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditMessage = (EditText) findViewById(R.id.edit_message);

        mContext = this;

        mNfcAdapter = NfcAdapter.getDefaultAdapter(mContext);
        mPendingIntent = PendingIntent.getActivity(mContext, 0, new Intent(mContext, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        mIntentFilters = new IntentFilter[]{tagDetected};
    }

    @Override
    public void onPause() {
        super.onPause();
        WriteModeOff();
    }

    @Override
    public void onResume() {
        super.onResume();
        WriteModeOn();
    }

    /**
     * Helper Method
     */
    private void WriteModeOn() {
        mWriteMode = true;
        if (mNfcAdapter != null) {
            mNfcAdapter.enableForegroundDispatch(MainActivity.this, mPendingIntent, mIntentFilters, null);
        }
    }

    /**
     * Helper Method
     */
    private void WriteModeOff() {
        mWriteMode = false;
        if (mNfcAdapter != null) {
            mNfcAdapter.disableForegroundDispatch(MainActivity.this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e(LOG_TAG, "onNewIntent: " + ++mCount);
        handleIntent(intent);
    }

    public void handleIntent(Intent intent) {
        String action = intent.getAction();

        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)) {
            mTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

            String infoMessage = getString(R.string.ok_detection) + "\n" + mTag.toString() + ",\nIntent: " + intent + "\nIs writable: " + writableTag();
            Log.e(LOG_TAG, infoMessage);
            Toast.makeText(MainActivity.this, infoMessage, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Helper Method
     *
     * @return true if the tag is writable
     */
    private boolean writableTag() {
//        mTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        try {
            mNdef = Ndef.get(mTag);
            if (mNdef != null) {
                mNdef.connect();
                if (!mNdef.isWritable()) {
                    mNdef.close();
                    Toast.makeText(mContext, "Tag is read-only.", Toast.LENGTH_SHORT).show();
                    Log.e(LOG_TAG, "Tag is read-only");
                    return false;
                }
                mNdef.close();
                return true;
            } else {
                // NDEF is not supported by this Tag.
            }
        } catch (Exception e) {
            Toast.makeText(mContext, "Failed to read tag", Toast.LENGTH_SHORT).show();
            Log.e(LOG_TAG, "Failed to read tag: " + e.getMessage());
        }

        return false;
    }

    public void writeToTag(View view) {
        new NdefWriterTask().execute();
    }

    private class NdefWriterTask extends AsyncTask<Void, Void, Boolean> {

        private final String LOG_TAG = NdefWriterTask.class.getSimpleName();

        @Override
        protected Boolean doInBackground(Void... params) {
            return writeText();
        }

        private boolean writeText() {
            try {
                NdefRecord[] records = {createRecord(mEditMessage.getText().toString())};
                NdefMessage ndefMessage = new NdefMessage(records);

                mNdef.connect();
                mNdef.writeNdefMessage(ndefMessage);
                mNdef.close();

                return true;
            } catch (FormatException e) {
                Log.e(LOG_TAG, e.getMessage());
            } catch (UnsupportedEncodingException e) {
                Log.e(LOG_TAG, e.getMessage());
            } catch (IOException e) {
                Log.e(LOG_TAG, e.getMessage());
            }

            return false;
        }

        private NdefRecord createRecord(String text) throws UnsupportedEncodingException {
            String lang = "en";
            byte[] textBytes = text.getBytes();
            byte[] langBytes = lang.getBytes("US-ASCII");
            int langLength = langBytes.length;
            int textLength = textBytes.length;
            byte[] payload = new byte[1 + langLength + textLength];

            // Set status byte (see NDEF spec for actual bits)
            payload[0] = (byte) langLength;

            // Copy langbytes and textbytes into payload
            System.arraycopy(langBytes, 0, payload, 1, langLength);
            System.arraycopy(textBytes, 0, payload, 1 + langLength, textLength);

            NdefRecord ndefRecord = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], payload);

            return ndefRecord;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            String result = success ? getString(R.string.ok_writing) : getString(R.string.error_writing);

            Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
            Log.e(LOG_TAG, result);
        }
    }
}
