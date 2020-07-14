package com.example.muf_abgabe.viewmodellDatenbank;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.muf_abgabe.Sensor.Speicher;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


public class SensorViewModel extends BaseViewModel {
    private Handler handler = new Handler(Looper.getMainLooper());
    private SensorLiveData sensorLiveData = new SensorLiveData();
    public SensorViewModel(@NonNull Application application) {super(application);}

    public LiveData<Speicher> setSensor(Speicher speicher){
        sensorLiveData.insertSensor(speicher);
        return sensorLiveData;
    }

    public LiveData<Speicher> getSpeicher(){
        return getDatabase().getSensorDao().getMessung();
    }

    public List<Speicher> getAll(){
        return getDatabase().getSensorDao().loadallData();
    }

    //public LiveData<Speicher> messungInserted(){  return sensorLiveData;   }

    public class SensorLiveData extends LiveData<Speicher> {
        private AtomicBoolean active = new AtomicBoolean();

        public void insertSensor(Speicher speicher) {
            Runnable r = () -> {
                getDatabase().getSensorDao().insert(speicher);
                if(active.get()) {
                    handler.post(() -> {
                        setValue(speicher);
                    });
                }
            };
            Thread t = new Thread(r);
            t.start();
        }

        @Override
        protected void onActive() {
            active.set(true);
        }

        @Override
        protected void onInactive() {
            active.set(false);
        }
    }



}
