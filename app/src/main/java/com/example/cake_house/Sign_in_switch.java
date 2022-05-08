package com.example.cake_house;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Sign_in_switch extends AppCompatActivity {
    private Button selllog;
    private Button buyerlog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_switch);

///page navigation
        selllog=findViewById(R.id.sellerbtnlogin); //going to edit profile view
        selllog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Sign_in_switch.this, "lording seller login form", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Sign_in_switch.this,Seller_login.class);
                startActivity(intent);
            }
        });
        buyerlog=findViewById(R.id.buyerbtnlogin); //going to edit profile view
        buyerlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Sign_in_switch.this, "lording seller login form", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Sign_in_switch.this,Buyer_Login.class);
                startActivity(intent);
            }
        });



    }
}