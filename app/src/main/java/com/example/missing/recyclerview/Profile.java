package com.example.missing.recyclerview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Profile extends AppCompatActivity {

    private String id_single;

    private DatabaseReference databaseReference;

    private DatabaseReference mCmmentData;

    private ImageView image;
    private TextView title;
    private TextView description;
    private Toolbar pro_toolbar;
    private EditText commet_box;
    private Button sumit_Comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__page);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Blog");
        mCmmentData = FirebaseDatabase.getInstance().getReference().child("Blog");

        id_single = getIntent().getExtras().getString("blog_id");
//        Toast.makeText(Profile.this, id_single, Toast.LENGTH_LONG).show();

        image = findViewById(R.id.pro_imageView);
        title = findViewById(R.id.pro_title);
        description = findViewById(R.id.pro_des);

        commet_box = findViewById(R.id.comment);
        sumit_Comment= findViewById(R.id.btn_c_sumit);

        sumit_Comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String data = commet_box.getText().toString();
                commet_box.setText("");

                DatabaseReference newpost = mCmmentData.push();
                newpost.child("Comment").setValue(data);

                final String key = newpost.getRef().getKey();
                Toast.makeText(Profile.this,key,Toast.LENGTH_LONG).show();

            }
        });

//        final String key = databaseReference.getKey()





        pro_toolbar = findViewById(R.id.pro_toolbar);
        setSupportActionBar(pro_toolbar);
        getSupportActionBar().setTitle("Profile");
        pro_toolbar.setTitleTextColor(0xFFFFFFFF);
        pro_toolbar.setNavigationIcon(R.drawable.back);
        pro_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main_act = new Intent(Profile.this,MainActivity.class);
                startActivity(main_act);
            }
        });



        databaseReference.child(id_single).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String mtitle = (String) dataSnapshot.child("title").getValue();
                String mdescripton = (String)dataSnapshot.child("description").getValue();
                String mimage = (String)dataSnapshot.child("image").getValue();

                title.setText(mtitle);
                description.setText(mdescripton);

                Picasso.get().load(mimage).into(image);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

}
