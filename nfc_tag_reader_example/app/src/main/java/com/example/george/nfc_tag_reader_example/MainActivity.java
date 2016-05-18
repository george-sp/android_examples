package com.example.george.nfc_tag_reader_example;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
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
    public static final String MIME_TEXT_PLAIN = "text/plain";

    private TextView mNfcStatusTextView;
    private TextView mMessageTextView;

    private NfcAdapter mNfcAdapter;
    private boolean mNfcAdapterSupported;
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

        mNfcAdapterSupported = isNfcAdapterSupported();

        if (mNfcAdapterSupported) {
            isNfcAdapterEnabled();
        }


        mNfcStatusTextView.setText(mNfcStatus);
    }

    /**
     * Helper Method
     *
     * Check if the NFC Adapter is enabled.
     */
    private void isNfcAdapterEnabled() {
        if (mNfcAdapter.isEnabled()) {
            mNfcStatus += "\n" + getString(R.string.nfc_status_enabled);
        } else {
            mNfcStatus += "\n" + getString(R.string.nfc_status_disabled);
        }
    }

    /**
     * Helper Method
     *
     * Check if the device has an NFC Adapter.
     *
     * @return true if the device has an NFC adapter
     */
    private boolean isNfcAdapterSupported() {
        if (null == mNfcAdapter) {
            mNfcStatus += getString(R.string.nfc_status_not_supported);
            return false;
        } else {
            mNfcStatus += getString(R.string.nfc_status_supported);
            return true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mNfcAdapterSupported) setupForegroundDispatch(this, mNfcAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mNfcAdapterSupported) stopForegroundDispatch(this, mNfcAdapter);
    }

    /**
     * Helper Method
     *
     * @param activity   the Activity to dispatch to.
     * @param nfcAdapter the NFC Adapter used for the foreground dispatch.
     */
    public static void setupForegroundDispatch(final Activity activity, NfcAdapter nfcAdapter) {
        final Intent intent = new Intent(activity.getApplicationContext(), activity.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        final PendingIntent pendingIntent = PendingIntent.getActivity(activity.getApplicationContext(), 0, intent, 0);

        IntentFilter[] filters = new IntentFilter[1];
        String[][] techList = new String[][]{};

        /*
         * Note: Use the same intent-filter as in your AndroidManifest.xml.
         */
        filters[0] = new IntentFilter();
        filters[0].addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        filters[0].addCategory(Intent.CATEGORY_DEFAULT);
        try {
            filters[0].addDataType(MIME_TEXT_PLAIN);
        } catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("Check your mime type." + e.getMessage());
        }

        nfcAdapter.enableForegroundDispatch(activity, pendingIntent, filters, techList);
    }

    /**
     * Helper Method
     *
     * @param activity   the Activity requesting to disable the foreground dispatch.
     * @param nfcAdapter the NFC Adapter used for the foreground dispatch.
     */
    public static void stopForegroundDispatch(final Activity activity, NfcAdapter nfcAdapter) {
        nfcAdapter.disableForegroundDispatch(activity);
    }
}