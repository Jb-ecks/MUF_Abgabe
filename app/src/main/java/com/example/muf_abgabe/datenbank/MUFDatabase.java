package com.example.muf_abgabe.datenbank;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.muf_abgabe.Sensor.Speicher;
import com.example.muf_abgabe.dao.SensorDao;

@Database(entities = {Speicher.class},version= 1)
public abstract class MUFDatabase extends RoomDatabase {
public abstract SensorDao getSensorDao();
}
