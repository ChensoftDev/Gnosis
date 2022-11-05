package com.example.gnosis;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.gnosis.model.category_model;
import com.example.gnosis.model.todo_list_model;
import com.example.gnosis.reclerview.CategoryAdapter;
import com.example.gnosis.reclerview.TodoAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LIstActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    List<todo_list_model> myTodoList;
    ArrayList myCategory;
    ArrayList<String> myKeys = new ArrayList<>();
    int categoryCounter;
    TodoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);




        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewTodoList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        myTodoList = new ArrayList<>();
        myCategory = new ArrayList();
        categoryCounter = 0;

        loadTodoList();
    }

    private void loadTodoList() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String[] allCategory = getResources().getStringArray(R.array.spinner_category);
        categoryCounter = 0;
        String selectedCategory = getIntent().getExtras().get("categoryName").toString();
        String[] selCategoryList;
        if(selectedCategory.equals("All")) {
            selCategoryList = allCategory;
        } else {
            selCategoryList = new String[]{selectedCategory};
        }
        myKeys.clear();
        myTodoList.clear();
        myCategory.clear();
        for(String category : selCategoryList) {
            db.collection(category)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()) {
                                categoryCounter++;

                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Toast.makeText(LIstActivity.this, "Loading Succeed",
                                            Toast.LENGTH_LONG).show();
                                    String name = document.getData().get("name").toString();
                                    String startDate = document.getData().get("startDate").toString();
                                    String startTime = document.getData().get("startTime").toString();
                                    String endDate = document.getData().get("endDate").toString();
                                    String endTime = document.getData().get("endTime").toString();
                                    String description = document.getData().get("description").toString();
                                    todo_list_model myTodoModel = new todo_list_model(name, startDate, startTime, endDate, endTime, description);
                                    myTodoList.add(myTodoModel);
                                    myCategory.add(category);
                                    myKeys.add(document.getId());
                                }
                                if(categoryCounter == selCategoryList.length) {
                                    setScreen();
                                }
                            } else {
                                Toast.makeText(LIstActivity.this, "Loading Failed",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void setScreen() {
        adapter = new TodoAdapter(myKeys, myTodoList, myCategory);
        adapter.setTodoClickListener(new TodoAdapter.TodoClickListener() {
            @Override
            public void itemClickListener(String itemId, String category) {
                Intent intent = new Intent(LIstActivity.this, CreateActivity.class);
                intent.putExtra("key", itemId);
                intent.putExtra("category", category);
                startActivityForResult(intent,100);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100) {
            recyclerView.swapAdapter(adapter, true);
            loadTodoList();
            setScreen();

        }
    }
}