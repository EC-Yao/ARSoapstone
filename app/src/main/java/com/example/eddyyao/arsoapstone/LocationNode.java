package com.example.eddyyao.arsoapstone;

import android.app.AlertDialog;

import com.google.ar.sceneform.ux.TransformableNode;
import com.google.ar.sceneform.ux.TransformationSystem;

public class LocationNode extends TransformableNode {

    private String description;

    LocationNode(TransformationSystem transformationSystem, String text){
        super(transformationSystem);
        description = text;
    }

    public boolean select(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.getContext());
        builder.setMessage(description)
                .setNegativeButton("Close", (dialog, id) -> {
                });
        builder.create();
        //builder.show();
        super.select();
        return false;
    }
}