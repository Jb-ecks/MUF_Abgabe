package com.example.muf_projekt_v1.Sensor;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

//wussten nicht wie wir eine Liste selber erstellen und mit der klasse konnten wir das ansonsten überflüssig

// Ansatz um einzelne Messungen unterschiedlich zu speichern:https://stackoverflow.com/questions/56326640/associating-tables-using-room-database-in-android-studio
//so startet zeile immer bei null und überschreibt die alte Messung (wenn ichs richtig verstanden habe
// war aber froh dass das ertsmal gelaufen ist

@Entity(tableName="messung")
public class Speicher {
    @PrimaryKey
    private int zeile;

    private String messungname;
    private float x;
    private float y;
    private float z;
    private long time;

    public Speicher(String messungname, int zeile ,float x, float y, float z, long time) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.time = time;
        this.zeile=zeile;
        this.messungname=messungname;
    }

    public String getMessungname() { return messungname;}

    public void setMessungname(String messungname) {this.messungname = messungname;}

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

    public int getZeile() {
        return zeile;
    }

    public void setZeile(int zeile) {
        this.zeile = zeile;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
