package com.example.muf_projekt_v1.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.muf_projekt_v1.Sensor.Speicher;

import java.util.List;

@Dao
public abstract class SensorDao {

    //@Query("SELECT * FROM messung WHERE messungname=:messungname")
    //public abstract LiveData<Speicher> getUserByEmail(String messungname);

    @Query("SELECT * FROM messung")
    public abstract List<Speicher> loadallData();

    @Query("SELECT * FROM messung")
    public abstract LiveData<Speicher> getMessung();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long insert (Speicher speicher);

}
