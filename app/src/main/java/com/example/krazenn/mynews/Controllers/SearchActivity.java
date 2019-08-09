package com.example.krazenn.mynews.Controllers;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;

import com.example.krazenn.mynews.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends Activity {
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
    String date;
    SharedPreferences sharedPreferences;

    ArrayList<Map<String, String>> listItem = new ArrayList<Map<String, String>>();

    Map<String, String> map = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        btnSwitchNotification.setVisibility(View.INVISIBLE);

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

}
