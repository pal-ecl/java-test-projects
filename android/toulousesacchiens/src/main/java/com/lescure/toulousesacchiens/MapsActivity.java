package com.lescure.toulousesacchiens;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.lescure.toulousesacchiens.model.DogBagPlaceBean;
import com.lescure.toulousesacchiens.model.RecordBean;
import com.lescure.toulousesacchiens.utils.WSUtils;

import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int MENU_RETURN = 2;
    public final static String DOGBAGPLACE = "dogBagPlace";
    public final static double[] TOULOUSEPLACE = {43.6043, 1.4437};
    private GoogleMap mMap;
    private DogBagPlaceBean dogBagPlaceBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        dogBagPlaceBean = (DogBagPlaceBean) getIntent().getSerializableExtra(DOGBAGPLACE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, MENU_RETURN, 1, "Retour")
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 2:
                Log.w("TAG_BUTTON", "click on return button");
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.clear();

        if (dogBagPlaceBean != null) {
            LatLng bagDogPlace = new LatLng(dogBagPlaceBean.getGeo_point_2d()[0],
                    dogBagPlaceBean.getGeo_point_2d()[1]);
            mMap.addMarker(new MarkerOptions().position(bagDogPlace).title(dogBagPlaceBean.getIntitule()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(bagDogPlace));
            mMap.moveCamera(CameraUpdateFactory.zoomTo(15));
            Log.w("TAG_MAP", "Map created for " + dogBagPlaceBean.toString());
        } else {
            WsAtMap wsAtMap = new WsAtMap();
            wsAtMap.execute();
        }
    }

    public class WsAtMap extends AsyncTask {
        private ArrayList<RecordBean> results;
        private Exception exception;
        String search = "";

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                results = WSUtils.findDogBagFromWeb(search);
            } catch (Exception e) {
                e.printStackTrace();
                exception = e;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            LatLng toulouse = new LatLng(TOULOUSEPLACE[0], TOULOUSEPLACE[1]);
            Log.w("TAG_MAP", "For MAP");
            if (exception != null) {
                Toast.makeText(MapsActivity.this,
                        "Les données n'ont pas pu être atteintes.", Toast.LENGTH_SHORT).show();
            }
            if (results != null) {
                for (RecordBean record : results) {
                    LatLng markerPlace = new LatLng(record.getFields().getGeo_point_2d()[0], record.getFields().getGeo_point_2d()[1]);
                    mMap.addMarker(new MarkerOptions().position(markerPlace).title(record.getFields().getIntitule()));
                }
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLng(toulouse));
            mMap.moveCamera(CameraUpdateFactory.zoomTo(13));
            Log.w("TAG_MAP", "Map created for all places");
        }
    }
}
