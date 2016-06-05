package com.codeburrow.android_pay_example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.wallet.Cart;
import com.google.android.gms.wallet.LineItem;
import com.google.android.gms.wallet.MaskedWalletRequest;
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
public class MainActivity extends AppCompatActivity {

    // SupportWalletFragment instance variable
    private SupportWalletFragment mWalletFragment;
    // Request code constant
    public static final int MASKED_WALLET_REQUEST_CODE = 888;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Wallet fragment style
        WalletFragmentStyle walletFragmentStyle = new WalletFragmentStyle()
                .setBuyButtonText(BuyButtonText.BUY_NOW)
                .setBuyButtonWidth(Dimension.MATCH_PARENT);

        // Wallet fragment options
        WalletFragmentOptions walletFragmentOptions = WalletFragmentOptions.newBuilder()
                .setEnvironment(WalletConstants.ENVIRONMENT_SANDBOX)
                .setFragmentStyle(walletFragmentStyle)
                .setTheme(WalletConstants.THEME_LIGHT)
                .setMode(WalletFragmentMode.BUY_BUTTON)
                .build();

        // Instantiate the WalletFragment
        mWalletFragment = SupportWalletFragment.newInstance(walletFragmentOptions);

        // Initialize the WalletFragment
        WalletFragmentInitParams.Builder startParamsBuilder =
                WalletFragmentInitParams.newBuilder()
                        .setMaskedWalletRequest(generateMaskedWalletRequest())
                        .setMaskedWalletRequestCode(MASKED_WALLET_REQUEST_CODE)
                        .setAccountName("Google I/O Codelab");
        mWalletFragment.initialize(startParamsBuilder.build());
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

}
