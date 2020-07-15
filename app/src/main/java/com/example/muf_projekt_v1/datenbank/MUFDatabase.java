package com.example.muf_projekt_v1.datenbank;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.muf_projekt_v1.Sensor.Speicher;
import com.example.muf_projekt_v1.dao.SensorDao;

@Database(entities = {Speicher.class},version= 1)
public abstract class MUFDatabase extends RoomDatabase {
public abstract SensorDao getSensorDao();
}
