package com.anantasak.example.chronometer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements Chronometer.OnChronometerTickListener, View.OnClickListener {

    private final String TAG = MainActivity.class.getSimpleName();
    private Chronometer stopWatch;
    private Button b_start,b_reset,b_getTime;
    private Boolean isStart = false;
    private Long base;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stopWatch = findViewById(R.id.stop_watch);
        b_start = findViewById(R.id.btn_start);
        b_reset = findViewById(R.id.btn_reset);
        b_getTime = findViewById(R.id.btn_getTime);

        initListener();
    }

    void initListener(){
        stopWatch.setOnChronometerTickListener(this);
        b_start.setOnClickListener(this);
        b_reset.setOnClickListener(this);
        b_getTime.setOnClickListener(this);
    }

    @Override
    public void onChronometerTick(Chronometer chronometer) {
        this.stopWatch = chronometer;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) { }

    @Override
    public void onClick(View v) {
        if(v == b_start){
            if(b_start.getText().equals("resume")){
                Log.i(TAG, "onClick: RESUME");
                stopWatch.setBase(base + SystemClock.elapsedRealtime());
                stopWatch.start();
                b_start.setText("STOP");
                isStart = true;
            }
            else if(isStart){
                stopWatch.stop();
                isStart = false;
                b_start.setText("START");
            }else {
                stopWatch.setBase(SystemClock.elapsedRealtime());
                stopWatch.start();
                isStart = true;
                b_start.setText("STOP");
            }
        }
        else if(v == b_reset){
            stopWatch.setBase(SystemClock.elapsedRealtime());
            Toast.makeText(this,"Reset",Toast.LENGTH_LONG).show();
        }
        else if(v == b_getTime){
            Toast.makeText(this,stopWatch.getText(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(isStart){
            base = stopWatch.getBase() - SystemClock.elapsedRealtime();
            Log.i(TAG, "onPause: " + base);
            stopWatch.stop();
            isStart = false;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
        if(!isStart && base != null){
            Log.i(TAG, "onResume: " + base);
            b_start.setText("resume");
        }
    }
}