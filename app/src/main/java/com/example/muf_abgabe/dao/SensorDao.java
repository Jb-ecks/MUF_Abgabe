package com.example.muf_abgabe.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.muf_abgabe.Sensor.Speicher;

import java.util.List;

@Dao
public abstract class SensorDao {

    //@Query("SELECT * FROM messung WHERE messungname=:messungname")
    //public abstract LiveData<Speicher> getUserByEmail(String messungname);

    @Query("SELECT * FROM messung")
    public abstract List<Speicher> loadallData();

    @Query("SELECT * FROM messung")
    public abstract LiveData<Speicher> getMessung();

    @Query("SELECT * FROM messung")
    public abstract LiveData<List<Speicher>> getAllData();

    @Query("SELECT * FROM messung")
    public abstract LiveData<List<Speicher>> getLastData();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long insert (Speicher speicher);

}
