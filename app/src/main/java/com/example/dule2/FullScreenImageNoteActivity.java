package com.example.dule2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

public class FullScreenImageNoteActivity extends AppCompatActivity {

    SubsamplingScaleImageView fullScreenImageNote;
    String ImageFromNote;
    String hiddenOrNot = "HIDDEN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image_note);

        ImageFromNote = "";

        fullScreenImageNote = findViewById(R.id.fullscreenImageNote);
        ImageFromNote = getIntent().getStringExtra("image_2");
        fullScreenImageNote.setImage(ImageSource.bitmap((BitmapFactory.decodeFile(ImageFromNote))));

        hideSystemBars();


        fullScreenImageNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hiddenOrNot.equals("HIDDEN"))
                    showSystemBars();
                else {
                    hideSystemBars();
                }
            }
        });
    }

    protected void hideSystemBars() {
        WindowInsetsControllerCompat windowInsetsController =
                ViewCompat.getWindowInsetsController(getWindow().getDecorView());
        if (windowInsetsController == null) {
            return;
        }
        windowInsetsController.setSystemBarsBehavior(
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        );
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());

        hiddenOrNot = "HIDDEN";
    }

    protected void showSystemBars() {
        WindowInsetsControllerCompat windowInsetsController =
                ViewCompat.getWindowInsetsController(getWindow().getDecorView());
        if (windowInsetsController == null) {
            return;
        }

        windowInsetsController.show(WindowInsetsCompat.Type.systemBars());

        hiddenOrNot = "NOT_HIDDEN";
    }
}

