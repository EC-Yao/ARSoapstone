package com.example.eddyyao.arsoapstone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class LoadingActivity extends AppCompatActivity{

    private TextView tv;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Log.wtf("Loading", "Started");

        setContentView(R.layout.load);

        tv = findViewById(R.id.bufferText);
        iv = findViewById(R.id.bufferImage);

        Animation loadingAnim = AnimationUtils.loadAnimation(this, R.anim.mytransition);
        tv.startAnimation(loadingAnim);
        iv.startAnimation(loadingAnim);

        final Intent i = new Intent(this, LoginActivity.class);
        Thread timer = new Thread(){
            public void run (){
                try{
                    sleep(5000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    // Launches login activity
                    startActivity(i);
                    finish();
                }
            }
        };
        timer.start();
    }
}
