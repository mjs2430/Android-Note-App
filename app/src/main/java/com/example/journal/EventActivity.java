package com.example.journal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EventActivity extends AppCompatActivity {

    private String selectedEvent;
    private ListView noteListView;
    private List<Note> noteList = new ArrayList<>();
    private NoteAdapter noteAdapter;
    private TextView eventTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        selectedEvent = getIntent().getStringExtra("event");
        setTitle(selectedEvent);

        selectedEvent = getIntent().getStringExtra("event");

        eventTitle = findViewById(R.id.event_title); // Add this
        eventTitle.setText(selectedEvent); // Add this

        noteListView = findViewById(R.id.note_list_view);
        noteAdapter = new NoteAdapter(this, noteList);
        noteListView.setAdapter(noteAdapter);

        // Load notes from internal storage
        loadNotes();

        noteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Note selectedNote = noteList.get(position);
                Intent intent = new Intent(EventActivity.this, EditNoteActivity.class);
                intent.putExtra("note_index", position);
                intent.putExtra("note_text", selectedNote.getText());
                startActivityForResult(intent, 3);
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

        if (resultCode == RESULT_OK) {
            if (requestCode == 2) {
                // The result is coming from NewNoteActivity
                String noteText = data.getStringExtra("note_text");
                String timestamp = new SimpleDateFormat("MM/dd/yy hh:mm a", Locale.getDefault()).format(new Date());
                Note note = new Note(timestamp, noteText);
                noteList.add(note);
            } else if (requestCode == 3) {
                // The result is coming from EditNoteActivity
                int noteIndex = data.getIntExtra("note_index", 0);
                String editedNoteText = data.getStringExtra("edited_note_text");
                Note editedNote = new Note(new SimpleDateFormat("MM/dd/yy hh:mm a", Locale.getDefault()).format(new Date()), editedNoteText);
                noteList.set(noteIndex, editedNote);
            }
            noteAdapter.notifyDataSetChanged();
            saveNotes();
        }
    }

    // ... other methods like saveNotes() and loadNotes()

    private void saveNotes() {
        try {
            FileOutputStream fos = openFileOutput(selectedEvent, MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(noteList);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadNotes() {
        try {
            FileInputStream fis = openFileInput(selectedEvent);
            ObjectInputStream ois = new ObjectInputStream(fis);
            noteList = (ArrayList<Note>) ois.readObject();
            ois.close();
            fis.close();
            noteAdapter = new NoteAdapter(this, noteList);
            noteListView.setAdapter(noteAdapter);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    }