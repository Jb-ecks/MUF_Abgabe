package com.example.muf_projekt_v1.fragment;

import android.os.Bundle;
import android.util.Log;
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
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class StartFragment extends Fragment {
    private SensorViewModel sensorViewModel;
    private String messungname="messung1";

    private MainViewModel mainViewModel;
    private Observer<SensorData> observer;
    private ArrayList<Speicher> datenList;
    private int count =0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensorViewModel =new ViewModelProvider(
                getActivity(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())
        ).get(SensorViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start,container,false);
        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        //final TextView vendor = view.findViewById(R.id.vendor);
        //final TextView name = view.findViewById(R.id.name);
        final TextView werte = view.findViewById(R.id.xyz);
        final TextView version = view.findViewById(R.id.version);


        observer = null;
        datenList = new ArrayList<>();
        mainViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(
                        getActivity().getApplication()))
                .get(MainViewModel.class);

        //Graph für Livedata
        LineChart lineChart_all = view.findViewById(R.id.liveChart_all);
        Description desc_x = new Description();
        desc_x.setText("");
        lineChart_all.setDescription(desc_x);
        lineChart_all.setDrawGridBackground(false);

        ArrayList<Entry> x_werte = new ArrayList<Entry>();
        ArrayList<Entry> y_werte = new ArrayList<Entry>();
        ArrayList<Entry> z_werte = new ArrayList<Entry>();
        ArrayList<ILineDataSet> alle_werte = new ArrayList<>();


        view.findViewById(R.id.startbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hier findet alles statt wenn der Startbutton gedrückt ist.
                // der observer
                if (observer==null){
                    observer = (sensorData) ->{
                        werte.setText("x:" + sensorData.getX() + " y " + sensorData.getY() + " z "+sensorData.getZ());
                        Speicher tempsensor = new Speicher(count,sensorData.getX(),sensorData.getY() ,sensorData.getZ(), System.currentTimeMillis());
                        datenList.add(tempsensor);

                        // eingabe in die Datenbank
                        sensorViewModel.setSensor(tempsensor);

                        // TODO: Ja mit einem ist es nett entweder ne scrollbar und sensor auswählen oder zusammen plotten können
                        // Live Daten in Anzeigen
                        //x_werte.add(new Entry(count,sensorData.getX()));
                        y_werte.add(new Entry(count, sensorData.getY()));
                        //z_werte.add(new Entry(count, sensorData.getZ()));
                        //all_werte.add(new Entry(count, sensorData.getX(), sensorData.getY(), sensorData.getZ()))

                        LineDataSet xDataSet = new LineDataSet(x_werte,"x_Data");
                        LineDataSet yDataSet = new LineDataSet(y_werte,"y_Data");
                        //LineDataSet zDataSet = new LineDataSet(z_werte,"z_Data");


                        //alle_werte.add(xDataSet);
                        //alle_werte.add(yDataSet);
                        //alle_werte.add(zDataSet);

                        //LineData data =new LineData(alle_werte);
                        //lineChart_all.setData(data);
                        //lineChart_all.invalidate();

                        LineData data =new LineData(yDataSet);
                        lineChart_all.setData(data);
                        lineChart_all.invalidate();

                        //Überprüfen ob Liste auch funktioniert
                        Log.d(TAG,"on Create: Daten: "+datenList.get(count).getX());
                        count=count+1;
                    };

                    mainViewModel.sensorDataLive.observe(getViewLifecycleOwner(),observer); // musste sensorDataLiveData public machen wieso keine Ahnung.
                }
            }
        });

        view.findViewById(R.id.stopbutton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mainViewModel.sensorDataLive.removeObserver(observer);
                observer=null;
                werte.setText("Messung gestoppt");
                count=0;
                datenList.clear();
            }
        });

        view.findViewById(R.id.fedbackfragmentbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_startfragment_to_fedbackfragment);
            }
        });
    }
}
