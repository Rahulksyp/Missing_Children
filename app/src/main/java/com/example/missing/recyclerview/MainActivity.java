package com.example.missing.recyclerview;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

   private DatabaseReference databaseReference;
   FirebaseAuth mAuth;
   FirebaseAuth.AuthStateListener mAuthListener;
   private ViewPager mviewPager;
   private SectionPageAdapter sectionPageAdapter;
   private TabLayout mTablayout;
   private Toolbar mToolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         mAuth = FirebaseAuth.getInstance();
         mAuthListener = new FirebaseAuth.AuthStateListener() {
             @Override
             public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                 if (firebaseAuth .getCurrentUser()==null){

                     Intent RegIntent = new Intent(MainActivity. this,Reg_Activity.class);
                     RegIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                     startActivity(RegIntent);
                 }

             }
         };

         mToolbar = findViewById(R.id.toolbar);
         setSupportActionBar(mToolbar);
         getSupportActionBar().setTitle("Missing Children");
         mToolbar.setTitleTextColor(0xFFFFFFFF);



        mviewPager = findViewById(R.id.tab_pager);
        sectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager());
        mviewPager.setAdapter(sectionPageAdapter);
        mTablayout=findViewById(R.id.main_tabs);
        mTablayout.setupWithViewPager(mviewPager);

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.logout){

            logOut();
        }


        return super.onOptionsItemSelected(item);
    }

    private void logOut() {

        mAuth.signOut();
    }

    @Override
    protected void onStart() {

        mAuth.addAuthStateListener(mAuthListener);

        super.onStart();

    }


}
