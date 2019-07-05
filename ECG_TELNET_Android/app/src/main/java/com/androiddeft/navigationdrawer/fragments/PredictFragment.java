package com.androiddeft.navigationdrawer.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.androiddeft.navigationdrawer.R;
import com.androiddeft.navigationdrawer.constants.NavigationDrawerConstants;


public class PredictFragment extends Fragment {

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
        mWebView.loadUrl("http://brahmiamine.pythonanywhere.com/predict");

        // Enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Force links and redirects to open in the WebView instead of in a browser
        mWebView.setWebViewClient(new WebViewClient());

        return view;
    }

}
