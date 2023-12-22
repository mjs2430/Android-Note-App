package com.example.journal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditNoteActivity extends AppCompatActivity {

    private EditText noteTextEditText;
    private int noteIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        noteTextEditText = findViewById(R.id.note_text_edit_text);

        // Get the index and the text of the note to be edited.
        noteIndex = getIntent().getIntExtra("note_index", 0);
        String noteText = getIntent().getStringExtra("note_text");

        // Set the text of the note in the EditText field.
        noteTextEditText.setText(noteText);
    }

    public void saveEditedNote(View view) {
        String editedNoteText = noteTextEditText.getText().toString();

        Intent data = new Intent();
        data.putExtra("note_index", noteIndex);
        data.putExtra("edited_note_text", editedNoteText);
        setResult(RESULT_OK, data);

        finish();
    }
}