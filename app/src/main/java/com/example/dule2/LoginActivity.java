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

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText email_login_edittext, password_login_edittext;
    Button login_button;
    TextView registration_tv;
    FirebaseAuth mAuth;
    ProgressBar progressbar_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    public void init() {
        email_login_edittext = findViewById(R.id.email_login_edittext);
        password_login_edittext = findViewById(R.id.password_login_edittext);
        login_button = findViewById(R.id.login_button);
        login_button.setOnClickListener(this);
        registration_tv = findViewById(R.id.registration_tv);
        registration_tv.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        progressbar_login = findViewById(R.id.progressbar_login);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.registration_tv:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;

            case R.id.login_button:
                userLogin();
        }
    }

    private void userLogin() {
        String email = email_login_edittext.getText().toString().trim();
        String password = password_login_edittext.getText().toString().trim();

        if (email.isEmpty()) {
            email_login_edittext.setError("Введите Email");
            email_login_edittext.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_login_edittext.setError("Введите действительный Email");
            email_login_edittext.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            password_login_edittext.setError("Введите пароль");
            password_login_edittext.requestFocus();
            return;
        }

        progressbar_login.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user.isEmailVerified()) {
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    } else {
                        progressbar_login.setVisibility(View.GONE);
                        //user.sendEmailVerification();
                        Toast.makeText(LoginActivity.this, "Подтвердите почту по ссылке из письма", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressbar_login.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Неправильный логин или пароль!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}