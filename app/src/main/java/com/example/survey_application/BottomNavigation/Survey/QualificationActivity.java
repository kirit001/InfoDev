package com.example.survey_application.BottomNavigation.Survey;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.survey_application.BottomNavigation.Profile.ProfileActivity;
import com.example.survey_application.BottomNavigation.SurveyList.SurveyListActivity;
import com.example.survey_application.Database.InfoDao;
import com.example.survey_application.Database.SurveyApplication;
import com.example.survey_application.Login.LoginActivity;
import com.example.survey_application.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class QualificationActivity extends AppCompatActivity {

    EditText occupation, income, education, father, mother, religion;
    FirebaseAuth firebaseAuth;
    private RadioGroup radioGroup;
    private RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qualification);

        //Actionbar and its title
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Survey");

        //init
        firebaseAuth = FirebaseAuth.getInstance();

        //init
        occupation = findViewById(R.id.et_occupation);
        income = findViewById(R.id.et_income);
        education = findViewById(R.id.et_education);
        father = findViewById(R.id.et_father);
        mother = findViewById(R.id.et_mother);
        radioGroup = findViewById(R.id.rg_relation);
        religion = findViewById(R.id.et_religion);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavigation);

        bottomNavigationView.setSelectedItemId(R.id.action_home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        startActivity(new Intent(getApplicationContext(), PersonalInfoActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.action_profile:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.action_List:
                        startActivity(new Intent(getApplicationContext(), SurveyListActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }

    public void next(View view) {
        String occupation1 = occupation.getText().toString();
        String income1 = income.getText().toString();
        String education1 = education.getText().toString();
        String fathername = father.getText().toString();
        String mothername = mother.getText().toString();
        String relation = radioButton.getText().toString();
        String religion1 = religion.getText().toString();

        final InfoDao userDao = ((SurveyApplication) getApplicationContext()).getMyDatabase().infoDao();

        userDao.updateinfo(QualificationActivity.this.getIntent().getLongExtra("id",
                0L), occupation1, income1, education1, fathername, mothername, relation, religion1);
        Toast.makeText(this, "Survey added", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), PersonalInfoActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate menu
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void checkUserStatus() {
        //get current user
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user == null) {
            //user not signed in, go to login activity
            startActivity(new Intent(QualificationActivity.this, LoginActivity.class));
            finish();
        }
    }

    /*handel menu item clicks*/

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //get item id
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            firebaseAuth.signOut();
            checkUserStatus();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        super.onBackPressed();
    }

    public void relation(View view) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
    }
}