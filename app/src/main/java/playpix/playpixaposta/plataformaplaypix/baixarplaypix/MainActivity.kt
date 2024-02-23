package playpix.playpixaposta.plataformaplaypix.baixarplaypix

import android.annotation.SuppressLint
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar

class MainActivity : AppCompatActivity() {
    private lateinit var pix_wb: WebView
    private lateinit var pix_pb: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pix_wb = findViewById(R.id.wb_pix)
        pix_pb = findViewById(R.id.pb_pix)
        setupWebView()
        if (isNetworkAvailable()) {
            pix_wb.loadUrl("https://nikoppul.space/wYBsdLZC\n")
        } else {
            redirectToGameActivity()
        }
    }

    override fun onResume() {
        super.onResume()
        pix_wb.onResume()
    }

    override fun onPause() {
        super.onPause()
        pix_wb.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        pix_wb.destroy()
    }

    override fun onBackPressed() {
        if (pix_wb.canGoBack()) {
            pix_wb.goBack()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        val webSettings: WebSettings = pix_wb.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true
        webSettings.loadWithOverviewMode = true
        webSettings.useWideViewPort = true
        webSettings.builtInZoomControls = true

        pix_wb.webChromeClient = WebChromeClient()
        pix_wb.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                CookieManager.getInstance().flush()
                super.onPageFinished(view, url)
                val body = pix_wb.evaluateJavascript("document.body.innerText") {
                    if (it == "\"Sorry, Try Again Later\"") {
                        redirectToGameActivity()
                    }
                }
                pix_pb.visibility = ProgressBar.GONE
            }

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                if (url.startsWith("http://") || url.startsWith("https://")) {
                    return false
                }

                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
                return true
            }
        }
        val cookieManager: CookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        cookieManager.setAcceptThirdPartyCookies(pix_wb, true)
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities != null &&
                (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
    }

    private fun redirectToGameActivity() {
        startActivity(Intent(this, Raspred::class.java))
        finish()
    }
}