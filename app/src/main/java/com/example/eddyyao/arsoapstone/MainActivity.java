package com.example.eddyyao.arsoapstone;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.GuardedBy;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.Camera;
import com.google.ar.core.Config;
import com.google.ar.core.Frame;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.Session;
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;

public class MainActivity extends AppCompatActivity {

    protected ArFragment arFragment;
    protected ModelRenderable andyRenderable;
    private String m_Text;
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private Session session;
    private Anchor anchor;
    private Frame frame;

    private final Object singleTapAnchorLock = new Object();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        MainActivity.context = getApplicationContext();
    }

    @Override
    protected void onResume(){
        super.onResume();

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ar);

        if (session == null){
            try{
                session = new Session(this);
                Config config = new Config(session);
                config.setCloudAnchorMode(Config.CloudAnchorMode.ENABLED);
                session.configure(config);
            } catch (Exception e){
                Log.wtf("Cloud", "This really ain't it chief");
            }
        }


        try{
            session.resume();
        } catch (Exception e){
        }

        ModelRenderable.builder()
                .setSource(this, R.raw.andy)
                .build()
                .thenAccept(renderable -> andyRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });

        arFragment.setOnTapArPlaneListener(
                (HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {
                    if (andyRenderable == null) {
                        return;
                    }

                    Log.wtf("Main", "Loading Anchor");

                    // Create the Anchor.
                    try {
                        frame = session.update();
                    } catch (Exception e){
                        Log.wtf("Cloud", "RIP Frames");
                    }

                    Camera camera = frame.getCamera();
                    TrackingState cameraTrackingState = camera.getTrackingState();
                    handleTapOnDraw(cameraTrackingState, hitResult);

                    appAnchorState = AppAnchorState.HOSTING;
                    checkUpdatedAnchor();

                    AnchorNode anchorNode = new AnchorNode(anchor);
                    anchorNode.setParent(arFragment.getArSceneView().getScene());

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Enter a description:");

                    final EditText input = new EditText(this);
                    input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    builder.setView(input);

                    builder.setPositiveButton("OK", (dialog, which) -> {
                        m_Text = input.getText().toString();
                        LocationNode andy = new LocationNode(arFragment.getTransformationSystem(), m_Text);
                        andy.setParent(anchorNode);
                        andy.setRenderable(andyRenderable);
                        andy.select();
                    });
                    builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

                    builder.show();
                });
    }

    protected boolean getCameraPerms(){
        return ContextCompat.checkSelfPermission(this , Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] results) {
        if (!getCameraPerms()) {
            Toast.makeText(this, "Camera permission is needed to run this application", Toast.LENGTH_LONG)
                    .show();
            finish();
        }
    }

    private enum AppAnchorState {
        NONE,
        HOSTING,
        HOSTED
    }

    @GuardedBy("singleTapAnchorLock")
    private AppAnchorState appAnchorState = AppAnchorState.NONE;

    @GuardedBy("singleTapAnchorLock")
    private void setNewAnchor(@Nullable Anchor newAnchor) {
        if (anchor != null) {
            anchor.detach();
        }
        anchor = newAnchor;
        appAnchorState = AppAnchorState.NONE;
    }

    public static Context getContext(){
        return context;
    }

    private void checkUpdatedAnchor() {
        try{
            Log.wtf("Cloud Anchor", "Anchor hosted successfully! Cloud ID: " + anchor.getCloudAnchorId());
                appAnchorState = AppAnchorState.HOSTED;
        } catch (Exception e) {
            Log.wtf("Cloud Anchor", "This ain't it #2");
        }
    }

    private void handleTapOnDraw(TrackingState currentTrackingState, HitResult hitResult) {
        synchronized (singleTapAnchorLock) {
            if (anchor == null
                    && currentTrackingState == TrackingState.TRACKING) {
                        Anchor newAnchor = session.hostCloudAnchor(hitResult.createAnchor());
                        setNewAnchor(newAnchor);
            }
        }
    }
}