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
import com.example.muf_projekt_v1.Sensor.Speicher;
import com.example.muf_projekt_v1.viewmodellDatenbank.SensorViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class FeedbackFragment extends Fragment {


    //ArrayList<Speicher> datenDatenbank;
    private MainViewModel mainViewModel;
    private SensorViewModel sensorViewModel;
    private List<Speicher> daten;
    private Observer<SensorData> observer;
    private ArrayList<Speicher> datenList;
    int count = 0;


    //getDatabase().getSensorDao().insert(speicher);


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_feedback,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        //langweilig weil das gleiche wie davor
        observer=null;
        datenList = new ArrayList<>();
        count=0;
        final TextView feedback = view.findViewById(R.id.testfeldfeedback);

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

        mainViewModel = new ViewModelProvider(this,
                ViewModelProvider
                        .AndroidViewModelFactory
                        .getInstance(getActivity()
                                .getApplication()))
                .get(MainViewModel.class);

        mainViewModel.sensorDataLive.observe(getViewLifecycleOwner(),(sensorData)-> {
                    feedback.setText(
                            "x:" + sensorData.getX() + " y " + sensorData.getY() + " z " + sensorData.getZ()
                    );

                    x_values.add(new Entry(count, sensorData.getX()));
                    y_values.add(new Entry(count, sensorData.getY()));
                    z_values.add(new Entry(count, sensorData.getZ()));
                    count = count + 1;

                    LineDataSet lineDataSet_x = new LineDataSet(x_values, "x-axis");
                    lineDataSet_x.setColor(Color.GREEN);
                    lineDataSet_x.setDrawCircles(false);
                    lineDataSet_x.setDrawCircleHole(false);
                    lineDataSet_x.setDrawValues(false);


                    LineDataSet lineDataSet_y = new LineDataSet(y_values, "x-axis");
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

                });
// Ab Hier eigener Blödsinn
//        final TextView werte = view.findViewById(R.id.testfeldfeedback);
//        daten=sensorViewModel.getAll();
       //daten[0].getX();
//         werte.setText("x:" + daten.get(0)) ;//[0].getX() );//+ " y " + daten[1].getY() + " z "+daten[1].getZ());
        //datenDatenbank.add();
        // eingabe in die Datenbank

       view.findViewById(R.id.buttonhomefeedback).setOnClickListener(new View.OnClickListener() {@Override
            public void onClick(View v) {
                   mainViewModel.sensorDataLive.removeObserver(observer);
                   observer=null;
                   datenList.clear();
                Navigation.findNavController(view).navigate(R.id.action_fedbackfragment_to_beginnfragment);
                }
        });

    }
}
