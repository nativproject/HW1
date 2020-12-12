package com.example.hw1_nativsibony;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.List;

public class WinnerActivity extends AppCompatActivity implements View.OnClickListener {
    Button winner_BTN_new_game;
    TextView winner_LBL, isWinner_LBL;
    boolean isPlay = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner);
        findViews();
        initViews();
    }

    private void initViews() {
        winner_BTN_new_game.setOnClickListener(this);
        String winner = getIntent().getExtras().getString("winner");
        if (winner.equals("It's A Draw!"))
            isWinner_LBL.setText("");
        winner_LBL.setText(winner);
    }

    private void findViews() {
        winner_LBL = findViewById(R.id.winner_LBL);
        isWinner_LBL = findViewById(R.id.isWinner_LBL);
        winner_BTN_new_game = (Button) findViewById(R.id.winner_BTN_new_game);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isPlay) {
            AudioPlay.playAudio(this, R.raw.bg_music);
            isPlay = true;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.winner_BTN_new_game)
            openMainActivity();
    }


    public void openMainActivity() {
        Intent intent = new Intent(this, EntryActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        this.finish();
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
    protected void onPause() {
        if (isApplicationSentToBackground(this)) {
            // Do what you want to do on detecting Home Key being Pressed
            AudioPlay.stopAudio();
            isPlay = false;
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.finish();
    }
}