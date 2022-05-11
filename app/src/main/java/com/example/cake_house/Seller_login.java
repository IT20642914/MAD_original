package com.example.cake_house;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Seller_login extends AppCompatActivity {
    EditText seditEmail,seditPassword;
    Button loginbtn;
    TextView sregseller;
    ProgressBar progressBar1;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_login);

        seditEmail = findViewById(R.id.editTextemail);
        seditPassword=findViewById(R.id.editTextPassword);
        progressBar1=findViewById(R.id.progressBar2);
        fAuth=FirebaseAuth.getInstance();
        loginbtn=findViewById(R.id.signinbtnseller);
        sregseller=findViewById(R.id.regseller);


        fstore=FirebaseFirestore.getInstance();
        if (fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),sellerprofile.class));
            finish();
        }

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= seditEmail.getText().toString().trim();
                String password=seditPassword.getText().toString().trim();






                if(TextUtils.isEmpty(email)){
                    seditEmail.setError("email is  requiresd");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    seditPassword.setError("Password is  required");
                    return;
                }

                progressBar1.setVisibility(View.VISIBLE);
                //seller login
                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            Toast.makeText(Seller_login.this, "Seller logeged in successfuly", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),sellerprofile.class));
                        }else {
                            Toast.makeText(Seller_login.this, "Error !"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar1.setVisibility(View.GONE);
                        }
                    }
                });


            }
        });

        sregseller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Seller_registation.class));

            }
        });
    }
}