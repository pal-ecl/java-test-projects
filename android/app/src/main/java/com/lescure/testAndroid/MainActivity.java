package com.lescure.testAndroid;

import android.Manifest;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private static final int MENU_ALERT_ID = 1;
    private static final int MENU_TIME_ID = 2;
    private static final int MENU_DATE_ID = 3;
    private static final int MENU_NOTIF_ID = 4;
    private static final int MENU_NOTIF_DELAY_ID = 5;
    private static final int MENU_SERVICE_EX_ID = 6;
    private static final int MENU_WEB_S = 7;

    private static final String CHANNEL_ID = "MyChannel";
    private static final CharSequence CHANNEL_NAME = "Commands";

    public static final SimpleDateFormat SPF = new SimpleDateFormat("dd - MM - yyyy hh:mm");

    private Button btCancel, btValidate, btNext;
    private RadioButton rbLike, rbDislike;
    private RadioGroup rgLike;
    private EditText etName;
    private ImageView imDoneDelete;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //test log et try catch finally
        Log.w("TEST", "Test log");
        String test = null;
        try {
            if (test.equals("test")) {
                Log.w("TAG_TEST", "not null pointer exception");
            }
        } catch (Exception e) {
            Log.w("TAG_TEST", "probably null pointer exception", e);
        } finally {
            Log.w("TAG_TEST", "end of test");
        }

        btCancel = findViewById(R.id.btCancel);
        btValidate = findViewById(R.id.btValidate);
        rbLike = findViewById(R.id.rbLike);
        rbDislike = findViewById(R.id.rbDislike);
        rgLike = findViewById(R.id.rgLike);
        etName = findViewById(R.id.etName);
        imDoneDelete = findViewById(R.id.imDoneDelete);
        btNext = findViewById(R.id.btNext);

        btCancel.setOnClickListener(this);
        btValidate.setOnClickListener(this);
        rbLike.setOnClickListener(this);
        rbDislike.setOnClickListener(this);
        btNext.setOnClickListener(this);

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
        menu.add(0, MENU_WEB_S, 7, "Web ex");
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
            case 4:
                createInstantNotification(this, "Notification immédiate");
                break;
            case 5:
                scheduleNotification(this, "Notification retardée", 6000);
                break;
            case 6:
                serviceExample();
                break;
            case 7:
                Log.w("TAG_next", "Click sur le bt web ex ");
                Intent intent = new Intent(this, WebExActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btValidate:
                if (rbLike.isChecked()) {
                    etName.setText("J'aime");
                } else if (rbDislike.isChecked()) {
                    etName.setText("J'aime pas");
                } else {
                    etName.setText("Pas d'avis");
                }
                imDoneDelete.setImageResource(R.mipmap.ic_done);
                imDoneDelete.setColorFilter(Color.CYAN);
                Log.w("TAG_validate", "Click sur le bouton valider");
                break;
            case R.id.btCancel:
                rgLike.clearCheck();
                etName.setText("");
                imDoneDelete.setImageResource(R.mipmap.ic_deleteforever);
                imDoneDelete.setColorFilter(Color.RED);
                Log.w("TAG_cancel", "Click sur le bouton annuler");
                break;
            case R.id.rbLike:
                Log.w("TAG_rb", "Click sur le rb j'aime");
                break;
            case R.id.rbDislike:
                Log.w("TAG_rb", "Click sur le rb j'aime pas");
                break;
            case R.id.btNext:
                Log.w("TAG_next", "Click sur le bt next");
                Intent intent = new Intent(this, SecondActivity.class);
                intent.putExtra("test", etName.getText().toString());
                startActivity(intent);
                break;
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
                        Toast.makeText(MainActivity.this, "Alerte",
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
            Intent intent = new Intent(this, ServiceExActivity.class);
            startActivity(intent);
        } else {
//Etape 2 : On affiche la fenêtre de demande de permission
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION
                    }, 0);
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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(this, ServiceExActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "L'autorisation de localisation est nécessaire", Toast.LENGTH_SHORT).show();
        }
    }

    private static void initChannel(Context c) {
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }
        NotificationManager nm = (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription("Commandes");
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setVibrationPattern(new long[]{100, 200, 300, 500, 400, 300, 200, 400});

        nm.createNotificationChannel(channel);
    }

    public static void createInstantNotification(Context c, String message) {
        initChannel(c);

        Intent intent = new Intent(c, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(c, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(c, CHANNEL_ID);
        notificationBuilder.setSmallIcon(R.mipmap.ic_done)
                .setContentTitle("Test notif")
                .setContentText(message)
                .setContentIntent(pi)
                .setPriority(NotificationManager.IMPORTANCE_HIGH)
                .setDefaults(Notification.DEFAULT_ALL);

        NotificationManagerCompat ncm = NotificationManagerCompat.from(c);

        ncm.notify(29, notificationBuilder.build());
        Log.w("TAG_NOTIF", "Notif send");
    }

    public static void scheduleNotification(Context c, String message, long delay) {
        initChannel(c);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(c, CHANNEL_ID);
        builder.setContentTitle("Notification retardée");
        builder.setContentText(message);
        builder.setSmallIcon(R.mipmap.ic_done);

        Intent intent = new Intent(c, NotificationPublisherBR.class);
        intent.putExtra("MaCle", builder.build());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(c, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)
                c.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }
}
