package com.example.gnosis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.gnosis.reclerview.CategoryAdapter;
import com.example.gnosis.reclerview.TodoAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class LIstActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewTodoList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        //dummy data start
        ArrayList todoName = new ArrayList<>(Arrays.asList("All", "Assignment", "To Do", "Mindful", "Timetable",
                "C-Language", "HTML 5", "CSS"));
        ArrayList todoStart = new ArrayList<>(Arrays.asList("Data Structure", "C++", "C#", "JavaScript", "Java",
                "C-Language", "HTML 5", "CSS"));
        ArrayList todoCate = new ArrayList<>(Arrays.asList("Todo", "Todo", "C#", "Mindful", "Todo",
                "Assignment", "Assignment", "Mindful"));
        //dummy data end

        TodoAdapter adapter = new TodoAdapter(LIstActivity.this, null, todoName , todoCate, todoStart);
        recyclerView.setAdapter(adapter);
    }
}