package com.example.eddyyao.arsoapstone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BufferingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load);
        startActivity();
    }

    protected void startActivity() {
        Intent i = new Intent(this, LoadingActivity.class);
        startActivity(i);
    }
}