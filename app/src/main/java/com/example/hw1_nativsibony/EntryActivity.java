package com.example.hw1_nativsibony;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.Button;

import java.util.List;

public class EntryActivity extends AppCompatActivity {
    private Button entry_BTN_start, entry_BTN_records, entry_BTN_auto;
    private final String MODE = "MODE";
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private int length;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        mediaPlayer = MediaPlayer.create(this, R.raw.bg_music);
        mediaPlayer.start();

        mediaPlayer.setVolume(25, 25);
        findViews();
        entry_BTN_start.setOnClickListener(view -> normalPlay());

        entry_BTN_records.setOnClickListener(view -> showRecords());

        entry_BTN_auto.setOnClickListener(view -> autoPlay());
    }

    private void findViews() {
        entry_BTN_start = (Button) findViewById(R.id.entry_BTN_start);
        entry_BTN_records = (Button) findViewById(R.id.entry_BTN_records);
        entry_BTN_auto = (Button) findViewById(R.id.entry_BTN_auto);
    }

    public void normalPlay() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MODE, "0");
        startActivity(intent);
    }

    public void autoPlay() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MODE, "1");
        startActivity(intent);
    }

    public void showRecords() {
        Intent intent = new Intent(this, BoardActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        if (isApplicationSentToBackground(this)) {
            // Do what you want to do on detecting Home Key being Pressed
            mediaPlayer.stop();
        }
        super.onPause();
    }

    public boolean isApplicationSentToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start();
    }
}

