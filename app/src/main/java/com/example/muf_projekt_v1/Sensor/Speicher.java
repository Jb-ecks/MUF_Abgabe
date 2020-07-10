package com.example.muf_projekt_v1.Sensor;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

//wussten nicht wie wir eine Liste selber erstellen und mit der klasse konnten wir das ansonsten überflüssig
@Entity(tableName="messung")
public class Speicher {
    @PrimaryKey
    private String messungname;

    private int zeile;
    private float x;
    private float y;
    private float z;
    private long time;

    public Speicher(int zeile ,float x, float y, float z, long time) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.time = time;
        this.zeile=zeile;
    }

    public String getMessungname() {
        return messungname;
    }

    public void setMessungname(String messungname) {
        this.messungname = messungname;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public long getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
