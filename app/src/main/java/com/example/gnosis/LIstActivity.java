package com.example.gnosis;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.gnosis.model.todo_list_model;
import com.example.gnosis.reclerview.TodoAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class LIstActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    List<todo_list_model> myTodoList;
    ArrayList<String> myCategory, myKey;
    int categoryCounter;
    TodoAdapter adapter;

    private final FirebaseAuth auth = FirebaseAuth.getInstance();
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
        myCategory = new ArrayList<>();
        myKey = new ArrayList<>();
        categoryCounter = 0;

        loadTodoList();


    }

    private void loadTodoList() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String[] allCategory = getResources().getStringArray(R.array.spinner_category);
        categoryCounter = 0;
        myCategory.clear();
        myTodoList.clear();
        myKey.clear();
        String selectedCategory = getIntent().getExtras().get("categoryName").toString();
        String[] selCategoryList;
        if(selectedCategory.equals("All")) {
            selCategoryList = allCategory;
        } else {
            selCategoryList = new String[]{selectedCategory};
        }
        for(String category : selCategoryList) {
            db.collection(auth.getCurrentUser().getUid())
                    .document(auth.getCurrentUser().getUid())
                    .collection(category)
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
                                    myKey.add(document.getId());
                                }

                                pd.hide();


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
        adapter = new TodoAdapter(myKey, myTodoList, myCategory);
        adapter.setTodoClickListener(new TodoAdapter.TodoClickListener() {
            @Override
            public void itemClickListener(String itemId, String category,String head) {

                if(category.equals("Mindful")) {
                    Intent intent = new Intent(LIstActivity.this, MindfulActivity.class);
                    intent.putExtra("key", itemId);
                    intent.putExtra("category", category);
                    intent.putExtra("head", head);
                    startActivity(intent);
                    return;
                }


                Intent intent = new Intent(LIstActivity.this, CreateActivity.class);
                intent.putExtra("key", itemId);
                intent.putExtra("category", category);
                startActivityForResult(intent, 100);
            }
        });
        recyclerView.setAdapter(adapter);

        if(myTodoList.size() == 0) {
            new AlertDialog.Builder(LIstActivity.this)
                    .setTitle("Message")
                    .setMessage("Sorry, no information to display")
                    .setCancelable(false)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Whatever...
                            finish();
                        }
                    }).show();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100) {
            loadTodoList();
        }
    }
}