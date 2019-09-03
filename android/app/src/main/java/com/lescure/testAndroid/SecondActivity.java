package com.lescure.testAndroid;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SecondActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener {

    private static final int MENU_ALERT_ID = 1;
    private static final int MENU_TIME_ID = 2;
    private static final int MENU_DATE_ID = 3;
    private static final int MENU_NOTIF_ID = 4;
    private static final int MENU_NOTIF_DELAY_ID = 5;
    private static final int MENU_SERVICE_EX_ID = 6;

    public static final SimpleDateFormat SPF = new SimpleDateFormat("dd - MM - yyyy hh:mm");

    private TextView tvImportTextTest;
    private Button btAlert;
    private Button btTimePicker;
    private Button btDatePicker;
    private Button btNotif;
    private Button btDelayNotif;

    private Calendar calendar;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //afficher le texte importé de l'écran main
        Bundle mainImport = getIntent().getExtras();
        String textImport = mainImport.getString("test");

        tvImportTextTest = findViewById(R.id.tvImport);
        btAlert = findViewById(R.id.btAlert);
        btTimePicker = findViewById(R.id.btTimePicker);
        btDatePicker = findViewById(R.id.btDatePicker);
        btNotif = findViewById(R.id.btNotif);
        btDelayNotif = findViewById(R.id.btDelayNotif);

        tvImportTextTest.setText(textImport);

        calendar = Calendar.getInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, MENU_ALERT_ID, 1, "Alerte")
                .setIcon(R.mipmap.ic_plane).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        menu.add(0, MENU_TIME_ID, 2, "Heure");
        menu.add(0, MENU_DATE_ID, 3, "Date");
        menu.add(0, MENU_NOTIF_ID, 4, "Notif");
        menu.add(0, MENU_NOTIF_DELAY_ID, 5, "Notif+");
        menu.add(0, MENU_SERVICE_EX_ID, 6, "Service Example");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                alertDialog();
                break;
            case 2:
                timeDialog();
                break;
            case 3:
                dateDialog();
                break;
            case 6:
                serviceExample();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick2(View view) {
        if (view.equals(btAlert)) {
            alertDialog();
        } else if (view.equals(btTimePicker)) {
            timeDialog();
        } else if (view.equals(btDatePicker)) {
            dateDialog();
        }
    }

    private void alertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Alerte !");
        alertDialogBuilder.setTitle("alerte");
        alertDialogBuilder.setPositiveButton("ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(SecondActivity.this, "Alerte",
                                Toast.LENGTH_LONG).show();
                    }
                });
        alertDialogBuilder.setIcon(R.mipmap.ic_plane);
        alertDialogBuilder.show();
    }

    private void timeDialog() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, this,
                calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    private void dateDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void serviceExample() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
//On a la permission
        } else {
//Etape 2 : On affiche la fenêtre de demande de permission
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION
                    }, 25);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(year, month, dayOfMonth);
        Toast.makeText(this, SPF.format(calendar.getTime()), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        Toast.makeText(this, SPF.format(calendar.getTime()), Toast.LENGTH_LONG).show();
    }

}
