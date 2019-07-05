package com.androiddeft.navigationdrawer;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


@SuppressLint({"NewApi", "SetJavaScriptEnabled"})
public class WebviewPREDICTActivity extends AppCompatActivity {

    Dialog myDialog;

    WebView mWebViewDemo;

    Details sm;
DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_gallery);


        //
        myDialog = new Dialog(this);

        mWebViewDemo = (WebView) findViewById(R.id.wwebvieww);
        ButtonClickJavascriptInterface myJavaScriptInterface = new ButtonClickJavascriptInterface(WebviewPREDICTActivity.this);
        CookieManager.getInstance().setAcceptCookie(true);
        mWebViewDemo.addJavascriptInterface(myJavaScriptInterface, "MyFunction");
        mWebViewDemo.getSettings().setJavaScriptEnabled(true);
        mWebViewDemo.getSettings().setDatabaseEnabled(true);
        mWebViewDemo.getSettings().setDomStorageEnabled(true);

        //Android WebView loadUrl
        //Une fois que nous avons obtenu une référence à WebView, nous pouvons le configurer
        // et charger les URL via HTTP. La loadUrl()méthode WebView permet de charger l’URL dans WebView, comme indiqué ci-dessous:


        mWebViewDemo.loadUrl("http://alaeddine.pythonanywhere.com/predict");

        //progress dialog pour attendre le resultat de l'authetification de 12 seconde
        final int SECONDS = 12*1000;
        final ProgressDialog dlg = new ProgressDialog(this);
        dlg.setMessage("Authentification...");
        dlg.setCancelable(false);
        dlg.show();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                dlg.dismiss();
            }
        }, SECONDS);

    }


    //Injection d'événement JavaScript onclick () dans WebView
    public class ButtonClickJavascriptInterface {
        Context mContext;
        ButtonClickJavascriptInterface(Context c) {
            mContext = c;

        }
       //
        @JavascriptInterface
        public void onButtonClick(String TextInsideLi) {



            final String s=TextInsideLi;





            if(s.equals(s)){
               Toast.makeText(mContext, "matching ECG person", Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(getApplicationContext(), RecordListActivity.class);
                intent.putExtra("EXTRA_SESSION_ID", s);

                final int SECONDS = 2*1000;
                final ProgressDialog dlg = new ProgressDialog(mContext);
                dlg.setMessage("Authentication...");
                dlg.setCancelable(false);
                dlg.show();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        dlg.dismiss();
                    }
                }, SECONDS);
                startActivity(intent);

            }else{
               Toast.makeText(mContext, "not matching to any person", Toast.LENGTH_SHORT).show();


           }

        }
    }

}