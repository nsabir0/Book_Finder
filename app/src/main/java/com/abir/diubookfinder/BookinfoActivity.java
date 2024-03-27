package com.abir.diubookfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.abir.BookModel;

public class BookinfoActivity extends AppCompatActivity {

    TextView bname,bwriter,blibrary,bphone,baddress,bprice;
    Button btnaddtocart, btnpurchase ;
    Intent intent;
    BookModel book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookinfo);

        btnaddtocart =(Button) findViewById(R.id.addtocart);
        btnpurchase = (Button) findViewById(R.id.purchase);
        intent = getIntent();
        book = (BookModel) intent.getSerializableExtra("book");

        bname = findViewById(R.id.bname);
        bwriter = findViewById(R.id.bwriter);
        blibrary = findViewById(R.id.blibrary);
        bphone = findViewById(R.id.bphone);
        baddress = findViewById(R.id.baddress);
        bprice = findViewById(R.id.bprice);

        bname.setText(book.getName());
        bwriter.setText(book.getWriter());
        blibrary.setText(book.getLibrary());
        bphone.setText(book.getPhone());
        baddress.setText(book.getAddress());
        bprice.setText("BDT. "+book.getPrice());
        final String id = intent.getStringExtra("userId");



        btnaddtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BookinfoActivity.this, "WORK IN PROGRESS !!!", Toast.LENGTH_SHORT).show();
            }
        });
        btnpurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BookinfoActivity.this, Shipment_infoActivity.class).putExtra("book", book));
            }
        });
    }
}