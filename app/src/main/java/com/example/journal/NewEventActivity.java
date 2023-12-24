package com.example.journal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class NewEventActivity extends AppCompatActivity {

    private EditText eventTitleEditText;
    private EditText attorneyOfRecordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_event_activity);

        eventTitleEditText = findViewById(R.id.event_title_edit_text);
        attorneyOfRecordEditText = findViewById(R.id.attorney_of_record_edit_text);

    }

    public void saveEvent(View view) {
        String eventTitle = eventTitleEditText.getText().toString();
        String attorneyOfRecord = attorneyOfRecordEditText.getText().toString();

        Bundle bundle = new Bundle();
        bundle.putString("event_title", eventTitle);
        bundle.putString("attorney_of_record", attorneyOfRecord);
        setResult(RESULT_OK, new Intent().putExtras(bundle));
        finish();
    }
}