package com.example.muf_projekt_v1.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.muf_projekt_v1.R;
import com.example.muf_projekt_v1.Sensor.MainViewModel;
import com.example.muf_projekt_v1.Sensor.SensorData;
import com.example.muf_projekt_v1.viewmodellDatenbank.SensorViewModel;


public class CaptureFragment extends Fragment {

    private MainViewModel mainViewModel;
    private double resultierende;
    private SensorViewModel sensorViewModel;
    private Observer<SensorData> observer;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_capture,container,false);
        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        //langweilig weil das gleiche wie davor
        //observer=null;
        TextView vorfuss = view.findViewById(R.id.vorfussdruck);
        TextView mittelfuss = view.findViewById(R.id.mittedruck);
        TextView ferse = view.findViewById(R.id.fersedruck);


        mainViewModel = new ViewModelProvider(this,
                ViewModelProvider
                        .AndroidViewModelFactory
                        .getInstance(getActivity()
                                .getApplication()))
                .get(MainViewModel.class);

        // Ist zwar das falsche Layout für dei Werte war am Anfang aber unsere Grundgedanke

        mainViewModel.sensorDataLive.observe(getViewLifecycleOwner(), (sensorData) -> {
            vorfuss.setText("Vorfußdruck: " + sensorData.getX());
            if (sensorData.getX()<1){vorfuss.setTextColor(Color.parseColor("#FFFFFF"));}
            else if (sensorData.getX()<5){vorfuss.setTextColor(Color.parseColor("#FFFFFF"));}
            else{vorfuss.setTextColor(Color.parseColor("#FFFFFF"));}

            mittelfuss.setText("Mittelfußdruck: " + sensorData.getY());
            if (sensorData.getX()<1){mittelfuss.setTextColor(Color.parseColor("#FFFFFF"));}
            else if (sensorData.getX()<5){mittelfuss.setTextColor(Color.parseColor("#FFFFFF"));}
            else{mittelfuss.setTextColor(Color.parseColor("#FFFFFF"));}

            ferse.setText("Fersendruck: " + sensorData.getZ());
            if (sensorData.getX()<1){ferse.setTextColor(Color.parseColor("#FFFFFF"));}
            else if (sensorData.getX()<5){ferse.setTextColor(Color.parseColor("#FFFFFF"));}
            else{ferse.setTextColor(Color.parseColor("#FFFFFF"));}

            resultierende= Math.sqrt(Math.pow(sensorData.getX(),2)+Math.pow(sensorData.getY(),2)+Math.pow(sensorData.getZ(),2));
            if (resultierende<2.5) {//Ibinder
            }
        });



        view.findViewById(R.id.feedbackfragmentbuttoncapture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainViewModel.sensorDataLive.removeObserver(observer);
                Navigation.findNavController(view).navigate(R.id.action_fragmentcapture_to_fedbackfragment);
           }
        });

    }
}
