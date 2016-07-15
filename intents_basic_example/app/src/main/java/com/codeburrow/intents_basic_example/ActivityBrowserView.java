package com.codeburrow.intents_basic_example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class ActivityBrowserView extends AppCompatActivity {

    private static final String LOG_TAG = ActivityBrowserView.class.getSimpleName();
    // WebView to display web pages.
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_browser_view);

        mWebView = (WebView) findViewById(R.id.web_view);

        Intent intent = getIntent();
        if (intent != null) {
            Toast.makeText(ActivityBrowserView.this, "ACTION PERFORMED: " + intent.getAction(), Toast.LENGTH_SHORT).show();
            // Retrieve data this intent is operating on, as an encoded String.
            String url = intent.getDataString();
            Toast.makeText(ActivityBrowserView.this, url, Toast.LENGTH_SHORT).show();
            mWebView.setWebViewClient(new CustomBrowser());
            mWebView.loadUrl(url);
        }
    }

    private class CustomBrowser extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

}
