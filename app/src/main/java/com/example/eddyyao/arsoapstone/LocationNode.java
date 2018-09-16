package com.example.eddyyao.arsoapstone;

import android.content.Context;

import com.google.ar.sceneform.ux.TransformableNode;
import com.google.ar.sceneform.ux.TransformationSystem;

public class LocationNode extends TransformableNode {

    private String description;

    public LocationNode(TransformationSystem transformationSystem, String text){
        super(transformationSystem);
        description = text;
    }

    public boolean select(){
        super.select();

        return false;
    }

}
