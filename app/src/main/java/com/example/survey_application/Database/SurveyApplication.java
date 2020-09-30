package com.example.survey_application.Database;

import android.app.Application;

import androidx.room.Room;

public class SurveyApplication extends Application {
    SurveyDatabase myDatabase;

    @Override
    public void onCreate() {
        super.onCreate();

        // when upgrading versions, kill the original tables by using fallbackToDestructiveMigration()
        myDatabase = Room.databaseBuilder(this, SurveyDatabase.class, "Survey").allowMainThreadQueries().build();
    }

    public SurveyDatabase getMyDatabase() {
        return myDatabase;
    }
}
