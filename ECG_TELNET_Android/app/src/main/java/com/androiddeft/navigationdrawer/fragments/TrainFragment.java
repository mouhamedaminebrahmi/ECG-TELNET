package com.androiddeft.navigationdrawer.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.androiddeft.navigationdrawer.R;
import com.androiddeft.navigationdrawer.constants.NavigationDrawerConstants;

@SuppressLint({"NewApi", "SetJavaScriptEnabled"})
public class TrainFragment extends Fragment {

    public WebView mWebView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(NavigationDrawerConstants.TAG_GALLERY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout. fragment_gallery, container, false);
        mWebView = (WebView) view.findViewById(R.id.wwebview);
        mWebView.loadUrl("http://brahmiamine.pythonanywhere.com/train");

        // Enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Force links and redirects to open in the WebView instead of in a browser
        mWebView.setWebViewClient(new WebViewClient());

        return view;
    }

    public class ButtonClickJavascriptInterface {
        Context mContext;
        ButtonClickJavascriptInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void onButtonClick(String toast) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }
    }

}
