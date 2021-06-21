package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TotalScoreActivity extends AppCompatActivity {
    private Button btnTryAgain;
    private Button btnHome;
    private TextView totalName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_score);
        totalName = findViewById(R.id.totalName);
 Intent iin = getIntent();
 Bundle b = iin.getExtras();
 if(b!=null){
     String j = (String) b.get("name");
     totalName.setText("Total Score: " + j +"/5");
 }

        btnTryAgain = findViewById(R.id.btnTryAgain);
        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TotalScoreActivity.this, QuizActivity.class);
                startActivity(intent);
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