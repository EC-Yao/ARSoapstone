package com.example.eddyyao.arsoapstone;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ARFragment extends Fragment {
    private View rootView;

    // Constructor - Doesn't do anything except instance the object
    public ARFragment(){}

    // Sets base layout element using given parameters and UI elements
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.ar, container, false);
        return rootView;
    }
}