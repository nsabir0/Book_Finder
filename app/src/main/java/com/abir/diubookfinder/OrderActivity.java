package com.abir.diubookfinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import java.util.ArrayList;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.abir.BookModel;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnPending, btnDeliver;
    ListView listView;
    BookModel book;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mDatabase, bData;
    ArrayList<RequestModel> allOrder = new ArrayList<>();
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        listView = findViewById(R.id.listV);

        mDatabase= firebaseDatabase.getReference("order");
        bData = firebaseDatabase.getReference("allbook");
        intent = getIntent();




        listView.setAdapter(adapter);

    }

    BaseAdapter adapter  = new BaseAdapter() {
        @Override
        public int getCount() {
            return allOrder.size();
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
            TextView tv = convertView.findViewById(android.R.id.text1);
            String bookId = allOrder.get(position).getBookId();
            BookModel b = findBook(bookId);
            tv.setText(allOrder.get(position).getPhoneNumber()+" ");
            return convertView;
        }
    };

    private  BookModel findBook(final String bookId) {
        String id, name, writer, library, address, phone, price, sellerId;
        bData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren()){
                    BookModel  b = data.getValue(BookModel.class);
                    if(b.getId() == bookId){
                        book = b;
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return book;
    }

    private void getValue() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    RequestModel model = data.getValue(RequestModel.class);
                    if(model.getStatus() == 1){
                        allOrder.add(model);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onClick(View v) {

    }
}