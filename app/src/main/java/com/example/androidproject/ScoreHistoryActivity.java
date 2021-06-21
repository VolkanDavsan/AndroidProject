package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ScoreHistoryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    MyAdapter myAdapter;
    ArrayList<Score> list2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_history2);
        recyclerView = findViewById(R.id.scoreList);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        db = FirebaseFirestore.getInstance();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list2 = new ArrayList<>();
        myAdapter = new MyAdapter(this,list2);
        recyclerView.setAdapter(myAdapter);

        db.collection("Scores").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : list) {
                        Score score = new Score(d.get("Score").toString(), d.get("Date").toString());
                        list2.add(score);
                    }
                    myAdapter.notifyDataSetChanged();
                }
            }


        });
    }
}