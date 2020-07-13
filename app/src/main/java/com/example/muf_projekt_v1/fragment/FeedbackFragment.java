package com.example.muf_projekt_v1.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.muf_projekt_v1.R;
import com.example.muf_projekt_v1.Sensor.Speicher;
import com.example.muf_projekt_v1.viewmodellDatenbank.BaseViewModel;
import com.example.muf_projekt_v1.viewmodellDatenbank.SensorViewModel;

import java.util.ArrayList;

public class FeedbackFragment extends Fragment {


    //ArrayList<Speicher> datenDatenbank;
    private SensorViewModel sensorViewModel;
    private Speicher[] daten;
    //getDatabase().getSensorDao().insert(speicher);


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_feedback,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        final TextView werte = view.findViewById(R.id.tesxtfeldfeedback);
        //daten=sensorViewModel.getAll();
        //daten[0].getX();
        //werte.setText("x:" + daten[0].getX() );//+ " y " + daten[1].getY() + " z "+daten[1].getZ());
        //datenDatenbank.add();
        // eingabe in die Datenbank


        view.findViewById(R.id.buttonhomefeedback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_fedbackfragment_to_beginnfragment);
            }
        });

    }
}
