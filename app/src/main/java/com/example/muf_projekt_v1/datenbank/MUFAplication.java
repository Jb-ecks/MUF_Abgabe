package com.example.muf_projekt_v1.datenbank;

import android.app.Application;

import androidx.room.Room;

public class MUFAplication extends Application {
    private MUFDatabase mufDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        mufDatabase = Room.databaseBuilder(this,MUFDatabase.class,"mufProjekt")
                .build();
    }
    public MUFDatabase getDatabase() {return mufDatabase;}
}


