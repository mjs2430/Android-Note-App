package com.example.journal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView eventListView;
    private List<String> eventList = new ArrayList<>();
    private ArrayAdapter<String> eventAdapter;
    private static final String EVENT_LIST_FILE = "eventList";

    private ActivityResultLauncher<Intent> newEventLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eventListView = findViewById(R.id.event_list_view);
        eventAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, eventList);
        eventListView.setAdapter(eventAdapter);

        newEventLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                Intent data = result.getData();
                String eventTitle = data.getStringExtra("event_title");
                eventList.add(eventTitle);
                eventAdapter.notifyDataSetChanged();
                saveEventList(); //Save eventList to Storage after each addition
            }
        });

        loadEventList(); //Load eventList from Storage during app startup

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

    private void saveEventList() {
        try {
            FileOutputStream fos = openFileOutput(EVENT_LIST_FILE, MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(eventList);
            oos.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Event Save Error", "Unable to save events to internal storage");
        }
    }

    private void loadEventList() {
        try {
            FileInputStream fis = openFileInput(EVENT_LIST_FILE);
            ObjectInputStream ois = new ObjectInputStream(fis);
            eventList = (ArrayList<String>) ois.readObject();
            eventAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, eventList);
            eventListView.setAdapter(eventAdapter);
            ois.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Event Load Error", "Unable to load events from internal storage. File might not exist");
        }
    }
}