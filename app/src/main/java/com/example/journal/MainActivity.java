package com.example.journal;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView eventListView;
    private List<String> eventList;
    private ArrayAdapter<String> eventAdapter;

    private ActivityResultLauncher<Intent> newEventLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eventListView = findViewById(R.id.event_list_view);
        eventList = new ArrayList<>();
        eventAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, eventList);
        eventListView.setAdapter(eventAdapter);

        newEventLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                Intent data = result.getData();
                String eventTitle = data.getStringExtra("event_title");
                eventList.add(eventTitle);
                eventAdapter.notifyDataSetChanged();
            }
        });

        eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedEvent = eventList.get(position);
                Intent intent = new Intent(MainActivity.this, EventActivity.class);
                intent.putExtra("event", selectedEvent);
                startActivity(intent);
            }
        });
    }

    public void addNewEvent(View view) {
        Intent intent = new Intent(MainActivity.this, NewEventActivity.class);
        newEventLauncher.launch(intent);
    }
}