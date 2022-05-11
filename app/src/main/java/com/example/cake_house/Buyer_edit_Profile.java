package com.example.cake_house;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class Buyer_edit_Profile extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText proname,proaddress,prophone,proemail;
    ImageView propice;
    Button Savebtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_edit_profile);

        Intent data=getIntent();
        String Fullname =data.getStringExtra("Fname");
        String Address =data.getStringExtra("Address");
        String phone =data.getStringExtra("phone");
        String email=data.getStringExtra("email");


        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        user=fAuth.getCurrentUser();
        storageReference= FirebaseStorage.getInstance().getReference();

        proemail=findViewById(R.id.editeEmail);
        proname=findViewById(R.id.editename);
        proaddress=findViewById(R.id.editeAddress);
        prophone=findViewById(R.id.editeContact);
        propice=findViewById(R.id.propice);
        Savebtn=findViewById(R.id.savebtn);

        StorageReference profileRef= storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Picasso.get().load(uri).into(propice);

            }
        });

        Savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (proname.getText().toString().isEmpty()||proemail.getText().toString().isEmpty()||proaddress.getText().toString().isEmpty()||prophone.getText().toString().isEmpty()){

                    Toast.makeText(Buyer_edit_Profile.this, "One or Many fields are empty.", Toast.LENGTH_SHORT).show();
                    return;

                }

                String email=proemail.getText().toString();
                user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        DocumentReference docRef=fStore.collection("users").document(user.getUid());
                        Map<String,Object> edited=new HashMap<>();
                        edited.put("email",email);
                        edited.put("Fname",proname.getText().toString());
                        edited.put("Address",proaddress.getText().toString());
                        edited.put("phone",prophone.getText().toString());
                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(Buyer_edit_Profile.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),Buyer_Profile.class));
                                finish();
                            }
                        });


                        Toast.makeText(Buyer_edit_Profile.this, "email is changed", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(Buyer_edit_Profile.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });



            }
        });


        proemail.setText(email);
        proname.setText(Fullname);
        proaddress.setText(Address);
        prophone.setText(phone);

        propice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openGalleryIntent =new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent,1000);
            }
        });


        Savebtn=findViewById(R.id.savebtn);

        Log.d(TAG, "onCreate: "+Fullname+""+email+""+Address+""+phone);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==1000){
            if (resultCode== Activity.RESULT_OK){
                Uri imageUri = data.getData();
                //propic.setImageURI(imageUri);

                uploadImageToFirebase(imageUri);




            }
        }

    }


    private void uploadImageToFirebase(Uri imageUri) {

        // uplaod image to firebase storage

        final StorageReference fileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(propice);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(Buyer_edit_Profile.this, "Failed", Toast.LENGTH_SHORT).show();

            }
        });


    }




}