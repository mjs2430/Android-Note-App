package com.example.journal;

        import android.Manifest;
        import android.content.ContentValues;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.graphics.Bitmap;
        import android.net.Uri;
        import android.os.Bundle;
        import android.os.Environment;
        import android.provider.MediaStore;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.Toast;

        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.core.content.FileProvider;

        import com.bumptech.glide.Glide;

        import java.io.File;
        import java.io.IOException;
        import java.text.SimpleDateFormat;
        import java.util.Date;

public class NewNoteActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1888;
    private Uri photoURI;

    private EditText noteEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        noteEditText = findViewById(R.id.note_edit_text);

    }
    

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            final ImageView imageView = findViewById(R.id.imageView);

            Glide.with(this)
                    .load(photoURI)
                    .into(imageView);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (photoURI != null) {
                        // delete image from storage
                        getContentResolver().delete(photoURI, null, null);
                        // clear the ImageView
                        imageView.setImageDrawable(null);
                        photoURI = null;
                        Toast.makeText(NewNoteActivity.this, "Image Deleted", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void saveNote(View view) {
        String noteText = noteEditText.getText().toString();

        Intent intent = new Intent();
        intent.putExtra("note_text", noteText);
        setResult(RESULT_OK, intent);
        finish();
    }

}// end