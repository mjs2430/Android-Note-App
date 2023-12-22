package com.example.journal;

        import android.content.ContentValues;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.net.Uri;
        import android.os.Bundle;
        import android.os.Environment;
        import android.provider.MediaStore;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.Toast;
        import androidx.appcompat.app.AlertDialog;

        import androidx.appcompat.app.AppCompatActivity;

        import com.bumptech.glide.Glide;

        import java.text.SimpleDateFormat;
        import java.util.Date;
        import android.provider.MediaStore.Images.Media;

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
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            photoURI = createImageUri(); // Get URI for image
            if (photoURI != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private Uri createImageUri() {
        // Mime type for jpg files
        String mimeType = "image/jpeg";

        ContentValues values = new ContentValues();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        values.put(Media.DISPLAY_NAME, imageFileName);
        values.put(Media.MIME_TYPE, mimeType);
        values.put(Media.RELATIVE_PATH, Environment.DIRECTORY_DCIM + "/Event_Journal");

        // InstrumentationRegistry.getTargetContext() was used to get the content resolver. In your case, you may directly use `getContentResolver()`
        return getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, values);
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
                        new AlertDialog.Builder(NewNoteActivity.this)
                                .setTitle("Delete Image")
                                .setMessage("Are you sure you want to delete this image?")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // delete image from storage
                                        getContentResolver().delete(photoURI, null, null);
                                        // clear the ImageView
                                        imageView.setImageDrawable(null);
                                        photoURI = null;
                                        Toast.makeText(NewNoteActivity.this, "Image Deleted", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setNegativeButton(android.R.string.no, null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
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