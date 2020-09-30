package com.example.survey_application.Login;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.survey_application.BottomNavigation.Survey.PersonalInfoActivity;
import com.example.survey_application.R;
import com.example.survey_application.ResetPassword.PasswordResetActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    //declaring progressbar
    ProgressDialog tempDialog = null;
    //declaring shared preference
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    //declaring views,checkbox and FirebaseAuth
    private EditText email1, pass;
    private CheckBox checkbox;
    private FirebaseAuth mAuth;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Actionbar and its title
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Create Account");

        //init
        email1 = findViewById(R.id.tv_username);
        pass = findViewById(R.id.password);
        checkbox = findViewById(R.id.checkBox);
        mAuth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences("loginref", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        //creating sharedpreference
        boolean savelogin = sharedPreferences.getBoolean("savelogin", true);

        //storing email and password in shared preference when checkbox is checked
        if (savelogin) {
            email1.setText(sharedPreferences.getString("email", null));
            pass.setText(sharedPreferences.getString("password", null));
        }
    }

    //on click handler for login button
    public void login(View view) {
        String Username = email1.getText().toString();
        String Password = pass.getText().toString();
        //checking if email and password is entered or not
        if (!Patterns.EMAIL_ADDRESS.matcher(Username).matches()) {
            email1.setError("Invalid Email");
            email1.setFocusable(true);
        } else if (Password.isEmpty()) {
            pass.setError("Password is empty");
            pass.setFocusable(true);
        } else {
            signIn(Username, Password);
        }
    }

    public void signIn(String email1, String pass) {

        if (checkbox.isChecked()) {
            //save username and password when remember me is ticked
            editor.clear();
            editor.putBoolean("savelogin", true);
            editor.putString("email", email1);
            editor.putString("password", pass);
            editor.putBoolean("islogged", true);
            editor.commit();

            showProgressDialog();
            mAuth.signInWithEmailAndPassword(email1, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            dismissProgressDialog();
                            if (task.isSuccessful()) {
                                // Sign in success
                                Intent a = new Intent(LoginActivity.this, PersonalInfoActivity.class);
                                startActivity(a);
                            } else {
                                // If sign in fails.
                                dismissProgressDialog();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {

                @Override
                public void onFailure(@NonNull Exception e) {
                    dismissProgressDialog();
                    Toast.makeText(LoginActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            editor.clear();
            editor.putBoolean("savelogin", false);
            editor.putString("email", email1);
            editor.putString("password", pass);
            editor.commit();

            showProgressDialog();
            mAuth.signInWithEmailAndPassword(email1, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            dismissProgressDialog();
                            if (task.isSuccessful()) {
                                // Sign in success
                                Intent a = new Intent(LoginActivity.this, PersonalInfoActivity.class);
                                startActivity(a);
                            } else {
                                // If sign in fails.
                                dismissProgressDialog();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {

                @Override
                public void onFailure(@NonNull Exception e) {
                    dismissProgressDialog();
                    Toast.makeText(LoginActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    //showing progress dialog
    void showProgressDialog() {
        tempDialog = new ProgressDialog(this);
        tempDialog.setCancelable(false);
        tempDialog.setMessage("Please wait");
        tempDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        tempDialog.show();
    }

    //dismissing progress dialog
    void dismissProgressDialog() {
        if (tempDialog != null) tempDialog.dismiss();
    }

    //back pressed handler
    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
        super.onBackPressed();
    }

    public void forgotpassword(View view) {
        startActivity(new Intent(getApplicationContext(), PasswordResetActivity.class));
    }
}