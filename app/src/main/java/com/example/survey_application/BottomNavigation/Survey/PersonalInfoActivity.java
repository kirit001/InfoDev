package com.example.survey_application.BottomNavigation.Survey;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.survey_application.BottomNavigation.Profile.ProfileActivity;
import com.example.survey_application.BottomNavigation.SurveyList.SurveyListActivity;
import com.example.survey_application.Database.Info;
import com.example.survey_application.Database.InfoDao;
import com.example.survey_application.Database.SurveyApplication;
import com.example.survey_application.Login.LoginActivity;
import com.example.survey_application.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

public class PersonalInfoActivity extends AppCompatActivity {

    //declaring views
    EditText firstname, lastname, bloodgroup, citizen, permanentaddress, temporaryaddress, houseno, wardno,
            email, mobile, landline;
    Button add;
    FirebaseAuth firebaseAuth;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private TextView dateofbirth;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        //Actionbar and its title
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Survey");

        //init
        firebaseAuth = FirebaseAuth.getInstance();

        //init
        firstname = findViewById(R.id.et_firstname);
        lastname = findViewById(R.id.et_lastname);
        radioGroup = findViewById(R.id.rg_gender);
        dateofbirth = findViewById(R.id.tv_dob);
        bloodgroup = findViewById(R.id.et_bloodgroup);
        citizen = findViewById(R.id.et_citizen);
        permanentaddress = findViewById(R.id.et_permaaddress);
        temporaryaddress = findViewById(R.id.et_tempaddress);
        houseno = findViewById(R.id.et_houseno);
        wardno = findViewById(R.id.et_wardno);
        email = findViewById(R.id.et_email);
        mobile = findViewById(R.id.et_mobile);
        landline = findViewById(R.id.et_landline);

        dateofbirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(PersonalInfoActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable((new ColorDrawable(Color.TRANSPARENT)));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = year + "/" + month + "/" + day;
                dateofbirth.setText(date);
            }
        };

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavigation);

        bottomNavigationView.setSelectedItemId(R.id.action_home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
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

        add = findViewById(R.id.bt_next);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check if the data entered in the fields are empty or invalid

                if (firstname.getText().toString().isEmpty()) {
                    firstname.setError("FirstName cannot be Empty");
                    firstname.setFocusable(true);
                }
                if (lastname.getText().toString().isEmpty()) {
                    lastname.setError("LastName cannot be Empty");
                    lastname.setFocusable(true);
                }
                if (bloodgroup.getText().toString().isEmpty()) {
                    bloodgroup.setError("Blood group cannot be Empty");
                    bloodgroup.setFocusable(true);
                }
                if (citizen.getText().toString().isEmpty()) {
                    citizen.setError("Citizenship Number cannot be Empty");
                    citizen.setFocusable(true);
                }
                if (permanentaddress.getText().toString().isEmpty()) {
                    permanentaddress.setError("Permanent address cannot be Empty");
                    permanentaddress.setFocusable(true);
                }
                if (!permanentaddress.getText().toString().isEmpty()) {
                    temporaryaddress.setText(permanentaddress.getText().toString());
                }
                if (temporaryaddress.getText().toString().isEmpty()) {
                    temporaryaddress.setError("Temporary address cannot be Empty");
                    temporaryaddress.setFocusable(true);
                }
                if (wardno.getText().toString().isEmpty()) {
                    wardno.setError("Ward Number cannot be Empty");
                    wardno.setFocusable(true);
                }
                if (email.getText().toString().isEmpty()) {
                    email.setError("Email cannot be Empty");
                    email.setFocusable(true);
                }
                if (!mobile.getText().toString().isEmpty()) {
                    landline.setText(mobile.getText().toString());
                }
                if (mobile.getText().toString().isEmpty()) {
                    mobile.setError("Mobile cannot be Empty");
                    mobile.setFocusable(true);
                } else {
                    //get the entered data and store them in variables and send them to database for storage
                    String fname = firstname.getText().toString();
                    String lname = lastname.getText().toString();
                    String Gender = radioButton.getText().toString();
                    String dob = dateofbirth.getText().toString();
                    String bloodgrp = bloodgroup.getText().toString();
                    String citizenship = citizen.getText().toString();
                    String paddress = permanentaddress.getText().toString();
                    String taddress = temporaryaddress.getText().toString();
                    String house = houseno.getText().toString();
                    String ward = wardno.getText().toString();
                    String email1 = email.getText().toString();
                    String mobile1 = mobile.getText().toString();
                    String land = landline.getText().toString();

                    final InfoDao userDao = ((SurveyApplication) getApplicationContext()).getMyDatabase().infoDao();

                    Info info = new Info();

                    info.setFirstname(fname);
                    info.setLastname(lname);
                    info.setGender(Gender);
                    info.setDateofbirth(dob);
                    info.setBloodgroup(bloodgrp);
                    info.setCitizenshipnumber(citizenship);
                    info.setPermanentaddress(paddress);
                    info.setTemporaryaddress(taddress);
                    info.setHousenumber(house);
                    info.setWardnumber(ward);
                    info.setMobile(mobile1);
                    info.setEmail(email1);
                    info.setLandline(land);

                    //get the primary key i.e. id
                    long id = userDao.save(info);

                    startActivity(new Intent(getApplicationContext(),
                            QualificationActivity.class).putExtra("id", id));
                }
            }
        });
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
            startActivity(new Intent(PersonalInfoActivity.this, LoginActivity.class));
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

    public void checkButton(View v) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        super.onBackPressed();
    }
}