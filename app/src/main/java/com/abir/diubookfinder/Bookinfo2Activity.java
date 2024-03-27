package com.abir.diubookfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Bookinfo2Activity extends AppCompatActivity {

    Button btnedit, btndelete ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookinfo2);

        btnedit =(Button) findViewById(R.id.editbook);
        btndelete = (Button) findViewById(R.id.deletebook);

        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Bookinfo2Activity.this, "WORK IN PROGRESS !!!", Toast.LENGTH_SHORT).show();
            }
        });
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Bookinfo2Activity.this, "WORK IN PROGRESS !!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}