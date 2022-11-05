package com.example.gnosis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.gnosis.model.todo_list_model;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
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

    Calendar calendar = Calendar.getInstance();
    int categoryIndex;



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
        if(getIntent().getExtras().get("key").equals("new")) {
            initInput();
        } else {
            loadDetail();
        }
    }

    private void loadDetail() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String category = getIntent().getExtras().get("category").toString();
        categoryIndex = 0;
        if(!category.equals("All")) {
            categoryIndex =  Arrays.asList(getResources().getStringArray(R.array.spinner_category)).indexOf(category) ;
        }

        String myKey = getIntent().getExtras().get("key").toString();
        db.collection(category)
                .document(myKey)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        etNewTodoName.setText(documentSnapshot.get("name").toString());
                        spinNewTodoCategory.setSelection(categoryIndex);
                        etNewTodoStartDate.setText(documentSnapshot.get("startDate").toString());
                        String[] StartTimeFull = documentSnapshot.get("startTime").toString().split(" ");
                        String[] StartTime = StartTimeFull[0].split(":");
                        spinNewTodoStartHour.setSelection(Integer.parseInt(StartTime[0])-1);
                        if(StartTime[1].equals("00")){
                            spinNewTodoStartMin.setSelection(0);
                        } else {
                            spinNewTodoStartMin.setSelection(Integer.parseInt(StartTime[1])/10);
                        }
                        if( StartTimeFull[1].equals("AM")) {
                            spinNewTodoStartMidday.setSelection(0);
                        } else {
                            spinNewTodoStartMidday.setSelection(1);
                        }
                        etNewTodoEndDate.setText(documentSnapshot.get("endDate").toString());
                        String[] EndTimeFull = documentSnapshot.get("startTime").toString().split(" ");
                        String[] EndTime = EndTimeFull[0].split(":");
                        spinNewTodoEndHour.setSelection(Integer.parseInt(EndTime[0])-1);
                        if(EndTime[1].equals("00")){
                            spinNewTodoEndMin.setSelection(0);
                        } else {
                            spinNewTodoEndMin.setSelection(Integer.parseInt(EndTime[1])/10);
                        }
                        if( EndTimeFull[1].equals("AM")) {
                            spinNewTodoEndMidday.setSelection(0);
                        } else {
                            spinNewTodoEndMidday.setSelection(1);
                        }
                        etNewTodoDescription.setText(documentSnapshot.get("description").toString());

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }

    private void initInput() {

        // Set initial value of Dates as tomorrow
        Calendar initDate = Calendar.getInstance();
        initDate.add(Calendar.DAY_OF_YEAR, 1);

        String txtInitStartDate = String.format("%02d", initDate.get(Calendar.DAY_OF_MONTH));
        txtInitStartDate += "-" + String.format("%02d", initDate.get(Calendar.MONTH)+1);
        txtInitStartDate += "-" + initDate.get(Calendar.YEAR);
        String txtInitEndDate = String.format("%02d", initDate.get(Calendar.DAY_OF_MONTH));
        txtInitEndDate += "-" + String.format("%02d", initDate.get(Calendar.MONTH)+1);
        txtInitEndDate += "-" + initDate.get(Calendar.YEAR);

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
        DatePickerDialog.OnDateSetListener startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i, i1, i2);
                String dateFormat = "dd-MM-yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.getDefault());
                etNewTodoStartDate.setText(sdf.format(calendar.getTime()));
            }
        };

        etNewTodoStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dPDialog = new DatePickerDialog(CreateActivity.this, startDate, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                DatePicker dPicker = dPDialog.getDatePicker();
                dPDialog.show();
            }
        });


        DatePickerDialog.OnDateSetListener endDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i, i1, i2);
                String dateFormat = "dd-MM-yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.getDefault());
                etNewTodoEndDate.setText(sdf.format(calendar.getTime()));
            }
        };

        etNewTodoEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dPDialog = new DatePickerDialog(CreateActivity.this, endDate, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                DatePicker dPicker = dPDialog.getDatePicker();
//                dPicker.setMaxDate(Calendar.getInstance().getTimeInMillis());
                dPDialog.show();
            }
        });

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

        if(getIntent().getExtras().get("key").equals("new")){
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
        } else {
            db.collection(category).document(getIntent().getExtras().get("key").toString())
                    .set(myTodoItem)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
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

    }

    // Check there is input in 'Name'
    private Boolean isInputValid() {
        Boolean validChecker = true;
        if(etNewTodoName.getText().toString() == "") validChecker = false;
        return validChecker;
    }
}