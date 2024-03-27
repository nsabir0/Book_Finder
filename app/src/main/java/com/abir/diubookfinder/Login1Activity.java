package com.abir.diubookfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.Model;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login1Activity extends AppCompatActivity implements View.OnClickListener{

    EditText etuser, etpass;
    Button btnLogin,btnSignup;
    RadioGroup rgMain;
    RadioButton rbUser, rbSeller;
    String role ="";

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);

        etuser= findViewById(R.id.email);
        etpass= findViewById(R.id.password);
        rgMain = findViewById(R.id.rg_main);
        rbUser = findViewById(R.id.rb_user);
        rbSeller = findViewById(R.id.rb_seller);
        btnLogin = findViewById(R.id.login);
        btnSignup = findViewById(R.id.signup);

        firebaseDatabase = FirebaseDatabase.getInstance();
        sp = getSharedPreferences("userInfo", MODE_PRIVATE);
        reference = firebaseDatabase.getReference("home");

        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rb_user){
                    role = "USER";
                }
                if(checkedId == R.id.rb_seller){
                    role = "SELLER";
                }
            }
        });

        etuser.setOnClickListener(this);
        etpass.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnSignup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        try {
            if(v.getId() == R.id.login){
                String mail = etuser.getText().toString().trim();
                String pass = etpass.getText().toString().trim();

                if (mail.isEmpty() || pass.isEmpty() || role.isEmpty()) {
                    Toast.makeText(Login1Activity.this, "Please Enter Username and Password !!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                logIn(mail, pass, role);
            }

            if (v.getId()==R.id.signup) {
                Intent intent = new Intent(Login1Activity.this,SignupActivity.class);
                startActivity(intent);
            }

        }catch (Exception e){
            Toast.makeText(Login1Activity.this, "SOMETHING IS WRONG !!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void logIn(final String mail, final String pass, final String role) {

        if(role.equals("USER")){
            reference.child("USER").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int a = 0;
                    for( DataSnapshot data : dataSnapshot.getChildren()){
                        Model value = data.getValue(Model.class);
                        if(value.getEmail().equals(mail) && value.getPassword().equals(pass)){
                            a = 1;
                            sp.edit().putString("info", value.getId()).apply();


                            Toast.makeText(Login1Activity.this, "Log In Successful.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), UserActivity.class).putExtra("id", value.getId()).putExtra("name", value.getName()));
                            finish();
                        }
                    }
                    if(a == 0){
                        Toast.makeText(Login1Activity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }if(role.equals("SELLER")){
            reference.child("SELLER").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int a = 0;
                    for( DataSnapshot data : dataSnapshot.getChildren()){
                        Model value = data.getValue(Model.class);
                        if(value.getEmail().equals(mail) && value.getPassword().equals(pass)){
                            a = 1;

                            Toast.makeText(Login1Activity.this, "Log In Successful.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), SellerActivity.class).putExtra("id", value.getId()).putExtra("name", value.getName()));
                            finish();
                        }
                    }
                    if(a == 0){
                        Toast.makeText(Login1Activity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }
}