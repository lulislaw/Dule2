package com.example.dule2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    TextView profile_fio_tv, profile_class_tv, profile_email_tv, profile_password_tv;
    Button profile_logout_button;

    private FirebaseUser user;
    private DatabaseReference databaseReference;

    private String userID;

    public static final String PREFS_NAME = "MY_PREFS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        init();
    }

    private void init() {
        profile_fio_tv = findViewById(R.id.profile_fio_tv);
        profile_class_tv = findViewById(R.id.profile_class_tv);
        profile_email_tv = findViewById(R.id.profile_email_tv);
        profile_password_tv = findViewById(R.id.profile_password_tv);

        profile_logout_button = findViewById(R.id.profile_logout_button);
        profile_logout_button.setOnClickListener(this);

        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null) {
                    String fio = userProfile.fio;
                    String email = userProfile.email;
                    String class_ = userProfile.class_;

                    profile_fio_tv.setText(fio);
                    profile_email_tv.setText(email);
                    profile_class_tv.setText(class_);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Что-то пошло не по сценарию...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profile_logout_button:
                FirebaseAuth.getInstance().signOut();

                SharedPreferences sharedPreferences = getSharedPreferences(ProfileActivity.PREFS_NAME, 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("hasLoggedIn", false);
                editor.commit();

                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                overridePendingTransition(0, android.R.anim.fade_out);
        }
    }
}