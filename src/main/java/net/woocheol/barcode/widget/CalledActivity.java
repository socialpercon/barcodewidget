package net.woocheol.barcode.widget;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

public class CalledActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent localIntent = new Intent("com.google.zxing.client.android.SCAN");
        localIntent.putExtra("SCAN_MODE", "ONE_D_MODE");
		startActivityForResult(localIntent, 0);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		try {
			String contents = intent.getStringExtra("SCAN_RESULT");
			String baseUrl = "http://book.naver.com/search/search.nhn?sm=sta_hty.book&sug=&where=nexearch&query={%WOOCHEOL%}";
			baseUrl = baseUrl.replace("{%WOOCHEOL%}", contents);
			Intent intentUri = new Intent(Intent.ACTION_VIEW,
					Uri.parse(baseUrl));
			startActivity(intentUri);
			finish();
		}catch (Exception e) {
			Toast.makeText(this, "Cancelled in Barcode Camera", Toast.LENGTH_SHORT).show();
		}
	}
}
