package com.example.gnosis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;


import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MindfulActivity extends AppCompatActivity {

    YouTubePlayerView yt_player;

    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mindful);

        String title = getIntent().getExtras().get("head").toString();

        TextView title_head = findViewById(R.id.tv_mindful_title);

        title_head.setText(title);

        yt_player = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(yt_player);

        Button btn_delete = findViewById(R.id.button_delete);

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                AlertDialog.Builder alertDBuilder = new AlertDialog.Builder(MindfulActivity.this);
                alertDBuilder.setTitle("Do you want to delete?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                db.collection(auth.getCurrentUser().getUid())
                                        .document(auth.getCurrentUser().getUid())
                                        .collection(getIntent().getExtras().get("key").toString())
                                        .document(getIntent().getExtras().get("category").toString())
                                        .delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(MindfulActivity.this, "Success to delete", Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(MindfulActivity.this, "Failed to delete", Toast.LENGTH_SHORT).show();
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



        loadDetail();


    }

    private String getYouTubeId (String youTubeUrl) {
        String pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(youTubeUrl);
        if(matcher.find()){
            return matcher.group();
        } else {
            return "error";
        }
    }


    private void loadDetail() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String category = getIntent().getExtras().get("category").toString();

        String myKey = getIntent().getExtras().get("key").toString();
        db.collection(auth.getCurrentUser().getUid())
                .document(auth.getCurrentUser().getUid())
                .collection(category)
                .document(myKey)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        String yturl = getYouTubeId(documentSnapshot.get("description").toString());

                        yt_player.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                            @Override
                            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                                super.onReady(youTubePlayer);
                                youTubePlayer.loadVideo(yturl,0);
                            }
                        });


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }
}