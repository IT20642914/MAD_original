package com.example.cake_house;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Sign_up_switch1 extends AppCompatActivity {
private Button sellreg;
    private Button buyerreg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_switch1);



        sellreg=findViewById(R.id.sellerbtnlogin); //going to edit profile view
        sellreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Sign_up_switch1.this, "lording seller registation form", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Sign_up_switch1.this,Seller_registation.class);
                startActivity(intent);
            }
        });


        buyerreg=findViewById(R.id.buyerbtnlogin); //going to edit profile view
        buyerreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Sign_up_switch1.this, "lording buyer registation form", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Sign_up_switch1.this,Buyer_registation.class);
                startActivity(intent);
            }
        });


    }
}