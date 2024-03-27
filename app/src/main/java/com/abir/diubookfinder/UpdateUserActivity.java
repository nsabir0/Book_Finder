package com.abir.diubookfinder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.abir.BookModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.Serializable;
import java.util.ArrayList;

public class UpdateUserActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

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
        setContentView(R.layout.activity_update_user);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);



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
                    Toast.makeText(UpdateUserActivity.this, "Please Provide BookName First", Toast.LENGTH_SHORT).show();
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
        actionBar.setTitle("User Activity");
        actionBar.setDisplayHomeAsUpEnabled(true);
        searchBr=findViewById(R.id.search_bar);
        btnSearch = findViewById(R.id.btn_search);
        listView=findViewById(R.id.listV);
        mDatabase= firebaseDatabase.getReference("allbook");
        intent = getIntent();
        tvName = findViewById(R.id.text);
        tvName.setText("Hello "+intent.getStringExtra("name"));


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
                .setPositiveButton("Log Out", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).show();
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
