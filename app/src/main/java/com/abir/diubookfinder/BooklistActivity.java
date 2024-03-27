package com.abir.diubookfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.Model;
import com.abir.BookModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BooklistActivity extends AppCompatActivity {

    Intent intent;
    String id;
    ArrayList<BookModel> allBook;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    ListView list_book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booklist);

        intent = getIntent();
        id = intent.getStringExtra("sellerId");
        allBook = new ArrayList<>();
        list_book = findViewById(R.id.list_book);
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("allbook");
        getlist();
        list_book.setAdapter(adapter);

    }

    BaseAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return allBook.size();
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
            TextView tvBookName = convertView.findViewById(android.R.id.text1);
            tvBookName.setText(allBook.get(position).getName());
            return convertView;
        }
    };


    public void getlist() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for( DataSnapshot data : dataSnapshot.getChildren()){
                    BookModel value = data.getValue(BookModel.class);
                    if(value.getSellerId().equals(id)){
                        allBook.add(value);
                        adapter.notifyDataSetChanged();
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }
}