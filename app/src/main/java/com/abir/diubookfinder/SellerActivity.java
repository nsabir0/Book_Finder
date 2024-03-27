package com.abir.diubookfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SellerActivity extends AppCompatActivity {

    LinearLayout btnAdd,btnprofile,btnorder,btnlist;
    TextView tvName;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mDatabase;
    Intent intent;
    BaseAdapter adapter;
    String sellerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);


        btnprofile= findViewById(R.id.button0);
        btnlist =  findViewById(R.id.button1);
        btnAdd =  findViewById(R.id.button2);
        btnorder =  findViewById(R.id.button3);

        mDatabase= firebaseDatabase.getReference("allbook");
        intent = getIntent();
        tvName = findViewById(R.id.text);
        tvName.setText("Hello "+intent.getStringExtra("name"));
        sellerId = intent.getStringExtra("id");

        btnprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SellerActivity.this, editBookActivity.class));
            }
        });
        btnlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SellerActivity.this, BooklistActivity.class).putExtra("sellerId", sellerId));
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SellerActivity.this, addBookActivity.class).putExtra("sellerId", sellerId));
            }
        });
        btnorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SellerActivity.this, OrderActivity.class));
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        new AlertDialog.Builder(this)
                .setTitle("CONFIRMATION !!!")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        return;
                    }
                })
                .setPositiveButton("Log Out", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode ==  KeyEvent.KEYCODE_BACK){
            onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        if(item.getItemId() == R.id.about){
            Intent intent = new Intent(SellerActivity.this,AboutActivity.class);
            startActivity(intent);
        }
        if(item.getItemId() == R.id.logout){
            finish();
        }
        if(item.getItemId() == R.id.exit){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
