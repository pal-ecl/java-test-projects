package com.lescure.toulousesacchiens;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lescure.toulousesacchiens.model.DogBagPlaceBean;
import com.lescure.toulousesacchiens.model.RecordBean;
import com.lescure.toulousesacchiens.utils.WSUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements BagDogPlaceAdapter.OnBagDogPlaceAdapterListener {

    private static final int MENU_MAP_ICON = 2;
    private static final int MENU_MAP = 1;
    private EditText etSearch;
    private String search;

    private RecyclerView rvPlaceDogBag;
    private ArrayList<RecordBean> data;
    private BagDogPlaceAdapter bagDogPlaceAdapter;
    private TextView tvNoResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etSearch = findViewById(R.id.etSearch);
        rvPlaceDogBag = findViewById(R.id.rvPlaceDogBag);

        data = new ArrayList<>();
        bagDogPlaceAdapter = new BagDogPlaceAdapter(data, this);
        rvPlaceDogBag.setAdapter(bagDogPlaceAdapter);
        rvPlaceDogBag.setLayoutManager(new LinearLayoutManager(this));
        tvNoResults = findViewById(R.id.tvNoResults);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, MENU_MAP_ICON, 1, "carte")
                .setIcon(R.mipmap.ic_gmap).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(1, MENU_MAP, 2, "Carte").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                toMapAll();
            case 2:
                toMapAll();
        }
        return super.onOptionsItemSelected(item);
    }

    public void checkSearch(View view) {
        Log.w("TAG_BUTTON", "Click sur le bouton vérifier");
        search = etSearch.getText().toString().toUpperCase();
        WsAt wsAt = new WsAt();
        wsAt.execute();
    }

    @Override
    public void onRecordClick(DogBagPlaceBean dogBagPlaceBean) {
        Log.w("TAG_BUTTON", "click record");
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra(MapsActivity.DOGBAGPLACE, dogBagPlaceBean);
        startActivity(intent);
    }

    public void toMapAll() {
        Log.w("TAG_BUTTON", "click map all");
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(intent);
    }

    public class WsAt extends AsyncTask {
        private ArrayList<RecordBean> results;
        private Exception exception;

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
            Log.w("TAG_VH", "display data on VH");
            if (exception != null) {
                tvNoResults.setText("Les données ne sont pas accessibles");
                tvNoResults.setVisibility(View.VISIBLE);
                Log.w("TAG_VH", "data not accessible");
            } else {
                if (!results.isEmpty()) {
                    Log.w("TAG_VH", "results not null");
                    tvNoResults.setVisibility(View.GONE);
                    bagDogPlaceAdapter.notifyDataSetChanged();
                    data.clear();
                    data.addAll(results);
                } else {
                    Toast.makeText(MainActivity.this, "Aucun endroit correspondant trouvé",
                            Toast.LENGTH_LONG).show();
                    data.clear();
                    tvNoResults.setText("Il n'y a aucun résultat.");
                    tvNoResults.setVisibility(View.VISIBLE);
                }
            }
        }
    }
}
