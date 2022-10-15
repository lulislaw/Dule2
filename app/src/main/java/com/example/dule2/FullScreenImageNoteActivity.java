package com.example.dule2;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

public class FullScreenImageNoteActivity extends AppCompatActivity {

    SubsamplingScaleImageView fullScreenImageNote;
    String ImageFromNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image_note);

        ImageFromNote = "";

        fullScreenImageNote = findViewById(R.id.fullscreenImageNote);

        ImageFromNote = getIntent().getStringExtra("image_2");

        fullScreenImageNote.setImage(ImageSource.bitmap((BitmapFactory.decodeFile(ImageFromNote))));
    }
}