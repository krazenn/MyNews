package com.example.krazenn.mynews.Controllers;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.krazenn.mynews.R;

import java.util.Calendar;

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


    String input_search;

    Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        editTextStartDate = findViewById(R.id.edit_text_start_date);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input_search = editTextSearch.getText().toString();
                Intent intent = new Intent(getApplicationContext(), ResultSearchActivity.class);
                intent.putExtra("input_search", input_search);
                startActivity(intent);

            }
        });
        editTextStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePickerToEditText(editTextStartDate);

            }
        });

        editTextEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePickerToEditText(editTextEndDate);
            }
        });


    }

    private void openDatePickerToEditText(final EditText editText) {
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

    }


}
