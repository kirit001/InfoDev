package com.example.survey_application.BottomNavigation.SurveyList;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.survey_application.BottomNavigation.Profile.ProfileActivity;
import com.example.survey_application.BottomNavigation.Survey.PersonalInfoActivity;
import com.example.survey_application.Database.Info;
import com.example.survey_application.Database.InfoDao;
import com.example.survey_application.Database.SurveyApplication;
import com.example.survey_application.Login.LoginActivity;
import com.example.survey_application.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SurveyListEditActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    EditText F_name, L_name, Gender, DOB, Bloodgroup, Citizen, P_Address, T_Address, House_No, Ward_no,
            email, mobile, landline, occupation, annualincome, education, fathername, mothername,
            relationship, religion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_list_edit);

        //Actionbar and its title
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Edit Survey");

        firebaseAuth = FirebaseAuth.getInstance();
        F_name = findViewById(R.id.et_firstname);
        L_name = findViewById(R.id.et_lastname);
        Gender = findViewById(R.id.et_gender);
        DOB = findViewById(R.id.et_dob);
        Bloodgroup = findViewById(R.id.et_bloodgroup);
        Citizen = findViewById(R.id.et_citizen);
        P_Address = findViewById(R.id.et_permaaddress);
        T_Address = findViewById(R.id.et_tempaddress);
        House_No = findViewById(R.id.et_houseno);
        Ward_no = findViewById(R.id.et_wardno);
        email = findViewById(R.id.et_email);
        mobile = findViewById(R.id.et_mobile);
        landline = findViewById(R.id.et_landline);
        occupation = findViewById(R.id.et_occupation);
        annualincome = findViewById(R.id.et_income);
        education = findViewById(R.id.et_education);
        fathername = findViewById(R.id.et_father);
        mothername = findViewById(R.id.et_mother);
        relationship = findViewById(R.id.et_relationship);
        religion = findViewById(R.id.et_religion);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavigation);

        bottomNavigationView.setSelectedItemId(R.id.action_List);

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
        final InfoDao userDao = ((SurveyApplication) getApplicationContext()).getMyDatabase().infoDao();

        Info info = userDao.getinfobyid(SurveyListEditActivity.this.getIntent().getLongExtra("id",
                0L));

        F_name.setText(info.getFirstname());
        L_name.setText(info.getLastname());
        Gender.setText(info.getGender());
        DOB.setText(info.getDateofbirth());
        Bloodgroup.setText(info.getBloodgroup());
        Citizen.setText(info.getCitizenshipnumber());
        P_Address.setText(info.getPermanentaddress());
        T_Address.setText(info.getTemporaryaddress());
        House_No.setText(info.getHousenumber());
        Ward_no.setText(info.getWardnumber());
        email.setText(info.getEmail());
        mobile.setText(info.getMobile());
        landline.setText(info.getLandline());
        occupation.setText(info.getOccupation());
        annualincome.setText(info.getAnnualincome());
        education.setText(info.getEducation());
        fathername.setText(info.getFathername());
        mothername.setText(info.getMothername());
        relationship.setText(info.getRelationshipstatus());
        religion.setText(info.getReligion());
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
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
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
        startActivity(new Intent(getApplicationContext(), SurveyListActivity.class));
        super.onBackPressed();
    }

    public void save(View view) {
        firebaseAuth = FirebaseAuth.getInstance();
        String Fname = F_name.getText().toString();
        String Lname = L_name.getText().toString();
        String gender = Gender.getText().toString();
        String dob = DOB.getText().toString();
        String blooggrp = Bloodgroup.getText().toString();
        String citizen = Citizen.getText().toString();
        String paddress = P_Address.getText().toString();
        String taddress = T_Address.getText().toString();
        String houseno = House_No.getText().toString();
        String wardno = Ward_no.getText().toString();
        String email1 = email.getText().toString();
        String mobile1 = mobile.getText().toString();
        String landline1 = landline.getText().toString();
        String occupation1 = occupation.getText().toString();
        String income = annualincome.getText().toString();
        String education1 = education.getText().toString();
        String father = fathername.getText().toString();
        String mother = mothername.getText().toString();
        String relation = relationship.getText().toString();
        String religion1 = religion.getText().toString();

        final InfoDao userDao = ((SurveyApplication) getApplicationContext()).getMyDatabase().infoDao();

        userDao.editinfo(SurveyListEditActivity.this.getIntent().getLongExtra("id",
                0L), Fname, Lname, gender, dob, blooggrp, citizen, paddress, taddress, houseno,
                wardno, email1, mobile1, landline1, occupation1, income, education1, father, mother,
                relation, religion1);
        Toast.makeText(this, "Survey Updated", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(),SurveyListActivity.class));
    }

    public void delete(View view) {
        final InfoDao userDao = ((SurveyApplication) getApplicationContext()).getMyDatabase().infoDao();

        userDao.deleteById(SurveyListEditActivity.this.getIntent().getLongExtra("id",
                0L));
        Toast.makeText(this, "Survey has been deleted", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(),SurveyListActivity.class));
    }
}