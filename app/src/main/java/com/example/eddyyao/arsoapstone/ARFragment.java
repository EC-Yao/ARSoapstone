package com.example.eddyyao.arsoapstone;

import android.Manifest;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.ar.sceneform.ArSceneView;

public class ARFragment extends Fragment {

    private View rootView;
    private ArSceneView scene;

    public ARFragment(){}

    // Sets base layout element using given parameters and UI elements
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.ar, container, false);
        Log.wtf("ARFrag", "Loaded");
        return rootView;
    }

    @Override
    public void onResume(){
        super.onResume();
    }
}