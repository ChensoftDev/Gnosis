package com.example.gnosis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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
    Button btnNewTodoDelete;

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
        btnNewTodoDelete = findViewById(R.id.btnNewTodoDelete);

        etNewTodoStartDate.setOnClickListener(v -> {
            setDateOnEditText(etNewTodoStartDate);
        });

        etNewTodoStartDate.setOnClickListener(v -> {
            setDateOnEditText(etNewTodoStartDate);
        });

        addEventListeners();
        if(getIntent().getExtras().get("key").equals("new")) {
            initInput();
        } else {
            loadDetail();
            btnNewTodoDelete.setVisibility(View.VISIBLE);
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

                        TextView title = findViewById(R.id.tv_title);
                        title.setText(etNewTodoName.getText().toString());

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

        btnNewTodoDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                AlertDialog.Builder alertDBuilder = new AlertDialog.Builder(CreateActivity.this);
                alertDBuilder.setTitle("Do you want to delete?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                db.collection(getIntent().getExtras().get("key").toString())
                                        .document(getIntent().getExtras().get("category").toString())
                                        .delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(CreateActivity.this, "Success to delete", Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(CreateActivity.this, "Failed to delete", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDBuilder.create();
                alertDialog.show();
            }
        });

//        btnNewTodoCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
    }

    private void setDateOnEditText(EditText et) {
        // on below line we are getting
        // the instance of our calendar.
        final Calendar c = Calendar.getInstance();

        // on below line we are getting
        // our day, month and year.
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // on below line we are creating a variable for date picker dialog.
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                // on below line we are passing context.
                CreateActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // on below line we are setting date to our edit text.

                        String Month,Day;

                        if((monthOfYear+1) < 10) {
                            Month = "0" + (monthOfYear + 1);
                        } else {
                            Month =  "" + (monthOfYear + 1);
                        }

                        if(dayOfMonth < 10) {
                            Day = "0" + dayOfMonth;
                        } else {
                            Day = "" + dayOfMonth;
                        }

                        et.setText(Day + "-" + Month + "-" + year);

                    }
                },
                // on below line we are passing year,
                // month and day for selected date in our date picker.
                year, month, day);
        // at last we are calling show to
        // display our date picker dialog.
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
        datePickerDialog.setCanceledOnTouchOutside(false);
        datePickerDialog.show();
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

        if(name.isEmpty()) {
            etNewTodoName.setError("required");
            return;
        }
        if(startDate.isEmpty()) {
            etNewTodoStartDate.setError("required");
            return;
        }
        if(EndDate.isEmpty()) {
            etNewTodoEndDate.setError("required");
            return;
        }
        if(description.isEmpty()) {
            etNewTodoDescription.setError("required");
            return;
        }

        // generate new todo item
        todo_list_model myTodoItem = new todo_list_model(name, startDate, startTime,
                EndDate, endTime, description);



        if(getIntent().getExtras().get("key").equals("new")){
            // Write down new data in Database
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
            // update data in Database
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