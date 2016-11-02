package io.payapi.payapisdk;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 *
 * Created by franciscopayapi on 31/10/16.
 *
 */
public class PayApiWebViewFragment extends Fragment {

    private WebView webview;
    private android.app.ActionBar actionBar = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.payapi_webview_fragment, null, false);
        webview = (WebView) view.findViewById(R.id.payapi_webview);
        view.findViewById(R.id.payapi_webview_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setVisibility(View.GONE);
                if(actionBar != null) actionBar.show();
                webview.loadUrl("about:blank");
                webview.clearHistory();
            }
        });

        webview.getSettings().setJavaScriptEnabled(true);

        webview.setWebViewClient(new WebViewClient() {
            boolean nextClose = false;

            @Override
            public void onPageStarted(WebView wv, String url, Bitmap favicon) {
                Log.d("PayApiWebViewFragment", url);
                if (nextClose) {
                    nextClose = false;
                    view.setVisibility(View.GONE);
                    if(actionBar != null) actionBar.show();
                    wv.loadUrl("about:blank");
                    wv.clearHistory();
                } else {
                    nextClose = url.matches("https://(staging-)?input.payapi.io/v[0-9]+/secureform/[a-z0-9_]+/return");
                    super.onPageStarted(wv, url, favicon);
                }
            }
        });
        return view;
    }

    public void makePost(final String url, String jwtSigned) {
        if(webview != null) {
            actionBar = getActivity().getActionBar();
            if(actionBar != null) actionBar.hide();
            ((View)webview.getParent()).setVisibility(View.VISIBLE);
            webview.postUrl(url, ("data="+jwtSigned).getBytes());
        }
    }
}
