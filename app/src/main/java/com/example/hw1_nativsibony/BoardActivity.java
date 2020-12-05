package com.example.hw1_nativsibony;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class BoardActivity extends AppCompatActivity {
    int[] textViews = {R.id.record_1, R.id.record_2, R.id.record_3, R.id.record_4, R.id.record_5, R.id.record_6, R.id.record_7, R.id.record_8};
    private DatabaseReference dbRef;
    private HashMap<String, Integer> scoreList = new HashMap<String, Integer>();
    private String[] name;
    private Integer score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        dbRef = FirebaseDatabase.getInstance().getReference().child("HighScore");
        dbRef.addValueEventListener(new ValueEventListener() {
            int i = 1;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    name = child.child("name").getValue().toString().split("_");
                    score = Integer.parseInt(child.child("score").getValue().toString());
                    scoreList.put(name[1].toUpperCase(), score);
                    i++;
                }
                List<Map.Entry<String, Integer>> greatest = findGreatest(scoreList, 8);
                for (int i = 0; i < greatest.size(); i++) {
                    TextView tv = (TextView) findViewById(textViews[i]);
                    String key = greatest.get(i).getKey();
                    String value = String.valueOf(greatest.get(i).getValue());
                    tv.setText(key + "\n" + value);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    private static <K, V extends Comparable<? super V>> List<Map.Entry<K, V>> findGreatest(Map<K, V> map, int n) {
        Comparator<? super Map.Entry<K, V>> comparator = new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> e0, Map.Entry<K, V> e1) {
                V v0 = e0.getValue();
                V v1 = e1.getValue();
                return v0.compareTo(v1);
            }
        };
        PriorityQueue<Map.Entry<K, V>> highest = new PriorityQueue<Map.Entry<K, V>>(n, comparator);
        for (Map.Entry<K, V> entry : map.entrySet()) {
            highest.offer(entry);
            while (highest.size() > n) {
                highest.poll();
            }
        }

        List<Map.Entry<K, V>> result = new ArrayList<Map.Entry<K, V>>();
        while (highest.size() > 0) {
            result.add(highest.poll());
        }
        return result;
    }

}