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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class Seller_registation extends AppCompatActivity {

    EditText seditEmail,seditPassword,seditAddress,seditname,seditContact,seditCpassword,sDescription;
    Button savedb;
    TextView LOGB;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fstore;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_registation);

        seditname= findViewById(R.id.editename);
        seditEmail= findViewById(R.id.editeEmail);
        seditPassword= findViewById(R.id.editPassword);
        seditCpassword= findViewById(R.id.editCpassword);
        seditContact= findViewById(R.id.editeContact);
        seditAddress= findViewById(R.id.editeAddress);
        sDescription= findViewById(R.id.EditDescription);
        savedb= findViewById(R.id.signupbtnseller);


        fAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
        if (fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),sellerprofile.class));
            finish();
        }
        savedb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= seditEmail.getText().toString().trim();
                String password=seditPassword.getText().toString().trim();
                String confrimPassword=seditCpassword.getText().toString();
                String NAME=seditname.getText().toString();
                String from=seditAddress.getText().toString();




                if(TextUtils.isEmpty(email)){
                    seditEmail.setError("email is  requiresd");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    seditPassword.setError("Password is  required");
                    return;
                }
                if (password.length()<6){
                    seditPassword.setError("password must be 6 Characters");
                    return;
                }
                if(!email.matches(emailPattern))
                {
                    seditEmail.setError("Enter Correct Email");
                }
                if(!password.equals(confrimPassword))
                {
                  seditCpassword.setError("Password Not match both field");
                }
                progressBar.setVisibility(View.VISIBLE);

                //register the user in firebase
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            Toast.makeText(Seller_registation.this, "Seller created", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(getApplicationContext(),sellerprofile.class));
                        }else {
                            Toast.makeText(Seller_registation.this, "Error !"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });

            }

        });








    }
}