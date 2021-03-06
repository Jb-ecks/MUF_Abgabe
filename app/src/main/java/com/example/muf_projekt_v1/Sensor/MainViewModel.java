package com.example.muf_projekt_v1.Sensor;

import android.app.Application;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

// übernommen von Vorlesung und Variablen verändert #neverchangearunningsystem
public class MainViewModel extends AndroidViewModel {

    public LiveData<SensorData> sensorDataLive;// weil ich umstrukturiert habe, musste ich das machen damit es mir in startfragment angezeigt wird. Ich weiss es ist falsch da eigentlich nur gelesen werden soll

    public MainViewModel(@NonNull Application application){
        super(application);
        sensorDataLive = new SensorDataLiveData(application.getApplicationContext());
    }

    private final static class SensorDataLiveData extends LiveData<SensorData>{
        private final SensorData sensorDaten =new SensorData();
        private SensorManager sm;
        private Sensor accelerometer;
        private Sensor gravitySensor;
        private float[] gravity;

        private SensorEventListener listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                switch (event.sensor.getType()) {
                    case Sensor.TYPE_ACCELEROMETER:
                        float[] values = removeGravity(gravity, event.values);
                        sensorDaten.setALL(values[0], values[1], values[2]);
                        sensorDaten.setSensor(event.sensor);
                        setValue(sensorDaten);
                        break;
                    case Sensor.TYPE_GRAVITY:
                        gravity = event.values;
                        break;
                    default:
                        break;//Ignore this case!
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        SensorDataLiveData(Context context) {
            sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            if (sm != null) {
                gravitySensor = sm.getDefaultSensor(Sensor.TYPE_GRAVITY);
                accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            } else {
                // should never happen
                throw new RuntimeException(("Patrick! Hilfe! Hier ist ein Fehler!"));
            }
        }

        @Override
        protected void onActive() {
            super.onActive();
            sm.registerListener(listener, gravitySensor, SensorManager.SENSOR_DELAY_NORMAL);
            sm.registerListener(listener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }

        @Override
        protected void onInactive() {
            super.onInactive();
            sm.unregisterListener(listener);
        }

        // kopiert aus: (danke Patrick fürs suchen) https://developer.android.com/guide/topics/sensors/sensors_motion#java
        private float[] removeGravity(float[] gravity, float[] values) {
            if (gravity == null) {
                return values;
            }
            final float alpha = 0.8f;
            float g[] = new float[3];
            g[0] = alpha * gravity[0] + (1 - alpha) * values[0];
            g[1] = alpha * gravity[1] + (1 - alpha) * values[1];
            g[2] = alpha * gravity[2] + (1 - alpha) * values[2];

            return new float[]{
                    values[0] - g[0],
                    values[1] - g[1],
                    values[2] - g[2]
            };

        }


    }
}
