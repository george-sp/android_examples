package com.example.george.nfc_tag_reader_example;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

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

        handleIntent(getIntent());
    }

    /**
     * Helper Method
     * <p/>
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
     * <p/>
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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            String type = intent.getType();
            if (MIME_TEXT_PLAIN.equals(type)) {
                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

                new NdefReaderTask().execute(tag);
            } else {
                Log.e(LOG_TAG, "Wrong mime type: " + type);
            }
        } else if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {
            // In case we still use the ACTION_TECH_DISCOVERED intent.
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String[] techList = tag.getTechList();
            String searchedTech = Ndef.class.getName();

            for (String tech : techList) {
                if (searchedTech.equals(tech)) {

                    new NdefReaderTask().execute(tag);
                    break;
                }
            }
        }
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


    private class NdefReaderTask extends AsyncTask<Tag, Void, String> {

        @Override
        protected String doInBackground(Tag... params) {
            Tag tag = params[0];

            Ndef ndef = Ndef.get(tag);
            if (ndef == null) {
                // NDEF is not supported by this Tag.
                return null;
            }

            NdefMessage ndefMessage = ndef.getCachedNdefMessage();

            NdefRecord[] records = ndefMessage.getRecords();

            for (NdefRecord ndefRecord : records) {
                if (ndefRecord.getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT)) {
                    try {
                        return readText(ndefRecord);
                    } catch (UnsupportedEncodingException e) {
                        Log.e(LOG_TAG, "Unsupported Encoding", e);
                    }
                }
            }

            return null;
        }

        private String readText(NdefRecord record) throws UnsupportedEncodingException {
        /*
         * See NFC forum specification for "Text Record Type Definition" at 3.2.1
         *
         * http://www.nfc-forum.org/specs/
         *
         * bit_7 defines encoding
         * bit_6 reserved for future use, must be 0
         * bit_5..0 length of IANA language code
         */

            byte[] payload = record.getPayload();

            // Get the Text Encoding
            String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";

            // Get the Language Code
            int languageCodeLength = payload[0] & 0063;

            // String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");
            // e.g. "en"

            // Get the Text
            return new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                mMessageTextView.setText(result);
            }
        }
    }

}