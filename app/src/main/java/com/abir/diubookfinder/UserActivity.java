package com.abir.diubookfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.abir.BookModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {


    ActionBar actionBar;
    EditText searchBr;
    ImageButton btnSearch;
    TextView tvName;
    ListView listView;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mDatabase;
    ArrayList<BookModel> allBook = new ArrayList<>();
    ArrayList<BookModel> selectedBook = new ArrayList<>();
    BaseAdapter adapter;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        init();
//        String id = intent.getStringExtra("id");
        getValue();
        populateList();

        searchBr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getDataByText(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookName = searchBr.getText().toString().trim();
                if(bookName.isEmpty()){
                    Toast.makeText(UserActivity.this, "Please Provide BookName First", Toast.LENGTH_SHORT).show();
                    return;
                }

                for(int i = 0; i < allBook.size(); i++){
                    if(bookName.equals(allBook.get(i).getName())){
                        selectedBook.add(allBook.get(i));

                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                startActivity(new Intent(getApplicationContext(), BookinfoActivity.class).putExtra("book", (Serializable) selectedBook.get(position)));
            }
        });
    }

    private void init() {

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("User Activity");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        searchBr=findViewById(R.id.search_bar);
        btnSearch = findViewById(R.id.btn_search);
        listView=findViewById(R.id.listV);
        mDatabase= firebaseDatabase.getReference("allbook");
        intent = getIntent();
        tvName = findViewById(R.id.text);
        tvName.setText("Hello ");
//        tvName.setText("Hello "+intent.getStringExtra("name"));

    }

    private void getDataByText(CharSequence s) {
        selectedBook.clear();
        for(BookModel book : allBook){
            if(book.getName().toLowerCase().startsWith(s.toString().toLowerCase())){
                selectedBook.add(book);
            }
        }
        adapter.notifyDataSetChanged();
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
                .setPositiveButton("Log Out & Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(UserActivity.this, MainActivity.class));
                    }
                }).show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        if(item.getItemId() == R.id.about){
            Intent intent = new Intent(UserActivity.this,AboutActivity.class);
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

    private void populateList() {
        adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return selectedBook.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                convertView = getLayoutInflater().inflate(android.R.layout.simple_list_item_1, parent, false);
                TextView tvName = convertView.findViewById(android.R.id.text1);
                tvName.setText(selectedBook.get(position).getName());
                return convertView;
            }
        };
        listView.setAdapter(adapter);
    }



    private void getValue() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    BookModel model = data.getValue(BookModel.class);
                    allBook.add(model);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
