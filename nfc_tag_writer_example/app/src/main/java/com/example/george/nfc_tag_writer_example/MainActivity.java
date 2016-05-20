package com.example.george.nfc_tag_writer_example;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

            String infoMessage = getString(R.string.ok_detection) + mTag.toString() + ",\n Intent: " + intent;
            Log.e(LOG_TAG, infoMessage);
            Toast.makeText(MainActivity.this, infoMessage, Toast.LENGTH_SHORT).show();
        }
    }

    public void writeToTag(View view) {
    }
}
