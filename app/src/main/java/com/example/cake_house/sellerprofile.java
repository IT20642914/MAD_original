package com.example.cake_house;


import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class sellerprofile extends AppCompatActivity {
    public static final int CAMERA_PERM_CODE = 101;
    public static final int Camere_request_code = 102;
    public static final int GALLEY_REQESTCODE = 105;
    String currentPhotoPath;
    StorageReference storageReference;

    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    TextView shopname,adress,Description,email,contact,Password;
    String Userid;
    Button myselling_btn1;



    ImageView proimage;
    ImageButton gallerybtn,proeditcam,updatesellerp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellerprofile);
        updatesellerp=findViewById(R.id.selleredit);
        proimage=findViewById(R.id.circleImageView);
        gallerybtn=findViewById(R.id.selleredit2);
        proeditcam=findViewById(R.id.camera);
        fAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
        storageReference=FirebaseStorage.getInstance().getReference();

        shopname=findViewById(R.id.shopname);
        adress=findViewById(R.id.addressid);
        Description=findViewById(R.id.Descriptionid);
        email=findViewById(R.id.emailid);
        contact=findViewById(R.id.Contactid);
        myselling_btn1=findViewById(R.id.myselling_btn);

        Userid=fAuth.getCurrentUser().getUid();

       //retriving data from base
        DocumentReference documentReference=fstore.collection("sellers").document(Userid);
        documentReference.addSnapshotListener(this,new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
            if(documentSnapshot.exists()) {
                shopname.setText(documentSnapshot.getString("fname"));
                adress.setText(documentSnapshot.getString("Adress"));
                Description.setText(documentSnapshot.getString("Description"));
                contact.setText(documentSnapshot.getString("Contact"));
                email.setText(documentSnapshot.getString("Email"));

            }else {
                Log.d("tag","Onevent:Document do not exists");
            }


            }
        });



        StorageReference profileref=storageReference.child("sller_profils/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(proimage);
            }
        });

updatesellerp.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Toast.makeText(sellerprofile.this, "lording seller registation form", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(v.getContext(),updateseller.class);
        intent.putExtra("fname",shopname.getText().toString());
        intent.putExtra("Adress",adress.getText().toString());
        intent.putExtra("Description",Description.getText().toString());
        intent.putExtra("Contact",contact.getText().toString());
        intent.putExtra("Email",email.getText().toString());

        startActivity(intent);

    }
});


        myselling_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(sellerprofile.this,myselling.class));
            }
        });




// set profile pictuer
        gallerybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery =new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery,GALLEY_REQESTCODE);

            }
        });
        proeditcam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(sellerprofile.this,"camera clikeed",Toast.LENGTH_SHORT).show();
                askcamerapermissions();
            }
        });



    }
    //camera permitions
    private void askcamerapermissions() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){



            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CAMERA}, CAMERA_PERM_CODE);

        }else{
            dispatchTakePictureIntent();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==CAMERA_PERM_CODE){
            if (grantResults.length<0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                dispatchTakePictureIntent();
            }else{
                Toast.makeText(this,"Camera permission is Required to use camera",Toast.LENGTH_SHORT).show();
            }
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Camere_request_code) {
            if (resultCode== Activity.RESULT_OK){
                File f= new File(currentPhotoPath);
              //  proimage.setImageURI(Uri.fromFile(f));
                Log.d("tag", "ABsolute URl of image is"+Uri.fromFile(f));

                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);

                Uri contentUri = Uri.fromFile(f);
                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);



                uplordImageTofirebase(contentUri);

            }
        }
        if (requestCode == GALLEY_REQESTCODE) {
            if (resultCode== Activity.RESULT_OK){
                Uri contentUri =data.getData();
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "JPEG_" + timeStamp + "_"+getFileEXT(contentUri);
                Log.d("tag", "ONActivityResult :gallery image"+imageFileName);
             //   proimage.setImageURI(contentUri);//display the image

                uplordImageTofirebase(contentUri);



            }
        }
    }

    private void uplordImageTofirebase( Uri contentUri) {
     final   StorageReference Image=storageReference.child("sller_profils/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        Image.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                       // Log.d("tag", "onSuccess:upoaded image URL is"+uri.toString());
                        Picasso.get().load(uri).into(proimage);

                    }
                });
                Toast.makeText(sellerprofile.this, "uplord successful", Toast.LENGTH_SHORT).show();
            }

        }).addOnFailureListener(e -> Toast.makeText(sellerprofile.this, "uplord faild", Toast.LENGTH_SHORT).show());



    }


    private String getFileEXT(Uri conetctUri) {
        ContentResolver c=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(c.getType(conetctUri));
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_"; //setting name
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, Camere_request_code);
            }
        }
    }





    //login out
    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Seller_login.class));
        finish();
    }



}