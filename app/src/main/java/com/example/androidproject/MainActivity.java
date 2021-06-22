package com.example.androidproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Button btnPlay;
    private Button btnUpload;
    private Button btnHistory;
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private ArrayList<Relative> relatives;
    private ArrayList<QuestionModel> questionsList;
    private String isSuccessfull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnPlay = findViewById(R.id.btnPlay);
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        isSuccessfull = getIntent().getStringExtra("success");

        if(isSuccessfull != null){
            if(isSuccessfull.equals("true")){
                Snackbar.make(findViewById(android.R.id.content), "Image Uploaded", Snackbar.LENGTH_LONG).show();
            } else {
                Snackbar.make(findViewById(android.R.id.content), "Failed to Upload the image", Snackbar.LENGTH_LONG).show();
            }
        }

        questionsList = new ArrayList<>();
        relatives = new ArrayList<>();
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(questionsList.size() == 5){
                    questionsList.clear();
                }
                Random rnd = new Random();
                ArrayList<Integer> correctAnswers = new ArrayList<>();
                db.collection("Relatives").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                Relative relative = new Relative(d.get("RelativeName").toString(), d.get("ImagePath").toString());
                                relatives.add(relative);
                            }
                            int number = rnd.nextInt(list.size());
                            while (correctAnswers.size() != 5) {
                                while (correctAnswers.contains(number)) {
                                    number = rnd.nextInt(list.size());
                                }
                                correctAnswers.add(number);
                            }

                            for (int i : correctAnswers) {
                                int num1 = rnd.nextInt(list.size());
                                while (i == num1) {
                                    num1 = rnd.nextInt(list.size());
                                }
                                int num2 = rnd.nextInt(list.size());
                                while (i == num2 || num1 == num2) {
                                    num2 = rnd.nextInt(list.size());
                                }
                                int num3 = rnd.nextInt(list.size());
                                while (i == num3 || num1 == num3 || num2 == num3) {
                                    num3 = rnd.nextInt(list.size());
                                }

                                Relative correctRelative = relatives.get(i);
                                Relative relative1 = relatives.get(num1);
                                Relative relative2 = relatives.get(num2);
                                Relative relative3 = relatives.get(num3);
                                int correctAnswerNo = rnd.nextInt(4) + 1;
                                switch (correctAnswerNo) {
                                    case 1:
                                        questionsList.add(new QuestionModel("FIND THE RELATIVE", correctRelative.getRelativeName(),
                                                relative1.getRelativeName(), relative2.getRelativeName(), relative3.getRelativeName(), correctRelative.getImage(),
                                                correctAnswerNo));
                                        break;
                                    case 3:
                                        questionsList.add(new QuestionModel("FIND THE RELATIVE", relative1.getRelativeName(),
                                                relative2.getRelativeName(), correctRelative.getRelativeName(), relative3.getRelativeName(), correctRelative.getImage(),
                                                correctAnswerNo));
                                        break;
                                    case 2:
                                        questionsList.add(new QuestionModel("FIND THE RELATIVE", relative1.getRelativeName(),
                                                correctRelative.getRelativeName(), relative2.getRelativeName(), relative3.getRelativeName(), correctRelative.getImage(),
                                                correctAnswerNo));
                                        break;
                                    case 4:
                                        questionsList.add(new QuestionModel("FIND THE RELATIVE", relative1.getRelativeName(),
                                                relative2.getRelativeName(), relative3.getRelativeName(), correctRelative.getRelativeName(), correctRelative.getImage(),
                                                correctAnswerNo));
                                        break;
                                    default:
                                        questionsList.add(new QuestionModel("FIND THE RELATIVE", "A", "B", "C", "D", correctRelative.getImage(), 1));
                                }

                            }
                            Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                            intent.putParcelableArrayListExtra("question_list", questionsList);
                            startActivity(intent);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Failed to create the question.", Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
            }
        });

        btnUpload = findViewById(R.id.btnUpload);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UploadRelativeActivity.class);
                startActivity(intent);
            }
        });
        btnHistory = findViewById(R.id.btnHistory);
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScoreHistoryActivity.class);
                startActivity(intent);
            }
        });
    }
}