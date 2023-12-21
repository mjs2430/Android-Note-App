package com.example.journal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class NewNoteActivity extends AppCompatActivity {

    private EditText noteEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        noteEditText = findViewById(R.id.note_edit_text);
    }

    public void saveNote(View view) {
        String noteText = noteEditText.getText().toString();

        Intent intent = new Intent();
        intent.putExtra("note_text", noteText);
        setResult(RESULT_OK, intent);
        finish();
    }
}