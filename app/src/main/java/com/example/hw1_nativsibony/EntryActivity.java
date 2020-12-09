package com.example.hw1_nativsibony;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class EntryActivity extends AppCompatActivity {
    private Button entry_BTN_start, entry_BTN_records, entry_BTN_auto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        findViews();

        entry_BTN_start.setOnClickListener(view -> startGame());

        entry_BTN_records.setOnClickListener(view -> showRecords());

        entry_BTN_auto.setOnClickListener(view -> autoPlay());
    }

    private void findViews() {
        entry_BTN_start = (Button) findViewById(R.id.entry_BTN_start);
        entry_BTN_records = (Button) findViewById(R.id.entry_BTN_records);
        entry_BTN_auto = (Button) findViewById(R.id.entry_BTN_auto);
    }

    public void startGame() {
        this.finish();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("MODE", "0");
        startActivity(intent);
    }

    public void showRecords() {
        Intent intent = new Intent(this, BoardActivity.class);
        startActivity(intent);
    }

    public void autoPlay() {
        this.finish();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("MODE", "1");
        startActivity(intent);
    }
}

