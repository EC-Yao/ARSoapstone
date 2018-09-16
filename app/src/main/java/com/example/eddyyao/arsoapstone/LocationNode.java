package com.example.eddyyao.arsoapstone;

import android.app.AlertDialog;
import android.content.DialogInterface;

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
        super.select();
        return false;
    }
}