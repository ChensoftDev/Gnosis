package com.example.gnosis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.Toast;

import com.example.gnosis.reclerview.CategoryAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    // Using ArrayList to store images data
    ArrayList categoryImg = new ArrayList<>(Arrays.asList(R.drawable.checklist, R.drawable.lightbulb,
            R.drawable.idea, R.drawable.mindful, R.drawable.schedule));
    ArrayList categoryName = new ArrayList<>(Arrays.asList("All"));
    HashMap<String, String> categoryDesc = new HashMap<>();



    int DBLoadCounter, DBLoadChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton addbtn = findViewById(R.id.fab_create);
        Button btnLogout = findViewById(R.id.btn_main_logout);

        // add categoryname in list
        for(String category : getResources().getStringArray(R.array.spinner_category)) {
            categoryName.add(category);
        }


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateActivity.class);
                intent.putExtra("key", "new");
                startActivity(intent);
            }
        });
        CheckList();

    }

    private void setScreen() {
        // Getting reference of recyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewMain);

        // Setting the layout as grid (2 cols)
        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);

        // Sending reference and data to Adapter
        CategoryAdapter adapter = new CategoryAdapter(MainActivity.this, categoryImg, categoryName, categoryDesc);

        // Call list activity when category button clicked
        adapter.setItemClickListener(new CategoryAdapter.ItemClickListener() {
            @Override
            public void itemListener(String categoryName) {

                if(categoryName.equals("Timetable")) {
                    Intent intent = new Intent(MainActivity.this, TimetableActivity.class);
                    startActivity(intent);
                    return;
                }

                Intent intent = new Intent(MainActivity.this, LIstActivity.class);
                intent.putExtra("categoryName", categoryName);
                startActivity(intent);
            }
        });

        // Setting Adapter to RecyclerView
        recyclerView.setAdapter(adapter);
    }

    private void CheckList() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DBLoadCounter = 0;
        DBLoadChecker = 0;
        categoryDesc.put("Timetable","");
        for(String category : getResources().getStringArray(R.array.spinner_category)) {
            if(!category.equals("Timetable")) {
                db.collection(category)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                categoryDesc.put(category, String.valueOf(queryDocumentSnapshots.size()));
                                DBLoadChecker++;
                                DBLoadCounter += queryDocumentSnapshots.size();
                                if(DBLoadChecker == 3) {
                                    setScreen();
                                    categoryDesc.put("All", String.valueOf(DBLoadCounter));
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "Loading error", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }

    }
}