package com.example.survey_application.BottomNavigation.Profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.survey_application.BottomNavigation.Survey.PersonalInfoActivity;
import com.example.survey_application.BottomNavigation.SurveyList.SurveyListActivity;
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
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";

    private FirebaseAuth firebaseAuth;
    private TextView username,mobile,email,address;
    private ImageView profilepic;
    ProgressDialog tempDialog = null;
    private static final String User = "user";
    private DatabaseReference dbRef;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Actionbar and its title
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Profile");

        //init
        firebaseAuth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences("loginref", MODE_PRIVATE);
        dbRef = FirebaseDatabase.getInstance().getReference(User);
        username = findViewById(R.id.tv_username1);
        mobile = findViewById(R.id.tv_mobile);
        email = findViewById(R.id.tv_email);
        address = findViewById(R.id.tv_address);
        profilepic = findViewById(R.id.iv_profile);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavigation);

        bottomNavigationView.setSelectedItemId(R.id.action_profile);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        startActivity(new Intent(getApplicationContext(), PersonalInfoActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.action_profile:
                        return true;
                    case R.id.action_List:
                        startActivity(new Intent(getApplicationContext(), SurveyListActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        showProgressDialog();

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (sharedPreferences.getString("email", "").equals(ds.child("email").getValue(String.class))) {
                        address.setText(ds.child("address").getValue(String.class));
                        email.setText(ds.child("email").getValue(String.class));
                        mobile.setText(ds.child("mobile").getValue(String.class));
                        username.setText(ds.child("username").getValue(String.class));
                        String pic = ds.child("image").getValue(String.class);
                        Picasso.get().load(pic).into(profilepic);
                        dismissProgressDialog();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
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

    private void checkUserStatus() {
        //get current user
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user == null) {
            //user not signed in, go to login activity
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            finish();
        }
    }

    @Override
    protected void onStart() {
        //check on start of app
        checkUserStatus();
        super.onStart();
    }

    /*inflate option menu*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate menu
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
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
}