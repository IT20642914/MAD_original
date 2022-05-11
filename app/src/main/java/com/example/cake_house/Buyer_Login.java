package com.example.cake_house;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Buyer_Login extends AppCompatActivity {

    EditText editEmail,editPassword;
    Button signupB;
    ImageView image;
    TextView CrateB;
    ProgressBar progressBarS;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_login);


        editEmail=findViewById(R.id.editTextemail);

        editPassword= findViewById(R.id.editTextPassword);
        signupB=findViewById(R.id.savebtn);
        fAuth = FirebaseAuth.getInstance();
        CrateB=findViewById(R.id.CrateB);

        signupB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String email= editEmail.getText().toString().trim();
                String password=editPassword.getText().toString().trim();


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



                //authenication user

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){


                            Toast.makeText(Buyer_Login.this, "Login is  success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),locationhome.class));
                        }else {
                            Toast.makeText(Buyer_Login.this, "Error !"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }

                    }
                });


            }
        });

CrateB.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(getApplicationContext(),Buyer_registation.class));
    }
});


    }
}