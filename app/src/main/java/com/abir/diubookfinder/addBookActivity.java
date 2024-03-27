package com.abir.diubookfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.abir.BookModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class addBookActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etName, etWriterName, etLibraryName, etLibraryContact, etLibraryAddress, etPrice;
    Button btnSave;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    Intent intent;
    String sellerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        init();

        btnSave.setOnClickListener(this);


    }

    private void init() {
        etName = findViewById(R.id.bname);
        etWriterName = findViewById(R.id.bwriter);
        etLibraryName = findViewById(R.id.blibrary);
        etLibraryAddress = findViewById(R.id.baddress);
        etLibraryContact = findViewById(R.id.bphone);
        etPrice = findViewById(R.id.bprice);
        btnSave = findViewById(R.id.addbutton);
        intent = getIntent();

        sellerId = intent.getStringExtra("sellerId");
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("allbook");
    }

    @Override
    public void onClick(View v) {

        String name = etName.getText().toString().trim();
        String writer = etWriterName.getText().toString().trim();
        String library = etLibraryName.getText().toString().trim();
        String address = etLibraryAddress.getText().toString().trim();
        String phone = etLibraryContact.getText().toString().trim();
        String price = etPrice.getText().toString().trim();

        if(!name.isEmpty() || !writer.isEmpty() || !library.isEmpty() || !address.isEmpty() || !phone.isEmpty() ||!price.isEmpty()){
            DatabaseReference push = reference.push();
            BookModel model = new BookModel(push.getKey(), name, writer, library, address, phone, price, sellerId);
            push.setValue(model);
            Toast.makeText(addBookActivity.this, "Book Added Successfully.", Toast.LENGTH_SHORT).show();
            finish();
        }

    }
}
