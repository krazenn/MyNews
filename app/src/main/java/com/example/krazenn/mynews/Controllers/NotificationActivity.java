package com.example.krazenn.mynews.Controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.krazenn.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationActivity extends AppCompatActivity {

    @BindView(R.id.buttonSearch)
    Button buttonSearch;
    @BindView(R.id.edit_text_start_date)
    EditText editTextStartDate;
    @BindView(R.id.edit_text_end_date)
    EditText editTextEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        buttonSearch.setVisibility(View.INVISIBLE);
        editTextStartDate.setVisibility(View.INVISIBLE);
        editTextEndDate.setVisibility(View.INVISIBLE);

    }
}
