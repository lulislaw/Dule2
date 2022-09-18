package com.example.dule2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.w3c.dom.Text;

import java.io.FileWriter;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText email_login_edittext, password_login_edittext;
    Button login_button;
    TextView registration_tv, forgot_pass_tv;
    FirebaseAuth mAuth;
    ProgressBar progressbar_login;
    ImageButton google_login_button;

    GoogleSignInClient googleSignInClient;

    public static final String PREFS_NAME = "MY_PREFS";

    /*Boolean checkIf;
    SharedPreferences sharedPreferences;
    String APP_PREFERENCES = "APP_PREFERENCES";
    String PREF_SOME_TEXT_VALUE = "PREF_SOME_TEXT_VALUE";*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    private void init() {
        email_login_edittext = findViewById(R.id.email_login_edittext);
        password_login_edittext = findViewById(R.id.password_login_edittext);
        login_button = findViewById(R.id.login_button);
        login_button.setOnClickListener(this);
        registration_tv = findViewById(R.id.registration_tv);
        registration_tv.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        progressbar_login = findViewById(R.id.progressbar_login);
        forgot_pass_tv = findViewById(R.id.forgot_pass_tv);
        forgot_pass_tv.setOnClickListener(this);
        /*sharedPreferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);*/
        google_login_button = findViewById(R.id.google_login_button);
        google_login_button.setOnClickListener(this);
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
    }

    private void googleLogin() {

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.registration_tv:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;

            case R.id.login_button:
                userLogin();
                break;

            case R.id.forgot_pass_tv:
                startActivity(new Intent(this, ResetPasswordActivity.class));
                break;

            case R.id.google_login_button:
                Intent intent = googleSignInClient.getSignInIntent();
                startActivityForResult(intent, 1234);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1234) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount googleSignInAccount = task.getResult(ApiException.class);

                AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
                FirebaseAuth.getInstance().signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
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
                        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("hasLoggedIn", true);
                        editor.commit();
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