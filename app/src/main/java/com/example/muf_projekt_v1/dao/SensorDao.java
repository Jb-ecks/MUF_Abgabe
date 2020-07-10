package com.example.muf_projekt_v1.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.muf_projekt_v1.Sensor.Speicher;

@Dao
public abstract class SensorDao {

    @Query("SELECT * FROM messung")
    public abstract LiveData<Speicher> getMessung();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long insert (Speicher speicher);

}