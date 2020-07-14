package com.example.muf_abgabe.Sensor;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//wussten nicht wie wir eine Liste selber erstellen und mit der klasse konnten wir das ansonsten überflüssig

// Ansatz um einzelne Messungen unterschiedlich zu speichern:https://stackoverflow.com/questions/56326640/associating-tables-using-room-database-in-android-studio
//so startet zeile immer bei null und überschreibt die alte Messung (wenn ichs richtig verstanden habe
// war aber froh dass das ertsmal gelaufen ist

@Entity(tableName="messung")
public class Speicher {
    @PrimaryKey
    @NonNull
    private int idZeile;
    private String messungname;
    //private int zeile;
    private float x;
    private float y;
    private float z;
    private long time;

    public Speicher( int idZeile, float x, float y, float z, long time,String messungname) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.time = time;
        this.messungname=messungname;
        this.idZeile=idZeile;
    }

    public String getMessungname() {
        return messungname;
    }

    public void setMessungname(String messungname) {
        this.messungname = messungname;
    }

    //public int getIdMessung() {       return idMessung;   }

    //public void setIdMessung(int idMessung) {       this.idMessung = idMessung;   }

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

    public int getIdZeile() {
        return idZeile;
    }

    public void setIdZeile(int idZeile) {
        this.idZeile = idZeile;
    }

}
