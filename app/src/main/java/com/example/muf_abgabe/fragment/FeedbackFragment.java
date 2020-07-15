package com.example.muf_abgabe.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.muf_abgabe.R;
import com.example.muf_abgabe.Sensor.Speicher;
import com.example.muf_abgabe.datenbank.MUFAplication;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class FeedbackFragment extends Fragment {
    private  String messungName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_feedback, container, false);
        //Bundle args = getArguments(); // wollte Messungname übergeben hat aber nicht funktioniert
        // FragmentArgs feedbackFragmentArgs = null;
        /*
        if(args != null)
            feedbackFragmentArgs = FeedbackFragmentArgs.fromBundle(args);
        if(feedbackFragmentArgs != null){
            messungName = feedbackFragmentArgs.getDisplayString();
        };*/
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        // Daten aus der Datenbank
        LineChart lineChart_x = view.findViewById(R.id.liveChart_x);
        Description desc_x = new Description();
        desc_x.setText("");
        lineChart_x.setDescription(desc_x);
        lineChart_x.setDrawGridBackground(false);

        LineChart lineChart_y = view.findViewById(R.id.liveChart_y);
        Description desc_y = new Description();
        desc_y.setText("");
        lineChart_y.setDescription(desc_y);
        lineChart_y.setDrawGridBackground(false);

        LineChart lineChart_z = view.findViewById(R.id.liveChart_z);
        Description desc_z = new Description();
        desc_z.setText("");
        lineChart_z.setDescription(desc_z);
        lineChart_z.setDrawGridBackground(false);

        ArrayList<Entry> x_values = new ArrayList<Entry>();
        ArrayList<Entry> y_values = new ArrayList<Entry>();
        ArrayList<Entry> z_values = new ArrayList<Entry>();

        ((MUFAplication) getActivity()
                .getApplication())
                .getDatabase()
                .getSensorDao()
                .getAllData()
                .observe(getViewLifecycleOwner(),
                        speichers -> {


                            for (Speicher s : speichers) {
                                // TODO: render speicher
                                Log.d(TAG, "DB. " + s.getX());
                                x_values.add(new Entry(s.getIdZeile(), s.getX()));
                                y_values.add(new Entry(s.getIdZeile(), s.getY()));
                                z_values.add(new Entry(s.getIdZeile(), s.getZ()));

                                LineDataSet lineDataSet_x = new LineDataSet(x_values, "x-axis");
                                lineDataSet_x.setColor(Color.GREEN);
                                lineDataSet_x.setDrawCircles(false);
                                lineDataSet_x.setDrawCircleHole(false);
                                lineDataSet_x.setDrawValues(false);

                                LineDataSet lineDataSet_y = new LineDataSet(y_values, "y-axis");
                                lineDataSet_y.setColor(Color.GREEN);
                                lineDataSet_y.setDrawCircles(false);
                                lineDataSet_y.setDrawCircleHole(false);
                                lineDataSet_y.setDrawValues(false);

                                LineDataSet lineDataSet_z = new LineDataSet(z_values, "z-axis");
                                lineDataSet_z.setColor(Color.GREEN);
                                lineDataSet_z.setDrawCircles(false);
                                lineDataSet_z.setDrawCircleHole(false);
                                lineDataSet_z.setDrawValues(false);

                                LineData data_x = new LineData(lineDataSet_x);
                                LineData data_y = new LineData(lineDataSet_y);
                                LineData data_z = new LineData(lineDataSet_z);

                                lineChart_x.setData(data_x);
                                lineChart_x.invalidate();

                                lineChart_y.setData(data_y);
                                lineChart_y.invalidate();

                                lineChart_z.setData(data_z);
                                lineChart_z.invalidate();

                            }
                        });

        view.findViewById(R.id.buttonhomefeedback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_fedbackfragment_to_beginnfragment);
            }
        });

    }

}
