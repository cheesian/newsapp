package com.example.news.views.activities

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
import com.example.news.R
import com.example.news.databinding.ArticleActivityBinding
import com.example.news.utils.Notify.log
import com.example.news.utils.Notify.snackBar
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
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
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
                function = View.OnClickListener { webView.loadUrl(url) }
            )
        } else
        webView.loadUrl(url)
        swipeRefreshLayout.setOnRefreshListener {
            webView.loadUrl(url)
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

        override fun onReceivedError(
            view: WebView?,
            request: WebResourceRequest?,
            error: WebResourceError?
        ) {
            log(message = error.toString())
            val errorMessage: String = if (error is UnknownHostException) {
                "Check your connection and try again"
            } else {
                "Something went wrong. Please try again"
            }
            snackBar(
                view = view!!.rootView,
                message = errorMessage,
                actionMessage = "Reload",
                function = View.OnClickListener {
                    webView.loadUrl(url)
                }
            )
        }
    }
}