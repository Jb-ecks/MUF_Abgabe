package com.example.muf_projekt_v1.viewmodellDatenbank;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.muf_projekt_v1.datenbank.MUFAplication;
import com.example.muf_projekt_v1.datenbank.MUFDatabase;

public abstract class BaseViewModel extends AndroidViewModel {
    public BaseViewModel(@NonNull Application application){super(application);}
    public MUFDatabase getDatabase(){
        return ((MUFAplication) getApplication()).getDatabase();
    }
}
