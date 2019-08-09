package com.example.krazenn.mynews.Controllers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.krazenn.mynews.R;
import com.example.krazenn.mynews.Utils.MyAlarmReceiver;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationActivity extends AppCompatActivity {

    @BindView(R.id.buttonSearch)
    Button buttonSearch;
    @BindView(R.id.edit_text_start_date)
    EditText editTextStartDate;
    @BindView(R.id.edit_text_end_date)
    EditText editTextEndDate;
    @BindView(R.id.btn_switch_notification)
    Switch btnSwitchNotification;
    SharedPreferences sharedPreferences;
    // 1 - Creating an intent to execute our broadcast
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);


        sharedPreferences = getBaseContext().getSharedPreferences("notification", 0);

        buttonSearch.setVisibility(View.INVISIBLE);
        editTextStartDate.setVisibility(View.INVISIBLE);
        editTextEndDate.setVisibility(View.INVISIBLE);
        if (sharedPreferences.getBoolean("notification", false)) {
            btnSwitchNotification.setChecked(true);
        }


        configureAlarmManager();

        btnSwitchNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    startAlarm();
                } else {
                    stopAlarm();
                }
            }
        });
    }


    // 2 - Configuring the AlarmManager
    private void configureAlarmManager() {
        Intent alarmIntent = new Intent(NotificationActivity.this, MyAlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(NotificationActivity.this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }


    // 3 - Start Alarm
    private void startAlarm() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 18);
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        Toast.makeText(this, "Alarm set !", Toast.LENGTH_SHORT).show();
    }

    // 4 - Stop Alarm
    private void stopAlarm() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        Toast.makeText(this, "Alarm Canceled !", Toast.LENGTH_SHORT).show();
    }
}
