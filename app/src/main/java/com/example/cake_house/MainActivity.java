package com.example.cake_house;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button signinbtn;
    private Button signup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        signinbtn=findViewById(R.id.wsignin); //going to edit profile view
        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "lording registation type", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this,Sign_in_switch.class);
                startActivity(intent);
            }
        });

        signup=findViewById(R.id.idsignup); //going to edit profile view
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "lording login", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this,Sign_up_switch1.class);
                startActivity(intent);
            }
        });







    }
}