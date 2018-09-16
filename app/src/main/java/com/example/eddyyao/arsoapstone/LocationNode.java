package com.example.eddyyao.arsoapstone;

import android.content.Context;
import android.widget.Toast;

import com.google.ar.sceneform.ux.TransformableNode;
import com.google.ar.sceneform.ux.TransformationSystem;

public class LocationNode extends TransformableNode {

    private String description;

    public LocationNode(TransformationSystem transformationSystem, String text){
        super(transformationSystem);
        description = text;
    }

    public boolean select(Context c){
        Toast.makeText(c, description, Toast.LENGTH_LONG);
        super.select();
        return false;
    }

}
