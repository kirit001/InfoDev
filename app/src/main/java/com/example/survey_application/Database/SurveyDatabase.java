package com.example.survey_application.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = Info.class,version = 1)
public abstract class SurveyDatabase extends RoomDatabase {
   public abstract InfoDao infoDao ();
}
