package com.example.muf_projekt_v1.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.muf_projekt_v1.R;
import com.example.muf_projekt_v1.Sensor.MainViewModel;
import com.example.muf_projekt_v1.Sensor.SensorData;

public class StartFragment extends Fragment {

    private MainViewModel mainViewModel;
    private Observer<SensorData> observer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start,container,false);
        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //final NavController controller = Navigation.findNavController(view);

        final TextView vendor = view.findViewById(R.id.vendor);
        final TextView name = view.findViewById(R.id.name);
        final TextView werte = view.findViewById(R.id.xyz);
        final TextView version = view.findViewById(R.id.version);
        final Button start_button=view.findViewById(R.id.startbutton);
        observer = null;
        mainViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(
                        getActivity().getApplication()))
                .get(MainViewModel.class);

        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hier findet alles statt wenn der Startbutton gedrückt ist.
                // der observer
                if (observer==null){
                    observer = (sensorData) ->{
                        werte.setText("x:" + sensorData.getX() + " y " + sensorData.getY() + " z "+sensorData.getZ());
                    };

                    mainViewModel.sensorData.observe(getViewLifecycleOwner(),observer); // musste sensorDataLiveData public machen wieso keine Ahnung.
                }

            }
        });
    }
}
