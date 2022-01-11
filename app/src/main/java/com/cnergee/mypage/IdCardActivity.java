package com.cnergee.mypage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import com.cnergee.myapp.instanet.R;
import com.cnergee.mypage.utils.Utils;

public class IdCardActivity extends Activity {
WebView webView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_id_card);
		Intent i = getIntent();
		String id= i.getStringExtra("icard_id");
		Utils.log("Id ","is:"+305397);
		webView=(WebView) findViewById(R.id.wvIdCard);
	
		webView.clearCache(true);
		webView.clearHistory();
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		webView.loadUrl("http://sampark.cmaya.in/Idcard/identity.aspx?id="+id);
		Utils.log("Url","is"+webView.getUrl());
	}
	
}
