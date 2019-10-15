package com.syahrul.kotlinnewsapp

import android.annotation.SuppressLint
import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    lateinit var alertDialog: AlertDialog

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        alertDialog = SpotsDialog(this)
        alertDialog.show()

        detailView.settings.javaScriptEnabled=true
        detailView.webChromeClient= WebChromeClient()
        detailView.webViewClient = object:WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?){
                alertDialog.dismiss()
            }
        }

        if(intent != null){
            if(!intent.getStringExtra("webUrl")!!.isEmpty()) {
                detailView.loadUrl(intent.getStringExtra("webUrl"))
            }
        }
    }
}
