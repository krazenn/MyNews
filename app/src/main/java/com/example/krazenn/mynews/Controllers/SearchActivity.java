package com.example.krazenn.mynews.Controllers;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.krazenn.mynews.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
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

    String[] selectSectionList;
    String input_search;
    String dateStart;
    String dateEnd;
    String section;
    String date;
    Map<String, String> listSection;
    Bundle bundle;
    ArrayList<Map<String, String>> listItem = new ArrayList<Map<String, String>>();

    Map<String, String> map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);


        selectSectionList = new String[6];
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
        // Is the view now checked?
        Gson gson = new Gson();
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked

        if (checkBoxArts.isChecked()) {

            listItem.add("section", "arts");
        }
        switch (view.getId()) {

            case R.id.checkbox_business:
                if (checked) {
                    map.put("section", "arts");
                } else {
                    selectSectionList[1] = null;
                }
                break;
            case R.id.checkbox_entrepreneurs:
                if (checked) {
                    selectSectionList[2] = checkBoxEntrepreneur.getText().toString();

                } else {
                    selectSectionList[2] = null;
                }
                break;
            case R.id.checkbox_politics:
                if (checked) {
                    selectSectionList[3] = checkBoxPolitics.getText().toString();

                } else {
                    selectSectionList[3] = null;
                }
                break;
            case R.id.checkbox_sport:
                if (checked) {
                    selectSectionList[4] = checkBoxSport.getText().toString();

                } else {
                    selectSectionList[4] = null;
                }
                break;
            case R.id.checkbox_travel:
                if (checked) {
                    selectSectionList[5] = checkBoxTravel.getText().toString();

                } else {
                    selectSectionList[5] = null;
                }
                break;

        }

        listItem.add(map);
        Log.e("list", section);

    }

    private void openDatePickerToEditText(final EditText editText, Boolean startDate) {
        DatePickerDialog datePickerDialog;

        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        // date picker dialog
        datePickerDialog = new DatePickerDialog(SearchActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        editText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                    }

                }, year, month, day);

        datePickerDialog.show();
        String monthFormat = String.format("%02d", month + 1);
        String dayFormat = String.format("%02d", day);
        if (startDate == true) {
            dateStart = String.valueOf(year) + String.valueOf(monthFormat) + String.valueOf(dayFormat);
            Log.e("date1", dateStart);
        } else {
            dateEnd = String.valueOf(year) + String.valueOf(monthFormat) + String.valueOf(dayFormat);
            Log.e("date2", dateEnd);
        }



    }

}
