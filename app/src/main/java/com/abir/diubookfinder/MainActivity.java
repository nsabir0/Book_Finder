package com.abir.diubookfinder;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnUser, btnSeller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnUser = findViewById(R.id.btnuser);
        btnSeller = findViewById(R.id.btnseller);


        btnUser.setOnClickListener(this);
        btnSeller.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId()==R.id.btnuser)
        {
            Intent intent = new Intent(MainActivity.this,Login1Activity.class);
            startActivity(intent);
        }

        if (v.getId()==R.id.btnseller)
        {
            Intent intent = new Intent(MainActivity.this,Login1Activity.class);
            startActivity(intent);
        }

    }
}