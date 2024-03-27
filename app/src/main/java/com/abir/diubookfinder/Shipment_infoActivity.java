package com.abir.diubookfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.Model;
import com.abir.BookModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Shipment_infoActivity extends AppCompatActivity {

    Spinner spinnerCity;
    Spinner spinnerArea;
    TextView bName, bWriter, bPrice;
    EditText temp_address,temp_phone;
    Button btn_confirm,btn_back;
    CheckBox checkBox;
    String book_Id,seller_Id,customer_Id;
    SharedPreferences sp;
    String userInfo;
    Intent intent;
    BookModel book;
    Model model;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipment_info);

        bName =findViewById(R.id.bname);
        bWriter =findViewById(R.id.bwriter);
        bPrice =findViewById(R.id.bprice);
        spinnerCity = findViewById(R.id.city_sp);
        spinnerArea = findViewById(R.id.area_sp);
        temp_address = findViewById(R.id.address);
        temp_phone = findViewById(R.id.phone);
        checkBox = findViewById(R.id.agree);
        btn_back = findViewById(R.id.btn_back);
        btn_confirm= findViewById(R.id.btn_confirm);

        intent = getIntent();
        firebaseDatabase = FirebaseDatabase.getInstance();
        book = (BookModel) intent.getSerializableExtra("book");
        model = (Model) intent.getSerializableExtra("model");
        reference = firebaseDatabase.getReference("order");

        bName.setText(book.getName());
        bWriter.setText(book.getWriter());
        bPrice.setText("BDT. "+book.getPrice());

        book_Id=book.getId();
        seller_Id=book.getSellerId();
        sp = getSharedPreferences("userInfo", MODE_PRIVATE);
        customer_Id= sp.getString("info", "abir");
        final int status= 1;


        ArrayAdapter city_arrayAdapter = ArrayAdapter.createFromResource(this,R.array.city_list,android.R.layout.simple_spinner_item);
        spinnerCity.setAdapter(city_arrayAdapter);
        ArrayAdapter area_arrayAdapter = ArrayAdapter.createFromResource(this,R.array.area_list,android.R.layout.simple_spinner_item);
        spinnerArea.setAdapter(area_arrayAdapter);

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String a= spinnerCity.getSelectedItem().toString();
                String b= spinnerArea.getSelectedItem().toString();
                String c= temp_address.getText().toString().trim();
                String address= a +", "+ b +", " + c +", " ;
                String phone = temp_phone.getText().toString().trim();

                if (checkBox.isChecked()) {
                    DatabaseReference push = reference.push();
                    RequestModel requestMode = new RequestModel(push.getKey(), book_Id, seller_Id, customer_Id, address, phone, status);
                    push.setValue(requestMode);

                    AlertDialog alertDialog = new AlertDialog.Builder(Shipment_infoActivity.this).create();
                    alertDialog.setTitle("Congratulations !!!");
                    alertDialog.setMessage("Your Order is taken.");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    finish();
                                }
                            });
                    alertDialog.show();

                }else {
                    Toast.makeText(Shipment_infoActivity.this, "Complete Requirements First", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish(); }
        });

    }
}