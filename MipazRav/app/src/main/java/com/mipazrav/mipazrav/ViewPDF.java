package com.mipazrav.mipazrav;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class ViewPDF extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdf_web_view);
        final String filename=getIntent().getStringExtra("filename");

//        WebView webView = (WebView) findViewById(R.id.webview);
//webView.loadUrl("http://www.mipazrav.com/audio/"+filename.replace(" ","%20"));
//        webView.setDownloadListener(new DownloadListener() {
//            public void onDownloadStart(String url, String userAgent,
//                                        String contentDisposition, String mimetype,
//                                        long contentLength) {
//                Intent i = new Intent(Intent.ACTION_VIEW);
//                i.setData(Uri.parse(url));
//                startActivity(i);
//            }
//        });

        WebView webview = (WebView) findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        String pdf=("http://www.mipazrav.com/audio/"+filename.replace(" ","%20"));
        webview.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);

    }
}
