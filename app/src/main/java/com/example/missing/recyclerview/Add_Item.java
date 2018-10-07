package com.example.missing.recyclerview;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

public class Add_Item extends AppCompatActivity {
    ImageView addimage;
    Button done;
    EditText title,description;
    ProgressDialog progressBar;

    private static final int GALLERY_REQUEST = 2;


    private StorageReference storageReference;
    private DatabaseReference database;
    private Uri imageUri;

    private Toolbar Add_item_Toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__item);

        storageReference = FirebaseStorage.getInstance().getReference();
        database = FirebaseDatabase.getInstance().getReference().child("users").child("Blog");

        addimage=findViewById(R.id.add_image);
        done=findViewById(R.id.done);
        title=findViewById(R.id.title);
        description=findViewById(R.id.description);
        progressBar = new ProgressDialog(this);


        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryItent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryItent.setType("image/*");
                startActivityForResult(galleryItent,GALLERY_REQUEST);

            }
        });


        Add_item_Toolbar = findViewById(R.id.Add_item_toolbar);
        setSupportActionBar(Add_item_Toolbar);
        Add_item_Toolbar.setNavigationIcon(R.drawable.back);
        Add_item_Toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main_act = new Intent(Add_Item.this,MainActivity.class);
                startActivity(main_act);
            }
        });





        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startPosting();

            }
        });


    }

    private void startPosting() {
        progressBar.setMessage("Wait a min");

        progressBar.setCanceledOnTouchOutside(false);

        final String title_Value = title.getText().toString().trim();
        final String description_value = description.getText().toString().trim();

        if (!TextUtils.isEmpty(title_Value) && !TextUtils.isEmpty(description_value) && imageUri == null);{
            progressBar.show();

        StorageReference filepath = storageReference.child("Blog_Image").child(imageUri.getLastPathSegment());

        filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Uri downloadUrl = taskSnapshot.getDownloadUrl();

                DatabaseReference newpost = database.push();
                newpost.child("title").setValue(title_Value);
                newpost.child("description").setValue(description_value);
                newpost.child("image").setValue(downloadUrl.toString());


                progressBar.dismiss();

                startActivity(new Intent(Add_Item.this,MainActivity.class));


            }
        });

        }



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GALLERY_REQUEST && resultCode == RESULT_OK){

            imageUri = data.getData();
            addimage.setImageURI(imageUri);

        }
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (resultCode == RESULT_OK) {
//                imageUri = result.getUri();
//                addimage.setImageURI(imageUri);
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Exception error = result.getError();
//            }
//        }



    }
}
