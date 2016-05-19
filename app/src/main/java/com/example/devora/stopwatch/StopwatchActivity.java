package com.example.devora.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;


public class StopwatchActivity extends AppCompatActivity {

    private int mSeconds = 0;
    private boolean mRunning = false;
    private boolean mWasRunning = false;
    private final Handler handler = new Handler();
    private Runnable runnableTimer = new Runnable() {
        @Override
        public void run() {
            tickClock();
            updateGUI();
            handler.postDelayed(this, 1000);
        }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, createTimeString(), Snackbar.LENGTH_LONG).show();
            }
        });

        if (savedInstanceState != null) {
            mSeconds = savedInstanceState.getInt("mSeconds");
            mRunning = savedInstanceState.getBoolean("mRunning");
        }
        updateGUI();
        if(mRunning)
        {
            handler.postDelayed(runnableTimer,1000);
        }
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    public void onClickStart(View view) {
        if (!mRunning) {
            handler.postDelayed(runnableTimer,1000);
            mRunning = true;
        }
    }

    public void onClickStop(View view) {
        handler.removeCallbacks(runnableTimer);
    mRunning = false;
    }

    public void onClickReset(View view) {
        mSeconds = 0;
        updateGUI();
    }

    private String createTimeString()
    {
        int hours = mSeconds / 3600;
        int minutes = (mSeconds % 3600) / 60;
        int secs = mSeconds % 60;

        String time = String.format("%d:%02d:%02d", hours, minutes, secs);
        return time;
    }
    private void updateGUI() {
        String time = createTimeString();
        TextView timeView = (TextView) findViewById(R.id.time_view);
        timeView.setText(time);
    }


    private void tickClock() {
        mSeconds++;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("mSeconds", mSeconds);
          savedInstanceState.putBoolean("mRunning", mRunning);
    }
}
