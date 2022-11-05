package com.example.gnosis;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gnosis.model.TimetableActivityModel;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;

public class CRUDTimeTableActivity extends AppCompatActivity {
    private final FirebaseFirestore fireStore = FirebaseFirestore.getInstance();
    private final CollectionReference timetableRef = fireStore.collection("timetable");
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private TimetableActivityModel param;
    private EditText eClassname;
    private EditText eStartTime;
    private EditText eEndTime;
    private EditText eClassroom;
    private Spinner spinnerClassDay;
    private Button btnUpSert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crudtime_table);
        TextView toolbar = findViewById(R.id.toolbar);
        param = getIntent().getParcelableExtra("param");
        toolbar.setText(param == null ? "Add Timetable" : "Timetable Detail");
        eClassname = findViewById(R.id.edit_text_class_name);
        eStartTime = findViewById(R.id.edit_text_start_time);
        eEndTime = findViewById(R.id.edit_text_end_time);
        eClassroom = findViewById(R.id.edit_text_classroom);
        spinnerClassDay = findViewById(R.id.spinner_class_day);
        btnUpSert = findViewById(R.id.btn_upsert);
        btnUpSert.setOnClickListener(v -> {
            upSert();
        });
        initDefault();
    }

    @SuppressWarnings("ConstantConditions")
    private void upSert() {
        TimetableActivityModel activity = param != null ? param : new TimetableActivityModel();
        activity.setDay(spinnerClassDay.getSelectedItem().toString());

        String className = eClassname.getText().toString().trim();
        String classRoom = eClassroom.getText().toString().trim();
        String startTime = eStartTime.getText().toString().trim();
        String endTime = eEndTime.getText().toString();

        if(className.isEmpty()) {
            eClassname.setError("required");
            return;
        }
        if(startTime.isEmpty()) {
            eStartTime.setError("required");
            return;
        }
        if(endTime.isEmpty()) {
            eEndTime.setError("required");
            return;
        }
        if(classRoom.isEmpty()) {
            eClassroom.setError("required");
            return;
        }

        activity.setClassName(className);
        activity.setClassroom(classRoom);
        activity.setStartTime(startTime);
        activity.setEndTime(endTime);

        DocumentReference ref = timetableRef
                .document(auth.getCurrentUser().getUid())
                .collection(activity.getDay())
                .document(activity.getUid());

        Task<Void> task;

        if (param == null) {
            task = ref.set(activity);
        } else {
            task = ref.update(activity.toHashMapUpdate());
        }
        task.addOnCompleteListener(this, task1 -> {
            if (!task1.isSuccessful()) {
                Toast.makeText(this, task1.getException().toString(), Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void initDefault() {
        if (param == null) return;
        String[] classEntryDay = getResources().getStringArray(R.array.class_entry_day);

        int defaultSelectDay = Arrays.asList(classEntryDay).indexOf(param.getDay());

        spinnerClassDay.setSelection(defaultSelectDay);

        spinnerClassDay.setEnabled(false);


        // Set default param
        eClassname.setText(param.getClassName());
        eStartTime.setText(param.getStartTime());
        eEndTime.setText(param.getEndTime());
        eClassroom.setText(param.getClassroom());
    }

    public void openTimePicker(View view) {


        MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .build();
        materialTimePicker.addOnPositiveButtonClickListener(view1 -> {
            EditText editText = (EditText) view;
            boolean isPm = materialTimePicker.getHour() > 12;
            int hour = isPm ? materialTimePicker.getHour() - 12 : materialTimePicker.getHour();
            editText.setText(String.format("%02d:%02d %s", hour, materialTimePicker.getMinute(), isPm ? "PM" : "AM"));
        });
        materialTimePicker.show(getSupportFragmentManager(), "Time picker");
    }

}