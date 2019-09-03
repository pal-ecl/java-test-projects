package com.lescure.testAndroid;

import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.otto.Subscribe;

public class ServiceExActivity extends AppCompatActivity {

    private TextView tvLocation;
    private TextView tvAt;
    private ProgressBar pbEleve;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_ex);
        tvAt = findViewById(R.id.tvAt);
        tvLocation = findViewById(R.id.tvLocation);
        pbEleve = findViewById(R.id.pbEleve);
    }

    @Override
    protected void onStart() {
        MyApplication.getBus().register(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        MyApplication.getBus().unregister(this);
        super.onStop();
    }

    @Subscribe
    public void displayLocation(Location location) {
        tvLocation.setText("La latitude est : " + location.getLatitude() + " et la longitude est : " + location.getLongitude() + ".");
    }

    public void onClickStartExampleService(View view) {
        startService(new Intent(this, ServiceExample.class));
    }

    public void onClickStopExampleService(View view) {
        stopService(new Intent(this, ServiceExample.class));
    }

    public void onClickFindStudent(View view) {
        MyAt myAt = new MyAt();
        myAt.execute();
        pbEleve.setVisibility(View.VISIBLE);
    }

    public class MyAt extends AsyncTask {

        private Exception exception;
        private EleveBean eleve1;

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                eleve1 = WSUtils.loadEleveFromWeb();
            } catch (Exception e) {
                e.printStackTrace();
                exception = e;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            pbEleve.setVisibility(View.INVISIBLE);
            if (exception != null) {
                Toast.makeText(ServiceExActivity.this, "Erreur de récupération de l'élève", Toast.LENGTH_SHORT).show();
            }
            tvAt.setText(eleve1.getName() + " " + eleve1.getFirstName());
        }
    }

}
