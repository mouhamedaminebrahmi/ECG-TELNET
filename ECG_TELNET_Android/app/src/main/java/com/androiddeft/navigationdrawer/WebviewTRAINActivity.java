package com.androiddeft.navigationdrawer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;


public class WebviewTRAINActivity extends Activity {

    WebView mWebViewDemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_train);
        mWebViewDemo = (WebView) findViewById(R.id.wwebview);

//Prise en charge de JavaScript : JavaScript est désactivé par défaut dans les widgets WebView. Par conséquent, les pages Web contenant
// des références javascript ne fonctionneront pas correctement.
// Pour activer le script java, l'extrait suivant doit être appelé sur l'instance webview:


        mWebViewDemo.getSettings().setJavaScriptEnabled(true);

        //url de Webview
        mWebViewDemo.loadUrl("http://brahmiamine.pythonanywhere.com/train");

    }



}