package com.example.gnosis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;

import com.example.gnosis.reclerview.CategoryAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    // Using ArrayList to store images data
    ArrayList categoryImg = null;
    ArrayList categoryName = new ArrayList<>(Arrays.asList("All", "Assignment", "To Do", "Mindful", "Timetable",
            "C-Language", "HTML 5", "CSS"));
    ArrayList categoryDesc = new ArrayList<>(Arrays.asList("Data Structure", "C++", "C#", "JavaScript", "Java",
            "C-Language", "HTML 5", "CSS"));
    // test comment
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton addbtn = findViewById(R.id.fab_create);

        // Getting reference of recyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewMain);

        // Setting the layout as linear
        // layout for vertical orientation

//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);

        // Sending reference and data to Adapter
        CategoryAdapter adapter = new CategoryAdapter(MainActivity.this, null, categoryName, categoryDesc);

        // Chul MIn : Call list activity when category button clicked
        adapter.setItemClickListener(new CategoryAdapter.ItemClickListener() {
            @Override
            public void itemListener(String categoryName) {
                Intent intent = new Intent(MainActivity.this, LIstActivity.class);
                intent.putExtra("categoryName", categoryName);
                startActivity(intent);
            }
        });

        // Setting Adapter to RecyclerView
        recyclerView.setAdapter(adapter);

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CreateActivity.class));
            }
        });


    }
}