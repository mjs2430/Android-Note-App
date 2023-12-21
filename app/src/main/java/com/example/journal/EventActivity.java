package com.example.journal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EventActivity extends AppCompatActivity {

    private String selectedEvent;
    private ListView noteListView;
    private List<Note> noteList;
    private NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        selectedEvent = getIntent().getStringExtra("event");
        setTitle(selectedEvent);

        noteListView = findViewById(R.id.note_list_view);
        noteList = new ArrayList<>();
        noteAdapter = new NoteAdapter(this, noteList);
        noteListView.setAdapter(noteAdapter);

        noteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Note selectedNote = noteList.get(position);
                Toast.makeText(EventActivity.this, selectedNote.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addNewNote(View view) {
        Intent intent = new Intent(EventActivity.this, NewNoteActivity.class);
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK) {
            String noteText = data.getStringExtra("note_text");
            String timestamp = new SimpleDateFormat("MM/dd/yy hh:mm a", Locale.getDefault()).format(new Date());

            Note note = new Note(timestamp, noteText);
            noteList.add(note);
            noteAdapter.notifyDataSetChanged();
        }
    }
}