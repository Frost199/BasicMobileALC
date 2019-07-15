package com.andela.alc4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

public class About extends AppCompatActivity {


    Toolbar aboutToolbar;
    ProgressBar progressBar;
    WebView alcWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        aboutToolbar = findViewById(R.id.about_activity_toolbar);
        progressBar = findViewById(R.id.about_progress_bar);
        alcWebView = findViewById(R.id.alc_about_web_view);
//        progressBar.setVisibility(View.GONE);

        setSupportActionBar(aboutToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        alcWebView.getSettings().setJavaScriptEnabled(true);
        alcWebView.getSettings().setAppCacheEnabled(true);
        alcWebView.getSettings().setDatabaseEnabled(true);
        alcWebView.getSettings().setDomStorageEnabled(true);
        alcWebView.getSettings().setDatabaseEnabled(true);
        alcWebView.getSettings().setSupportZoom(true);
        alcWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        alcWebView.getSettings().setBuiltInZoomControls(true);

        alcWebView.getSettings().setGeolocationEnabled(true);

        alcWebView.setWebViewClient(new WebViewClient(){

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
                super.onReceivedSslError(view, handler, error);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBar.setVisibility(View.VISIBLE);
                setTitle("Loading ALC...");
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
                setTitle(view.getTitle());
                super.onPageFinished(view, url);
            }
        });
        alcWebView.loadUrl("https://andela.com/alc/");

        alcWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            //go back to main activity
            Intent homeIntent = new Intent(About.this, MainActivity.class);
            startActivity(homeIntent);
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        if (alcWebView.canGoBack()){
            alcWebView.goBack();
        }else {
            super.onBackPressed();
        }
    }
}
