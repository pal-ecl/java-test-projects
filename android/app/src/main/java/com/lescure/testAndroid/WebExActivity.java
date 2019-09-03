package com.lescure.testAndroid;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lescure.testAndroid.utils.OkHttpUtil;
import com.lescure.testAndroid.webView.MyWebViewClient;

public class WebExActivity extends AppCompatActivity {


    private WebView wvWEx;
    private TextView tvWEx;
    private EditText etURL;
    public String results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_ex);

        wvWEx = findViewById(R.id.wvWEx);
        tvWEx = findViewById(R.id.tvWEx);

        wvWEx.setWebViewClient(new MyWebViewClient());
        WebSettings webviewSettings = wvWEx.getSettings();
        webviewSettings.setJavaScriptEnabled(true);

        wvWEx.loadUrl("https://www.google.com");
        etURL = findViewById(R.id.etURL);
        WebAt webAt = new WebAt();
        webAt.execute();
        etURL = findViewById(R.id.etURL);
    }

    public class WebAt extends AsyncTask {

        private Exception exception;

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                Log.w("TAG_REQUEST", etURL.toString());
                results = OkHttpUtil.sendGetOkHttpRequest(etURL.toString());
            } catch (Exception e) {
                e.printStackTrace();
                exception = e;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            if (exception != null) {
                Toast.makeText(WebExActivity.this, "Erreur de récupération de la requête", Toast.LENGTH_SHORT).show();
            }
            tvWEx.setText(results);
        }
    }


}
