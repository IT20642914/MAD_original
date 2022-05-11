package com.example.cake_house;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Buyer_registation extends AppCompatActivity {


    EditText editEmail,editPassword,editAddress,editname,editContact,editCpassword;
    Button signupB;
    TextView signup,Password,Address,name,Cpassword,ContactNo,Email,LOGB;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fstore;
    String userID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_registation);


        editname = findViewById(R.id.editename);
        editAddress=findViewById(R.id.editeAddress);
        editEmail=findViewById(R.id.editeEmail);
        editContact=findViewById(R.id.editeContact);
        editPassword=findViewById(R.id.editPassword);
        editCpassword=findViewById(R.id.editCpassword);
        signupB=findViewById(R.id.savebtn);
        LOGB=findViewById(R.id.LOGB);
        progressBar=findViewById(R.id.progressBarS);

        fAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();



        if (fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),locationhome.class));
            finish();
        }

signupB.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        String email= editEmail.getText().toString().trim();
        String password=editPassword.getText().toString().trim();
        String FullName= editname.getText().toString();
        String phone=editContact.getText().toString();
        String Address=editAddress.getText().toString();



        if(TextUtils.isEmpty(email)){
            editEmail.setError("email is  requiresd");
            return;
        }

        if(TextUtils.isEmpty(password)){
            editPassword.setError("Password is  required");
            return;
        }
        if (password.length()<6){
            editPassword.setError("password must be 6 Characters");
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        //register the user in firebase
        fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    Toast.makeText(Buyer_registation.this, "User created", Toast.LENGTH_SHORT).show();
                    userID=fAuth.getCurrentUser().getUid();
                    DocumentReference documentreference=fstore.collection("users").document(userID);
                    Map<String,Object> user=new HashMap<>();

                    user.put("Address",Address);
                    user.put("Fname",FullName);
                    user.put("email",email);
                    user.put("phone",phone);


                    documentreference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d("TAG","onSuccess:user Profile is created for"+userID);
                        }
                    });

                    startActivity(new Intent(getApplicationContext(),locationhome.class));
                }else {
                    Toast.makeText(Buyer_registation.this, "Error !"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }

            }
        });

    }
});

LOGB.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(getApplicationContext(),Buyer_Login.class));
    }
});


    }
}