package com.abir.diubookfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.Model;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    EditText etName, etMail, etPass;
    Button btnSign;
    RadioGroup rgMain;
    RadioButton rbUser, rbSeller;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    String role ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etName = findViewById(R.id.name);
        etMail = findViewById(R.id.email1);
        etPass = findViewById(R.id.password1);
        btnSign = findViewById(R.id.signup);
        rgMain = findViewById(R.id.rg_main2);
        rbUser = findViewById(R.id.rb_user2);
        rbSeller = findViewById(R.id.rb_seller2);
        TextView textView=findViewById(R.id.textView);

        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("home");



        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rb_user2){
                    role = "USER";
                }
                if(checkedId == R.id.rb_seller2){
                    role = "SELLER";
                }
            }
        });




        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String mail = etMail.getText().toString().trim();
                String pass = etPass.getText().toString().trim();
                if (name.isEmpty() || mail.isEmpty() || pass.isEmpty() ||role.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Provide Information First", Toast.LENGTH_SHORT).show();
                    return;
                }
                DatabaseReference push = reference.child(role).push();
                Model model = new Model(push.getKey(), name, mail, pass, role);
                push.setValue(model);
                if(role.equals("USER")){
                    Toast.makeText(SignupActivity.this, "Account Created Successfully.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignupActivity.this, Login1Activity.class));
                    finish();
                }else{
                    Toast.makeText(SignupActivity.this, "Account Created Successfully.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignupActivity.this, Login1Activity.class));
                    finish();
                }
                finish();

            }
        });

        textView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent MainAct=new Intent(SignupActivity.this, MainActivity.class);
                startActivity(MainAct);
            }
        });
    }

}
