package com.example.krazenn.mynews.Controllers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.krazenn.mynews.Models.ArticleList;
import com.example.krazenn.mynews.Models.Search.Doc;
import com.example.krazenn.mynews.R;
import com.example.krazenn.mynews.Utils.MyAlarmReceiver;
import com.example.krazenn.mynews.Utils.NyStreams;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class NotificationActivity extends AppCompatActivity {
    @BindView(R.id.edit_text_search)
    EditText editTextSearch;
    @BindView(R.id.buttonSearch)
    Button buttonSearch;
    @BindView(R.id.edit_text_start_date)
    EditText editTextStartDate;
    @BindView(R.id.edit_text_end_date)
    EditText editTextEndDate;
    @BindView(R.id.btn_switch_notification)
    Switch btnSwitchNotification;
    @BindView(R.id.checkbox_arts)
    CheckBox checkBoxArts;
    @BindView(R.id.checkbox_business)
    CheckBox checkBoxBusiness;
    @BindView(R.id.checkbox_entrepreneurs)
    CheckBox checkBoxEntrepreneur;
    @BindView(R.id.checkbox_politics)
    CheckBox checkBoxPolitics;
    @BindView(R.id.checkbox_sport)
    CheckBox checkBoxSport;
    @BindView(R.id.checkbox_travel)
    CheckBox checkBoxTravel;
    Map<String, String> params = new HashMap<>();
    String sectionNotification = "";
    String inputSearch = "";
    List<Doc> resultMostPopulars;
    SharedPreferences sharedPreferences;
    private Disposable disposable;
    // 1 - Creating an intent to execute our broadcast
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        loadPreferences();
        buttonSearch.setVisibility(View.GONE);
        editTextStartDate.setVisibility(View.GONE);
        editTextEndDate.setVisibility(View.GONE);

        configureAlarmManager();

        btnSwitchNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    inputSearch = editTextSearch.getText().toString();
                    sharedPreferences.edit().putString("editTextSearch", inputSearch).apply();
                    startAlarm();
                    sharedPreferences.edit().putBoolean("notification", true).apply();

                } else {
                    stopAlarm();
                    sharedPreferences.edit().putBoolean("notification", false).apply();
                }
            }
        });
    }

    public void onCheckboxClicked(View view) {

        List<String> listSection = new ArrayList<>();
        // Check which checkbox was clicked

        if (checkBoxArts.isChecked()) {
            listSection.add("Arts");
            sharedPreferences.edit().putBoolean("checkBoxArts", true).apply();
        } else {
            sharedPreferences.edit().putBoolean("checkBoxArts", false).apply();
        }

        if (checkBoxBusiness.isChecked()) {
            listSection.add("Business");
            sharedPreferences.edit().putBoolean("checkBoxBusiness", true).apply();
        } else {
            sharedPreferences.edit().putBoolean("checkBoxBusiness", false).apply();
        }

        if (checkBoxEntrepreneur.isChecked()) {
            listSection.add("Entrepreneurs");
            sharedPreferences.edit().putBoolean("checkBoxEntrepreneur", true).apply();
        } else {
            sharedPreferences.edit().putBoolean("checkBoxEntrepreneur", false).apply();
        }

        if (checkBoxPolitics.isChecked()) {
            listSection.add("Politics");
            sharedPreferences.edit().putBoolean("checkBoxPolitics", true).apply();
        } else {
            sharedPreferences.edit().putBoolean("checkBoxPolitics", false).apply();
        }

        if (checkBoxSport.isChecked()) {
            listSection.add("Sports");
            sharedPreferences.edit().putBoolean("checkBoxSport", true).apply();
        } else {
            sharedPreferences.edit().putBoolean("checkBoxSport", false).apply();
        }

        if (checkBoxTravel.isChecked()) {
            listSection.add("Travel");
            sharedPreferences.edit().putBoolean("checkBoxTravel", true).apply();
        } else {
            sharedPreferences.edit().putBoolean("checkBoxTravel", false).apply();
        }
        sectionNotification = "";

        for (int i = 0; i < listSection.size(); i++) {
            sectionNotification += "\"" + listSection.get(i) + "\"";

        }
        Log.e("sectionNotif", sectionNotification);
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
        calendar.set(Calendar.HOUR_OF_DAY, 6);
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        Toast.makeText(this, "Alarm set !", Toast.LENGTH_SHORT).show();
        executeHttpRequestWithRetrofitSearch();
    }

    // 4 - Stop Alarm
    private void stopAlarm() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        Toast.makeText(this, "Alarm Canceled !", Toast.LENGTH_SHORT).show();
    }

    private void loadPreferences() {
        sharedPreferences = getSharedPreferences("checkBoxArts", 0);
        sharedPreferences = getSharedPreferences("checkBoxBusiness", 0);
        sharedPreferences = getSharedPreferences("checkBoxEntrepreneur", 0);
        sharedPreferences = getSharedPreferences("checkBoxPolitics", 0);
        sharedPreferences = getSharedPreferences("checkBoxSport", 0);
        sharedPreferences = getSharedPreferences("checkBoxTravel", 0);
        sharedPreferences = getSharedPreferences("notification", 0);
        sharedPreferences = getSharedPreferences("editTextSearch", 0);
        editTextSearch.setText(sharedPreferences.getString("editTextSearch", ""));

        if (sharedPreferences.getBoolean("notification", false)) {
            btnSwitchNotification.setChecked(true);
        }

        if (sharedPreferences.getBoolean("checkBoxArts", false)) {
            checkBoxArts.setChecked(true);
        }
        if (sharedPreferences.getBoolean("checkBoxBusiness", false)) {
            checkBoxBusiness.setChecked(true);
        }
        if (sharedPreferences.getBoolean("checkBoxEntrepreneur", false)) {
            checkBoxEntrepreneur.setChecked(true);
        }
        if (sharedPreferences.getBoolean("checkBoxPolitics", false)) {
            checkBoxPolitics.setChecked(true);
        }
        if (sharedPreferences.getBoolean("checkBoxSport", false)) {
            checkBoxSport.setChecked(true);
        }
        if (sharedPreferences.getBoolean("checkBoxTravel", false)) {
            checkBoxTravel.setChecked(true);
        }
    }

    private void executeHttpRequestWithRetrofitSearch() {

        params.put("q", inputSearch);

        if (!sectionNotification.isEmpty()) {
            params.put("fq", "news_desk:(" + sectionNotification + ")");
            Log.e("sectionResult,", sectionNotification);
        }
        this.disposable = NyStreams.streamFetchArticleSearch(params, "hKPJScQIKlhcQ3V0GmlDulzquyM28AGL").subscribeWith(new DisposableObserver<ArticleList>() {

            @Override
            public void onNext(ArticleList articleLS) {
                Gson gson = new Gson();
                resultMostPopulars = articleLS.getResponse().getDocs();
                Log.e("listNotif", gson.toJson(resultMostPopulars.size()));
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
            }
        });
    }
}
