package com.example.cake_house;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class updateseller extends AppCompatActivity {


    public static final String TAG = "TAG";
    EditText seditEmail,seditAddress,seditname,seditContactrd,sDescription;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    Button savebtn;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateseller);


        Intent data=getIntent();
        String fullname=data.getStringExtra("fname");
        String email=data.getStringExtra("Email");
        String Description=data.getStringExtra("Description");
        String contact=data.getStringExtra("Contact");
        String adress=data.getStringExtra("Adress");


        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        user= fAuth.getCurrentUser();

        seditname=findViewById(R.id.editename);
        seditAddress=findViewById(R.id.editeAddress);
        seditEmail=findViewById(R.id.editeEmail);
        seditContactrd=findViewById(R.id.editeContact);
        sDescription=findViewById(R.id.EditDescription);

        savebtn=findViewById(R.id.savebtnid);
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seditname.getText().toString().isEmpty()||seditAddress.getText().toString().isEmpty()||seditEmail.getText().toString().isEmpty()||seditContactrd.getText().toString().isEmpty()||sDescription.getText().toString().isEmpty()){
                    Toast.makeText(updateseller.this, "one or many field are emty", Toast.LENGTH_SHORT).show();
                    return;
                }
                String email=seditEmail.getText().toString();
                user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        DocumentReference docref=fStore.collection("sellers").document(user.getUid());
                        Map<String,Object>edited=new HashMap<>();
                        edited.put("Email",email);
                        edited.put("fname",seditname.getText().toString());
                        edited.put("Adress",seditAddress.getText().toString());
                        edited.put("Description",sDescription.getText().toString());
                        edited.put("Contact",seditContactrd.getText().toString());



                 docref.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                     @Override
                     public void onSuccess(Void unused) {
                         Toast.makeText(updateseller.this, "prifle updated", Toast.LENGTH_SHORT).show();
                         startActivity(new Intent(getApplicationContext(),sellerprofile.class));
                         finish();
                     }
                 });

                        Toast.makeText(updateseller.this, "Email is change", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(updateseller.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        seditname.setText(fullname);
        seditAddress.setText(adress);
        seditEmail.setText(email);
        seditContactrd.setText(contact);
        sDescription.setText(Description);

        Log.d(TAG,"Oncreate"+fullname+""+adress+""+email+""+contact+""+Description);



    }
}