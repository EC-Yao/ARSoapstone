package com.example.eddyyao.arsoapstone;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;

import com.google.ar.sceneform.ux.TransformableNode;
import com.google.ar.sceneform.ux.TransformationSystem;

public class LocationNode extends TransformableNode {

    private String description;

    public LocationNode(TransformationSystem transformationSystem, String text){
        super(transformationSystem);
        description = text;
    }

    @Override
    public boolean select(){
        Log.wtf("Selected", description);
        super.select();
        return false;
    }
}