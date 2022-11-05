package com.example.gnosis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.gnosis.model.todo_list_model;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CreateActivity extends AppCompatActivity {
    EditText etNewTodoName;
    Spinner spinNewTodoCategory;
    EditText etNewTodoStartDate;
    Spinner spinNewTodoStartHour;
    Spinner spinNewTodoStartMin;
    Spinner spinNewTodoStartMidday;
    EditText etNewTodoEndDate;
    Spinner spinNewTodoEndHour;
    Spinner spinNewTodoEndMin;
    Spinner spinNewTodoEndMidday;
    EditText etNewTodoDescription;
    Button btnNewTodoSubmit;
    //Button btnNewTodoCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        etNewTodoName = findViewById(R.id.etNewTodoName);
        spinNewTodoCategory = findViewById(R.id.spinNewTodoCategory);
        etNewTodoStartDate = findViewById(R.id.etNewTodoStartDate);
        spinNewTodoStartHour = findViewById(R.id.spinNewTodoStarHour);
        spinNewTodoStartMin = findViewById(R.id.spinNewTodoStartMin);
        spinNewTodoStartMidday = findViewById(R.id.spinNewTodoStartMidday);
        etNewTodoEndDate = findViewById(R.id.etNewTodoEndDate);
        spinNewTodoEndHour = findViewById(R.id.spinNewTodoEndHour);
        spinNewTodoEndMin = findViewById(R.id.spinNewTodoEndMin);
        spinNewTodoEndMidday = findViewById(R.id.spinNewTodoStartMidday);
        etNewTodoDescription = findViewById(R.id.etNewTodoDescription);
        btnNewTodoSubmit = findViewById(R.id.btnNewTodoSubmit);
        //btnNewTodoCancel = findViewById(R.id.btnNewTodoCancel);

        addEventListeners();
        initInput();
    }

    private void initInput() {

        // Set initial value of Dates as tomorrow
        Calendar initDate = Calendar.getInstance();
        initDate.add(Calendar.DAY_OF_YEAR, 1);
        String txtInitStartDate = String.valueOf(initDate.get(Calendar.DAY_OF_MONTH));
        txtInitStartDate += "-" + String.valueOf(initDate.get(Calendar.MONTH)+1);
        txtInitStartDate += "-" + String.valueOf(initDate.get(Calendar.YEAR));
        String txtInitEndDate = String.valueOf(initDate.get(Calendar.DAY_OF_MONTH));
        txtInitEndDate += "-" + String.valueOf(initDate.get(Calendar.MONTH)+1);
        txtInitEndDate += "-" + String.valueOf(initDate.get(Calendar.YEAR));

        etNewTodoStartDate.setText(txtInitStartDate);
        etNewTodoEndDate.setText(txtInitEndDate);

        // set initial value of time 9:00 AM ~ 10:00 AM
        spinNewTodoStartHour.setSelection(8);
        spinNewTodoStartMin.setSelection(0);
        spinNewTodoStartMidday.setSelection(0);
        spinNewTodoEndHour.setSelection(9);
        spinNewTodoEndMin.setSelection(0);
        spinNewTodoEndMidday.setSelection(0);


    }

    private void addEventListeners() {
        btnNewTodoSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isInputValid()) {
                    setOnDatabase();
                }
            }
        });

//        btnNewTodoCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
    }

    private void setOnDatabase() {
        // read input values
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String name = etNewTodoName.getText().toString();
        String startDate = etNewTodoStartDate.getText().toString();
        String startTime = spinNewTodoStartHour.getSelectedItem().toString();
        startTime += ":" + spinNewTodoStartMin.getSelectedItem().toString();
        startTime += " " + spinNewTodoStartMidday.getSelectedItem().toString();
        String EndDate = etNewTodoEndDate.getText().toString();
        String endTime = spinNewTodoEndHour.getSelectedItem().toString();
        endTime += ":" + spinNewTodoEndMin.getSelectedItem().toString();
        endTime += " " + spinNewTodoEndMidday.getSelectedItem().toString();
        String description = etNewTodoDescription.getText().toString();
        String category = spinNewTodoCategory.getSelectedItem().toString();

        // generate new todo item
        todo_list_model myTodoItem = new todo_list_model(name, startDate, startTime,
                EndDate, endTime, description);

        // Write down data in Database
        db.collection(category).add(myTodoItem)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(CreateActivity.this, "Succeed in writing data.",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CreateActivity.this, "Failed to write data.",
                                        Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Check there is input in 'Name'
    private Boolean isInputValid() {
        Boolean validChecker = true;
        if(etNewTodoName.getText().toString() == "") validChecker = false;
        return validChecker;
    }
}