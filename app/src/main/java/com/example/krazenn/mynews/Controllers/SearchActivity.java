package com.example.krazenn.mynews.Controllers;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
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


public class SearchActivity extends AppCompatActivity {
    @BindView(R.id.edit_text_search)
    EditText editTextSearch;
    @BindView(R.id.edit_text_start_date)
    EditText editTextStartDate;
    @BindView(R.id.edit_text_end_date)
    EditText editTextEndDate;
    @BindView(R.id.buttonSearch)
    Button buttonSearch;
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
    @BindView(R.id.btn_switch_notification)
    Switch btnSwitchNotification;


    String input_search;
    String dateStart;
    String dateEnd;
    String section = "";
    int itemId;
    SharedPreferences sharedPreferences;
    String inputSearch = "";
    Map<String, String> params = new HashMap<>();
    List<Doc> resultMostPopulars;
    String sectionNotification = "";
    private Disposable disposable;
    // 1 - Creating an intent to execute our broadcast
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        itemId = getIntent().getIntExtra("tab", 0);
        this.configureToolbar();
        if (itemId == 0) {
            getSupportActionBar().setTitle("Notification");
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
        if (itemId == 1) {
            btnSwitchNotification.setVisibility(View.GONE);
            editTextStartDate = findViewById(R.id.edit_text_start_date);
            buttonSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    input_search = editTextSearch.getText().toString();
                    Intent intent = new Intent(getApplicationContext(), ResultSearchActivity.class);
                    intent.putExtra("input_search", input_search);
                    intent.putExtra("date_start", dateStart);
                    intent.putExtra("date_end", dateEnd);
                    intent.putExtra("section", section);

                    startActivity(intent);

                }
            });

            editTextStartDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openDatePickerToEditText(editTextStartDate, true);

                }
            });

            editTextEndDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openDatePickerToEditText(editTextEndDate, false);
                }
            });
        }
    }

    private void configureToolbar() {
        //Get the toolbar (Serialise)
        Toolbar toolbar = findViewById(R.id.app_bar);
        //Set the toolbar
        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
    }

    public void onCheckboxClicked(View view) {

        List<String> listSection = new ArrayList<>();

        // Check which checkbox was clicked

        if (checkBoxArts.isChecked()) {
            listSection.add("Arts");
        }

        if (checkBoxBusiness.isChecked()) {
            listSection.add("Business");
        }

        if (checkBoxEntrepreneur.isChecked()) {
            listSection.add("Entrepreneurs");
        }

        if (checkBoxPolitics.isChecked()) {
            listSection.add("Politics");
        }

        if (checkBoxSport.isChecked()) {
            listSection.add("Sports");
        }

        if (checkBoxTravel.isChecked()) {
            listSection.add("Travel");
        }
        section = "";

        for (int i = 0; i < listSection.size(); i++) {
            section += "\"" + listSection.get(i) + "\"";

        }
        Log.e("section", section);
    }

    private void openDatePickerToEditText(final EditText editText, final Boolean startDate) {
        DatePickerDialog datePickerDialog;

        final Calendar calendar = Calendar.getInstance();
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        // date picker dialog
        datePickerDialog = new DatePickerDialog(SearchActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String monthFormat = String.format("%02d", monthOfYear + 1);
                        String dayFormat = String.format("%02d", dayOfMonth);
                        editText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        if (startDate) {
                            dateStart = String.valueOf(year) + String.valueOf(monthFormat) + String.valueOf(dayFormat);
                            Log.e("date1", dateStart);
                        } else {
                            dateEnd = String.valueOf(year) + String.valueOf(monthFormat) + String.valueOf(dayFormat);
                            Log.e("date2", dateEnd);
                        }

                    }

                }, year, month, day);

        datePickerDialog.show();

    }

    // 2 - Configuring the AlarmManager
    private void configureAlarmManager() {
        Intent alarmIntent = new Intent(SearchActivity.this, MyAlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(SearchActivity.this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }


    // 3 - Start Alarm
    private void startAlarm() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.MINUTE, 2);
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
