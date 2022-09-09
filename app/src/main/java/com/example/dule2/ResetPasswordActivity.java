package com.example.dule2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    TextView back_from_reset_tv;
    Button reset_password_button;
    ProgressBar progressbar_reset;
    EditText reset_password_edittext;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        init();
    }

    private void init() {
        back_from_reset_tv = findViewById(R.id.back_from_reset_tv);
        back_from_reset_tv.setOnClickListener(this);
        reset_password_button = findViewById(R.id.reset_password_button);
        reset_password_button.setOnClickListener(this);
        progressbar_reset = findViewById(R.id.progressbar_reset);
        auth = FirebaseAuth.getInstance();
        reset_password_edittext = findViewById(R.id.reset_password_edittext);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_from_reset_tv:
                onBackPressed();
                break;

            case R.id.reset_password_button:
                resetPassword();
                break;
        }
    }

    private void resetPassword() {
        String email = reset_password_edittext.getText().toString().trim();

        if (email.isEmpty()) {
            reset_password_edittext.setError("Введите Email");
            reset_password_edittext.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            reset_password_edittext.setError("Введите действительный Email");
            reset_password_edittext.requestFocus();
            return;
        }

        progressbar_reset.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ResetPasswordActivity.this, "Письмо для восстановления отправлено на почту", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ResetPasswordActivity.this, "Вы не зарегистрированы", Toast.LENGTH_SHORT).show();
                }
                progressbar_reset.setVisibility(View.GONE);
            }
        });

    }
}