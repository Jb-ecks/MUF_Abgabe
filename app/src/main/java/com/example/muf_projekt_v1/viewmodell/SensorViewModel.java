package com.example.muf_projekt_v1.viewmodell;

import android.app.Application;
import android.hardware.Sensor;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.muf_projekt_v1.Sensor.Speicher;

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

    public LiveData<Speicher> messungInserted(){
        return sensorLiveData;
    }

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
