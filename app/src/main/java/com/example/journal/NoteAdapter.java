package com.example.journal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class NoteAdapter extends ArrayAdapter<Note> {

    public NoteAdapter(Context context, List<Note> notes) {
        super(context, 0, notes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Note note = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_note, parent, false);
        }

        TextView timestampTextView = convertView.findViewById(R.id.timestamp_text_view);
        TextView noteTextView = convertView.findViewById(R.id.note_text_view);

        timestampTextView.setText(note.getTimestamp());
        noteTextView.setText(note.getText());

        // replace '-' with bullets
        String noteText = note.getText()
                .replace(" -", "\t\u2023")  // replace " -" with tab and bullet
                .replace("-", "\u2022");  // replace "-" with bullet

        noteTextView.setText(noteText);

        return convertView;
    }


}