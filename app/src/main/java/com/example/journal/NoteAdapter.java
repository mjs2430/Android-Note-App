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

// replace '-' with bullets and add bullet if new line starts with space
        String[] lines = note.getText().split("\n");
        StringBuilder noteText = new StringBuilder();

        for (String line : lines) {
            if (line.startsWith("  ")) {
                noteText.append("\n\t\t\u25cb ").append(line.substring(2));
            } else if (line.startsWith(" ")) {
                noteText.append("\n\t\u25cf ").append(line.substring(1));
            } else {
                line = line.replace(" -", "\n\t\u2023"); // replace " -" with tab and bullet
                line = line.replace("-", "\u2022"); // replace "-" with bullet
                noteText.append("\n").append(line);
            }
        }
        noteTextView.setText(noteText.toString().trim()); // trim() to remove the leading '\n'

        return convertView;
    }


}