package com.example.muf_abgabe.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.room.Database;

import com.example.muf_abgabe.R;
import com.example.muf_abgabe.datenbank.MUFAplication;
import com.example.muf_abgabe.datenbank.MUFDatabase;

public class FragmentBeginn extends Fragment {
    private MUFDatabase database;
    private String messungName = "messung";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_beginn,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        database = ((MUFAplication) getActivity().getApplication()).getDatabase();


        view.findViewById(R.id.submitMessung).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_beginnfragment_to_firstfragment);

            }
        });

        view.findViewById(R.id.submitFragmentCapture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_beginnfragment_to_fragmentcapture);
            }
        });

    }
}
