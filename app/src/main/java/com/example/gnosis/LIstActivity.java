package com.example.gnosis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LIstActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    List<todo_list_model> myTodoList;
    ArrayList myCategory;
    int categoryCounter;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        pd = new ProgressDialog(LIstActivity.this);

        // Set progress dialog style spinner
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        // Set the progress dialog title and message
        pd.setTitle("Please wait");
        pd.setMessage("Loading.........");
        pd.setCancelable(false);

        // Set the progress dialog background color
        //pd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFD4D9D0")));

        pd.setIndeterminate(false);

        // Finally, show the progress dialog
        pd.show();


        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewTodoList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        myTodoList = new ArrayList<>();
        myCategory = new ArrayList();
        categoryCounter = 0;

//        //dummy data start
//        ArrayList todoName = new ArrayList<>(Arrays.asList("All", "Assignment", "To Do", "Mindful", "Timetable",
//                "C-Language", "HTML 5", "CSS"));
//        ArrayList todoStart = new ArrayList<>(Arrays.asList("Data Structure", "C++", "C#", "JavaScript", "Java",
//                "C-Language", "HTML 5", "CSS"));
//        ArrayList todoCate = new ArrayList<>(Arrays.asList("Todo", "Todo", "C#", "Mindful", "Todo",
//                "Assignment", "Assignment", "Mindful"));
        //dummy data end

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
        for(String category : selCategoryList) {
            db.collection(category)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()) {
                                categoryCounter++;


                                    for (QueryDocumentSnapshot document : task.getResult()) {

                                        String name = document.getData().get("name").toString();
                                        String startDate = document.getData().get("startDate").toString();
                                        String startTime = document.getData().get("startTime").toString();
                                        String endDate = document.getData().get("endDate").toString();
                                        String endTime = document.getData().get("endTime").toString();
                                        String description = document.getData().get("description").toString();
                                        todo_list_model myTodoModel = new todo_list_model(name, startDate, startTime, endDate, endTime, description);
                                        myTodoList.add(myTodoModel);
                                        myCategory.add(category);

                                    }










                                if(categoryCounter == selCategoryList.length) {
                                    setScreen();
//                                    if (categoryCounter == 1) {
//                                        new AlertDialog.Builder(LIstActivity.this)
//                                                .setTitle("Message")
//                                                .setMessage("Sorry no information to display")
//                                                .setCancelable(false)
//                                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
//                                                    @Override
//                                                    public void onClick(DialogInterface dialog, int which) {
//                                                        finish();
//                                                    }
//                                                }).show();
//
//                                    }
                                }

                            } else {
                                Toast.makeText(LIstActivity.this, "Loading Failed",
                                        Toast.LENGTH_SHORT).show();
                            }

                            pd.hide();
                        }
                    });
        }
    }

    private void setScreen() {
        TodoAdapter adapter = new TodoAdapter(LIstActivity.this, myTodoList, myCategory);
        recyclerView.setAdapter(adapter);
    }
}