package com.example.muf_abgabe.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.muf_abgabe.R;
import com.example.muf_abgabe.Sensor.MainViewModel;
import com.example.muf_abgabe.Sensor.SensorData;
import com.example.muf_abgabe.Sensor.Speicher;
import com.example.muf_abgabe.datenbank.MUFDatabase;
import com.example.muf_abgabe.muzik.MediaService;
import com.example.muf_abgabe.viewmodellDatenbank.SensorViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class StartFragment extends Fragment {
    private SensorViewModel sensorViewModel;
    private MUFDatabase database;
    private String messungName="defaultName";
    private String tempMessungname;
    private MediaService.MediaBinder mediaBinder;
    private MediaServiceConnection mediaServiceConnection = null;

    private MainViewModel mainViewModel;
    private Observer<SensorData> observer;
    private ArrayList<Speicher> datenList;
    private int count =0;
    private float Xmax=0;
    private float Ymax=0;
    private float Zmax=0;

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
        final EditText etMessungName = view.findViewById(R.id.mesungNameEdit);
        final TextView werte = view.findViewById(R.id.messungStatus);
        // name abrufen um auf diese Messung zu gehen final EditText tempMessungname = view.findViewById(R.id.editText);
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


        view.findViewById(R.id.startbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hier findet alles statt wenn der Startbutton gedrückt ist.
                // der observer
                Toast.makeText(getContext(),"Messung wird gestartet und wird in Datenbank gespeichert." ,Toast.LENGTH_SHORT).show();
                tempMessungname = etMessungName.getText().toString();
                if (observer==null){

                    if (messungName!=null){
                        if (messungName!=tempMessungname){
                            messungName="default_messung";
                            Log.d(TAG,"messungnameGeaendert: Name: "+messungName);}
                    }

                    observer = (sensorData) ->{
                        //Visuelles feedback
                        if(Xmax<sensorData.getX()){Xmax=sensorData.getX();}
                        if(Ymax<sensorData.getY()){Ymax=sensorData.getY();}
                        if(Zmax<sensorData.getZ()){Zmax=sensorData.getZ();}

                        werte.setText("x:" + sensorData.getX() + " y " + sensorData.getY() + " z "+sensorData.getZ());
                        Speicher tempsensor = new Speicher(count, sensorData.getX(),sensorData.getY() ,sensorData.getZ(), System.currentTimeMillis(),messungName);
                        datenList.add(tempsensor);

                        // eingabe in die Datenbank
                        sensorViewModel.setSensor(tempsensor);

                        // TODO: Ja mit einem ist es nett. ABER entweder ne scrollbar und sensor auswählen oder zusammen plotten können
                        // Live Daten in Anzeigen
                        x_werte.add(new Entry(count,sensorData.getX()));
                        y_werte.add(new Entry(count, sensorData.getY()));
                        z_werte.add(new Entry(count, sensorData.getZ()));

                        LineDataSet xDataSet = new LineDataSet(x_werte,"x_Data");
                        xDataSet.setColor(Color.GREEN);
                        xDataSet.setDrawCircles(false);
                        xDataSet.setDrawCircleHole(false);
                        xDataSet.setDrawValues(false);
                        LineDataSet yDataSet = new LineDataSet(y_werte,"y_Data");
                        yDataSet.setColor(Color.RED);
                        yDataSet.setDrawCircles(false);
                        yDataSet.setDrawCircleHole(false);
                        yDataSet.setDrawValues(false);
                        LineDataSet zDataSet = new LineDataSet(z_werte,"z_Data");
                        zDataSet.setColor(Color.BLUE);
                        zDataSet.setDrawCircles(false);
                        zDataSet.setDrawCircleHole(false);
                        zDataSet.setDrawValues(false);

                        LineData data =new LineData(xDataSet,yDataSet,zDataSet);
                        lineChart_all.setData(data);
                        lineChart_all.invalidate();
                        lineChart_all.setBackgroundColor(Color.LTGRAY);

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
                if (mediaBinder == null) return;
                mediaBinder.play(R.raw.start);
                Navigation.findNavController(view).navigate(R.id.action_startfragment_to_fedbackfragment);
                mainViewModel.sensorDataLive.removeObserver(observer);
                observer=null;
                count=0;
                datenList.clear();
            }
        });

        view.findViewById(R.id.submitClearDatabase).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (messungName != null) {
                    if (database.getSensorDao().getAllDataMessungName(messungName) != null) { //funktioniert so doch nicht
                        database.getSensorDao().deleteAll(messungName);
                    }
                }
            }
        });

        view.findViewById(R.id.submitBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_firstfragment_to_beginnfragment);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mediaServiceConnection == null) {
            getActivity().bindService(new Intent(getContext(), MediaService.class),
                    mediaServiceConnection = new MediaServiceConnection(),
                    Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mediaServiceConnection != null) {
            getActivity().unbindService(mediaServiceConnection);
            mediaServiceConnection = null;
        }
    }

    private final class MediaServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mediaBinder = (MediaService.MediaBinder) iBinder;

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mediaBinder = null;

        }
    }
}
