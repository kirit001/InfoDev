package com.example.survey_application.BottomNavigation.SurveyList;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class SurveyListActivity extends AppCompatActivity implements SurveryListAdapter.OnDataClickListener {

    private static final String survey = "Survey";
    FirebaseAuth firebaseAuth;
    private RecyclerView recyclerView;
    private DatabaseReference dbRef;
    ProgressDialog tempDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_list);

        final InfoDao userDao = ((SurveyApplication) getApplicationContext()).getMyDatabase().infoDao();

        dbRef = FirebaseDatabase.getInstance().getReference(survey);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager((SurveyListActivity.this)));
        recyclerView.setAdapter(new SurveryListAdapter(this, userDao.getall()));

        //Actionbar and its title
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("List of Survey");

        firebaseAuth = FirebaseAuth.getInstance();


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
                        return true;
                }
                return false;
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
            startActivity(new Intent(SurveyListActivity.this, LoginActivity.class));
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
        startActivity(new Intent(getApplicationContext(), PersonalInfoActivity.class));
        super.onBackPressed();
    }

    @Override
    public void onClick(Info info) {
        startActivity(new Intent(getApplicationContext(), SurveyListEditActivity.class).putExtra("id", info.getId()));
    }

    public void backup(View view) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(survey);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    ds.getRef().removeValue();
                }
                final InfoDao userDao = ((SurveyApplication) getApplicationContext()).getMyDatabase().infoDao();
                List<Info> info = userDao.getall();
                String id = dbRef.push().getKey();
                dbRef.child(id).setValue(info);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Toast.makeText(this, "Backup has been made", Toast.LENGTH_SHORT).show();
    }

    public void restore(View view) {
        showProgressDialog();
        final InfoDao userDao = ((SurveyApplication) getApplicationContext()).getMyDatabase().infoDao();
        userDao.delete();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Survey");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    for (DataSnapshot Ds : ds.getChildren()) {
                        String fname = Ds.child("firstname").getValue(String.class);
                        String lname = Ds.child("lastname").getValue(String.class);
                        String gender = Ds.child("gender").getValue(String.class);
                        String dob = Ds.child("dateofbirth").getValue(String.class);
                        String bloodgrp = Ds.child("bloodgroup").getValue(String.class);
                        String citizen = Ds.child("citizenshipnumber").getValue(String.class);
                        String paddress = Ds.child("permanentaddress").getValue(String.class);
                        String taddress = Ds.child("temporaryaddress").getValue(String.class);
                        String houseno = Ds.child("housenumber").getValue(String.class);
                        String wardno = Ds.child("wardnumber").getValue(String.class);
                        String email = Ds.child("email").getValue(String.class);
                        String mobile = Ds.child("mobile").getValue(String.class);
                        String landline = Ds.child("landline").getValue(String.class);
                        String occupation = Ds.child("occupation").getValue(String.class);
                        String annualincom = Ds.child("annualincom").getValue(String.class);
                        String education = Ds.child("education").getValue(String.class);
                        String fathername = Ds.child("fathername").getValue(String.class);
                        String mothername = Ds.child("mothername").getValue(String.class);
                        String relationship = Ds.child("relationshipstatus").getValue(String.class);
                        String religion = Ds.child("religion").getValue(String.class);

                        final InfoDao userDao = ((SurveyApplication) getApplicationContext()).getMyDatabase().infoDao();

                        Info info = new Info();

                        info.setFirstname(fname);
                        info.setLastname(lname);
                        info.setGender(gender);
                        info.setDateofbirth(dob);
                        info.setBloodgroup(bloodgrp);
                        info.setCitizenshipnumber(citizen);
                        info.setPermanentaddress(paddress);
                        info.setTemporaryaddress(taddress);
                        info.setHousenumber(houseno);
                        info.setWardnumber(wardno);
                        info.setMobile(mobile);
                        info.setEmail(email);
                        info.setLandline(landline);
                        info.setOccupation(occupation);
                        info.setAnnualincome(annualincom);
                        info.setEducation(education);
                        info.setFathername(fathername);
                        info.setMothername(mothername);
                        info.setRelationshipstatus(relationship);
                        info.setReligion(religion);

                        userDao.save(info);
                    }
                }
                recyclerview(userDao.getall());
                dismissProgressDialog();
                Toast.makeText(SurveyListActivity.this, "Restoring complete", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SurveyListActivity.this, "Restoring could not be complete", Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }
        });
    }

    void showProgressDialog() {
        tempDialog = new ProgressDialog(this);
        tempDialog.setCancelable(false);
        tempDialog.setMessage("Please wait");
        tempDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        tempDialog.show();
    }

    void dismissProgressDialog() {
        if (tempDialog != null) tempDialog.dismiss();
    }

    public void recyclerview(List<Info> infos){
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager((SurveyListActivity.this)));
        recyclerView.setAdapter(new SurveryListAdapter(this, infos));
    }
}