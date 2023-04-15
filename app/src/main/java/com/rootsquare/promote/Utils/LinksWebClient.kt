package com.rootsquare.promote.Utils

import android.graphics.Bitmap
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient


class LinksWebClient : WebViewClient() {
    override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
    //    Log.d("onpage start url - ",url)
        super.onPageStarted(view, url, favicon)

    }

    override fun onPageFinished(view: WebView, url: String) {
        //Page load finished
       // Log.d("onpage finish url - ",url)
        super.onPageFinished(view, url)
    }
}