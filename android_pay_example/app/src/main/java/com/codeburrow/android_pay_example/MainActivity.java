package com.codeburrow.android_pay_example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wallet.Cart;
import com.google.android.gms.wallet.FullWallet;
import com.google.android.gms.wallet.FullWalletRequest;
import com.google.android.gms.wallet.LineItem;
import com.google.android.gms.wallet.MaskedWallet;
import com.google.android.gms.wallet.MaskedWalletRequest;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;
import com.google.android.gms.wallet.fragment.BuyButtonText;
import com.google.android.gms.wallet.fragment.Dimension;
import com.google.android.gms.wallet.fragment.SupportWalletFragment;
import com.google.android.gms.wallet.fragment.WalletFragmentInitParams;
import com.google.android.gms.wallet.fragment.WalletFragmentMode;
import com.google.android.gms.wallet.fragment.WalletFragmentOptions;
import com.google.android.gms.wallet.fragment.WalletFragmentStyle;

/**
 * ======>    The Transaction Flow    <======
 * <p/>
 * 1. Request a Masked Wallet object
 * 2. Get a Masked Wallet object
 * 3. Request a Full Wallet object
 * 4. Get a Full Wallet object
 */
public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    // GoogleApiClient instance variable
    private GoogleApiClient mGoogleApiClient;
    // SupportWalletFragment instance variable
    private SupportWalletFragment mWalletFragment;
    // MaskedWallet instance variable
    private MaskedWallet mMaskedWallet;
    // Request code constant
    public static final int MASKED_WALLET_REQUEST_CODE = 888;
    // FullWallet instance variable
    private FullWallet mFullWallet;
    // Request code constant
    public static final int FULL_WALLET_REQUEST_CODE = 889;
    // Constant to contain the WalletFragment's tag
    public static final String WALLET_FRAGMENT_ID = "wallet_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check if WalletFragment exists.
        mWalletFragment = (SupportWalletFragment) getSupportFragmentManager()
                .findFragmentByTag(WALLET_FRAGMENT_ID);

        // If already exists in the Activity, don't add WalletFragment twice.
        if (mWalletFragment == null) {

            // Wallet fragment style.
            WalletFragmentStyle walletFragmentStyle = new WalletFragmentStyle()
                    .setBuyButtonText(BuyButtonText.BUY_NOW)
                    .setBuyButtonWidth(Dimension.MATCH_PARENT);

            // Wallet fragment options.
            WalletFragmentOptions walletFragmentOptions = WalletFragmentOptions.newBuilder()
                    .setEnvironment(WalletConstants.ENVIRONMENT_SANDBOX)
                    .setFragmentStyle(walletFragmentStyle)
                    .setTheme(WalletConstants.THEME_LIGHT)
                    .setMode(WalletFragmentMode.BUY_BUTTON)
                    .build();

            // Instantiate the WalletFragment.
            mWalletFragment = SupportWalletFragment.newInstance(walletFragmentOptions);

            // Initialize the WalletFragment.
            WalletFragmentInitParams.Builder startParamsBuilder =
                    WalletFragmentInitParams.newBuilder()
                            .setMaskedWalletRequest(generateMaskedWalletRequest())
                            .setMaskedWalletRequestCode(MASKED_WALLET_REQUEST_CODE)
                            .setAccountName("Google I/O Codelab");
            mWalletFragment.initialize(startParamsBuilder.build());

            // Add the WalletFragment to the UI.
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.wallet_button_holder, mWalletFragment, WALLET_FRAGMENT_ID)
                    .commit();
        }

        buildGoogleApiClient();
    }

    /**
     * Helper Method
     * <p/>
     * Build the Google Api Client.
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Wallet.API, new Wallet.WalletOptions.Builder()
                        .setEnvironment(WalletConstants.ENVIRONMENT_SANDBOX)
                        .setTheme(WalletConstants.THEME_LIGHT)
                        .build())
                .build();
    }

    @Override
    public void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {
        // GoogleApiClient is connected, we don't need to do anything here
    }

    @Override
    public void onConnectionSuspended(int cause) {
        // GoogleApiClient is temporarily disconnected, no action needed
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // GoogleApiClient failed to connect, we should log the error and retry
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /**
         * Check to see if the request code is for your Masked Wallet request
         * and store the resulting response
         */
        switch (requestCode) {
            case MASKED_WALLET_REQUEST_CODE:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        mMaskedWallet = data
                                .getParcelableExtra(WalletConstants.EXTRA_MASKED_WALLET);
                        Toast.makeText(this, "Got Masked Wallet", Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        // The user canceled the operation
                        break;
                    case WalletConstants.RESULT_ERROR:
                        Toast.makeText(this, "An Error Occurred", Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
            case FULL_WALLET_REQUEST_CODE:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        mFullWallet = data
                                .getParcelableExtra(WalletConstants.EXTRA_FULL_WALLET);
                        // Once you have the Full Wallet object, you have the one-time payment credentials.
                        // You can access the credit card number with the following call: mFullWallet.getProxyCard().getPan();
                        // Show the credit card number
                        Toast.makeText(this,
                                mFullWallet.getProxyCard().getPan().toString(),
                                Toast.LENGTH_SHORT).show();
                        break;
                    case WalletConstants.RESULT_ERROR:
                        Toast.makeText(this, "An Error Occurred", Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
        }
    }

    /**
     * Helper Method
     * <p/>
     * Create the Masked Wallet Request.
     * <p/>
     * The Masked Wallet Request defines the information about the order
     * and what you're requesting from the customer.
     *
     * @return MaskedWalletRequest
     */
    private MaskedWalletRequest generateMaskedWalletRequest() {
        // Here we're defining that we want:
        // the user's phone number,
        // shipping address and that they're purchasing a Google I/O sticker
        // for approximately $15 (including tax and shipping)
        MaskedWalletRequest maskedWalletRequest =
                MaskedWalletRequest.newBuilder()
                        .setMerchantName("Google I/O Codelab")
                        .setPhoneNumberRequired(true)
                        .setShippingAddressRequired(true)
                        .setCurrencyCode("USD")
                        .setCart(Cart.newBuilder()
                                .setCurrencyCode("USD")
                                .setTotalPrice("10.00")
                                .addLineItem(LineItem.newBuilder()
                                        .setCurrencyCode("USD")
                                        .setDescription("Google I/O Sticker")
                                        .setQuantity("1")
                                        .setUnitPrice("10.00")
                                        .setTotalPrice("10.00")
                                        .build())
                                .build())
                        .setEstimatedTotalPrice("15.00")
                        .build();

        return maskedWalletRequest;
    }

    // Just like with Masked Wallet, you need to create a request object to get a Full Wallet.

    /**
     * Helper Method
     * <p/>
     * Generate the Full Wallet Request.
     * <p/>
     * When you request a Full Wallet,
     * you get a one-time card from Google and the user’s full billing and shipping information.
     * This card is backed by the user’s secured payment credentials.
     *
     * @param googleTransactionId Can be found in the Masked Wallet Response
     * @return FullWalletRequest
     */
    private FullWalletRequest generateFullWalletRequest(String googleTransactionId) {
        // You'll also want to add information about the purchase including the exact amount you will be charging for.
        // Here we have a $10.00 sticker with $0.10 tax
        FullWalletRequest fullWalletRequest = FullWalletRequest.newBuilder()
                .setGoogleTransactionId(googleTransactionId)
                .setCart(Cart.newBuilder()
                        .setCurrencyCode("USD")
                        .setTotalPrice("10.10")
                        .addLineItem(LineItem.newBuilder()
                                .setCurrencyCode("USD")
                                .setDescription("Google I/O Sticker")
                                .setQuantity("1")
                                .setUnitPrice("10.00")
                                .setTotalPrice("10.00")
                                .build())
                        .addLineItem(LineItem.newBuilder()
                                .setCurrencyCode("USD")
                                .setDescription("Tax")
                                .setRole(LineItem.Role.TAX)
                                .setTotalPrice(".10")
                                .build())
                        .build())
                .build();
        return fullWalletRequest;
    }

    public void requestFullWallet(View view) {
        if (mMaskedWallet == null) {
            Toast.makeText(this, "No masked wallet, can't confirm.", Toast.LENGTH_SHORT).show();
            return;
        }
        Wallet.Payments.loadFullWallet(
                mGoogleApiClient,
                generateFullWalletRequest(mMaskedWallet.getGoogleTransactionId()),
                FULL_WALLET_REQUEST_CODE);
    }
}
