package com.example.missing.recyclerview;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class EXPLORE extends Fragment {
    private RecyclerView itemList;
    private Button add;
    private DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    private FloatingActionButton fab;

    public EXPLORE() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_explore, container, false);

        itemList=view.findViewById(R.id.list_item);


        mAuth = FirebaseAuth.getInstance();

        itemList.setLayoutManager(new LinearLayoutManager(getContext()));
        add=view.findViewById(R.id.add);

        fab =view. findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Intent intent = new Intent(getContext(),Add_Item.class);
                startActivity(intent);
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Blog");


        return view;

    }
    @Override
    public void onStart() {
        super.onStart();


        final FirebaseRecyclerAdapter<Adapter,AdapterViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter
                <Adapter, AdapterViewHolder>(

                Adapter.class,
                R.layout.card_item,
                AdapterViewHolder.class,
                databaseReference
        )
        {
            @Override
            protected void populateViewHolder(final AdapterViewHolder viewHolder, final Adapter model, final int position) {

                final String key = getRef(position).getKey();


                viewHolder.setTitle(model.title);
                viewHolder.setDesc(model.description);
                viewHolder.setimage(model.image);

//
//                viewHolder.mview.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//
//                    }
//                });


                //// onclick on view item //////



                viewHolder.mview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {

                        Intent myIntent = new Intent(view.getContext(), Profile.class);
                        myIntent.putExtra("blog_id",key);
                        startActivity(myIntent);


                    }
                });

            }
        };

        itemList.setAdapter(firebaseRecyclerAdapter);

    }


    public static class AdapterViewHolder extends RecyclerView.ViewHolder{

        View mview;
        public View buttonViewOption;

        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            mview = itemView;
        }

        public void setTitle(String title){

            TextView card_title = mview.findViewById(R.id.title);
            card_title.setText(title);
        }

        public void setDesc(String description){

            TextView card_description = mview.findViewById(R.id.description);
            card_description.setText(description);
        }


        public void setimage(String image) {
            ImageView card_image = mview.findViewById(R.id.image);
            Picasso.get().load(image).into(card_image);

        }



    }





}
