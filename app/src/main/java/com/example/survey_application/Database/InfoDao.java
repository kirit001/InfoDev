package com.example.survey_application.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public
interface InfoDao {

    @Insert
    long save(Info info);

    @Query("UPDATE surveyinfo SET occupation = :Occupation , annualincome = :Income , " +
            "education = :Education , fathername = :Father , mothername = :Mother , " +
            "relationshipstatus = :Relation , religion = :Religion WHERE id = :id")
    int updateinfo(Long id, String Occupation, String Income, String Education, String Father,
                   String Mother, String Relation, String Religion);

    @Query("SELECT * FROM surveyinfo WHERE id = :id")
    Info getinfobyid(Long id);

    @Query("SELECT * FROM surveyinfo")
    List<Info> getall();

    @Query("DELETE FROM surveyinfo WHERE id = :id")
    void deleteById(Long id);

    @Query("UPDATE surveyinfo SET firstname = :Firstname,lastname = :Lastname,gender = :Gender," +
            "dateofbirth = :dateofbirth,bloodgroup = :bloodgroup,citizenshipnumber = :citizenshipnumber," +
            "permanentaddress = :permanentaddress,temporaryaddress = :temporaryaddress," +
            "housenumber = :housenumber, wardnumber = :wardnumber,email = :email,mobile = :mobile," +
            "landline = :Landline ,occupation = :Occupation , annualincome = :Income , " +
            "education = :Education , fathername = :Father , mothername = :Mother , " +
            "relationshipstatus = :Relation , religion = :Religion WHERE id = :id")
    int editinfo(Long id, String Firstname, String Lastname, String Gender, String dateofbirth,
                 String bloodgroup, String citizenshipnumber, String permanentaddress,
                 String temporaryaddress, String housenumber, String wardnumber, String email,
                 String mobile, String Landline, String Occupation, String Income, String Education,
                 String Father, String Mother, String Relation, String Religion);

    @Query("DELETE FROM surveyinfo")
    void delete();

}
