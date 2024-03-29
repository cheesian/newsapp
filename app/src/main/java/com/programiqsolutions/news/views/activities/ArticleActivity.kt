package com.programiqsolutions.news.views.activities

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.programiqsolutions.news.R
import com.programiqsolutions.news.databinding.ArticleActivityBinding
import com.programiqsolutions.news.utils.Notify.log
import com.programiqsolutions.news.utils.Notify.snackBar
import java.net.UnknownHostException


/**
Created by ian
 */

class ArticleActivity: AppCompatActivity() {

    private lateinit var binding: ArticleActivityBinding
    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_webview)
        webView = binding.webview
        progressBar = binding.progressBar
        val toolbar = binding.toolbar
        toolbar.title = ""
        progressBar = binding.progressBar
        swipeRefreshLayout = binding.swipe
        webView = binding.webview
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = Client()
        url = intent.getStringExtra("url")
        if (url.isNullOrBlank()) {
            snackBar(
                view = binding.root,
                message = "Failed to load url",
                actionMessage = "Go back",
                function = {  }
            )
        } else {
            webView.loadUrl(url!!)
        }
        swipeRefreshLayout.setOnRefreshListener {
            url?.let {
                webView.loadUrl(it)
            }
            swipeRefreshLayout.isRefreshing = false
        }
    }

    inner class Client: WebViewClient () {

        // To load any other links from the web page within the app
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            return false
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            progressBar.visibility = View.VISIBLE
        }


        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            progressBar.visibility = View.GONE
        }

    }
}