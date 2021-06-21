package com.example.androidproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TotalScoreActivity extends AppCompatActivity {
    private Button btnTryAgain;
    private Button btnHome;
    private TextView totalName;
    private ArrayList<Relative> relatives;
    private ArrayList<QuestionModel> questionsList;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_score);
        totalName = findViewById(R.id.totalName);
        questionsList = getIntent().getParcelableArrayListExtra("question_list");
        relatives = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        Intent iin = getIntent();
        Bundle b = iin.getExtras();
        if (b != null) {
            String j = (String) b.get("name");
            totalName.setText("Total Score: " + j + "/5");
        }

        btnTryAgain = findViewById(R.id.btnTryAgain);
        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(questionsList.size() > 0){
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
                            Intent intent = new Intent(TotalScoreActivity.this, QuizActivity.class);
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


        btnHome = findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TotalScoreActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}