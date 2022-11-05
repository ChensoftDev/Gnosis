package com.example.gnosis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.gnosis.model.TimetableActivityModel;
import com.example.gnosis.reclerview.TimetableActivityListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.List;

public class TimetableActivity extends AppCompatActivity implements TimetableActivityListAdapter.Listener {
    private final FirebaseFirestore fireStore = FirebaseFirestore.getInstance();
    private final CollectionReference timetableRef = fireStore.collection("timetable");
    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    TimetableActivityListAdapter adapterMon = new TimetableActivityListAdapter(R.color.timetable_row, this);
    TimetableActivityListAdapter adapterTue = new TimetableActivityListAdapter(R.color.timetable_row_2, this);
    TimetableActivityListAdapter adapterWed = new TimetableActivityListAdapter(R.color.timetable_row, this);
    TimetableActivityListAdapter adapterThu = new TimetableActivityListAdapter(R.color.timetable_row_2, this);
    TimetableActivityListAdapter adapterFri = new TimetableActivityListAdapter(R.color.timetable_row, this);
    TimetableActivityListAdapter adapterSat = new TimetableActivityListAdapter(R.color.timetable_row_2, this);
    TimetableActivityListAdapter adapterSun = new TimetableActivityListAdapter(R.color.timetable_row, this);

    ListenerRegistration listenerMon;
    ListenerRegistration listenerTue;
    ListenerRegistration listenerWed;
    ListenerRegistration listenerThu;
    ListenerRegistration listenerFri;
    ListenerRegistration listenerSat;
    ListenerRegistration listenerSun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        RecyclerView rvMon = findViewById(R.id.rv_mon_activity);
        RecyclerView rvTue = findViewById(R.id.rv_tue_activity);
        RecyclerView rvWed = findViewById(R.id.rv_wed_activity);
        RecyclerView rvThu = findViewById(R.id.rv_thu_activity);
        RecyclerView rvFri = findViewById(R.id.rv_fri_activity);
        RecyclerView rvSat = findViewById(R.id.rv_sat_activity);
        RecyclerView rvSun = findViewById(R.id.rv_sun_activity);

        rvMon.setAdapter(adapterMon);
        rvTue.setAdapter(adapterTue);
        rvWed.setAdapter(adapterWed);
        rvThu.setAdapter(adapterThu);
        rvFri.setAdapter(adapterFri);
        rvSat.setAdapter(adapterSat);
        rvSun.setAdapter(adapterSun);

        FloatingActionButton fabAddTimetableActivity = findViewById(R.id.fab_add_activity);
        fabAddTimetableActivity.setOnClickListener(v -> {
            startActivity(new Intent(this, CRUDTimeTableActivity.class));
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        listenerMon = createRealtimeDocumentListener("Monday", adapterMon);
        listenerTue = createRealtimeDocumentListener("Tuesday", adapterTue);
        listenerWed = createRealtimeDocumentListener("Wednesday", adapterWed);
        listenerThu = createRealtimeDocumentListener("Thursday", adapterThu);
        listenerFri = createRealtimeDocumentListener("Friday", adapterFri);
        listenerSat = createRealtimeDocumentListener("Saturday", adapterSat);
        listenerSun = createRealtimeDocumentListener("Sunday", adapterSun);
    }

    @Override
    protected void onDestroy() {
        listenerMon.remove();
        listenerTue.remove();
        listenerWed.remove();
        listenerThu.remove();
        listenerFri.remove();
        listenerSat.remove();
        listenerSun.remove();
        super.onDestroy();
    }

    @SuppressWarnings("ConstantConditions")
    private ListenerRegistration createRealtimeDocumentListener(String day, TimetableActivityListAdapter adapter) {
        return timetableRef.document(auth.getCurrentUser().getUid()).collection(day).addSnapshotListener((value, error) -> {
            if(error != null || value == null) {
                Toast.makeText(this, day + " error", Toast.LENGTH_SHORT).show();
                return;
            }
            List<TimetableActivityModel> items = value.toObjects(TimetableActivityModel.class);
            adapter.submitList(items);
        });
    }

    @Override
    public void onClick(TimetableActivityModel item) {
        Intent intent = new Intent(this, CRUDTimeTableActivity.class);
        intent.putExtra("param", item);
        startActivity(intent);
    }
}