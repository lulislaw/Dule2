package com.example.dule2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText email_reg_edittext, fio_reg_edittext, class_reg_edittext, password_reg_edittext;
    Button registration_button;
    TextView login_tv;
    ProgressBar progressBar_reg;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
    }

    public void init() {
        email_reg_edittext = findViewById(R.id.email_reg_edittext);
        fio_reg_edittext = findViewById(R.id.fio_reg_edittext);
        class_reg_edittext = findViewById(R.id.class_reg_edittext);
        password_reg_edittext = findViewById(R.id.password_reg_edittext);
        registration_button = findViewById(R.id.registration_button);
        registration_button.setOnClickListener(this);
        login_tv = findViewById(R.id.login_tv);
        login_tv.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        progressBar_reg = findViewById(R.id.progressbar_reg);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_tv:
                startActivity(new Intent(this, LoginActivity.class));
                break;

            case R.id.registration_button:
                userRegister();
        }
    }

    private void userRegister() {
        String email = email_reg_edittext.getText().toString().trim();
        String fio = fio_reg_edittext.getText().toString().trim();
        String class_ = class_reg_edittext.getText().toString().trim();
        String password = password_reg_edittext.getText().toString().trim();

        if (email.isEmpty()) {
            email_reg_edittext.setError("Введите Email");
            email_reg_edittext.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_reg_edittext.setError("Введите действительный Email");
            email_reg_edittext.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            password_reg_edittext.setError("Придумайте пароль");
            password_reg_edittext.requestFocus();
            return;
        }

        if (password.length() < 6) {
            password_reg_edittext.setError("Минимальная длина 6 символов");
            password_reg_edittext.requestFocus();
            return;
        }

        progressBar_reg.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    User user = new User(email, fio, class_);
                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                progressBar_reg.setVisibility(View.GONE);
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                user.sendEmailVerification();
                                Toast.makeText(RegisterActivity.this, "Письмо для подтверждения регистрации отправлено на почту", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            }
                            else {
                                Toast.makeText(RegisterActivity.this, "Регистрация не удалась, повторите попытку!", Toast.LENGTH_SHORT).show();
                                progressBar_reg.setVisibility(View.GONE);
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Регистрация не удалась", Toast.LENGTH_SHORT).show();
                    progressBar_reg.setVisibility(View.GONE);
                }
            }
        });

    }
}