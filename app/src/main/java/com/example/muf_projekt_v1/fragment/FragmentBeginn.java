package com.example.muf_projekt_v1.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.muf_projekt_v1.R;

public class FragmentBeginn extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_beginn,container,false);
        return view;
        //USerViewModel...
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        //final EditText messungname = view.findViewById(R.id.mesungNameEdit);

        view.findViewById(R.id.submitMessung).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_beginnfragment_to_firstfragment);
            }
        });

        view.findViewById(R.id.submitDatenbank).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_beginnfragment_to_fragmentcapture);
            }
        });
    }
}
