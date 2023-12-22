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

    public void openCamera(View view) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
        photoURI = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPG_" + timeStamp + "_";

        // Environment.getExternalStoragePublicDirectory has been deprecated in API 29, hence replaced
        // Directory will be /storage/emulated/0/Pictures/JournalPics
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "JournalPics");

        // If you use above deprecated API use below one for API level >= 29
        // File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        if (!storageDir.exists()) {
            storageDir.mkdirs(); // Make sure you have set WRITE_EXTERNAL_STORAGE permission
        }

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        return image;
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