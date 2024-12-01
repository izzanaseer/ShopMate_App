package com.example.shopping_list_app;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    TextInputEditText etName, etPassword;
    Button btnLogin;
    TextView tvForgottenPassword, tvLoginWithGmail, tvSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

        tvSignup.setOnClickListener(v->{
            startActivity(new Intent(MainActivity.this, Signup.class));
            finish();
        });



        btnLogin.setOnClickListener(v->{
            String name = etName.getText().toString().trim();
            String password = etPassword.getText().toString();

            if(TextUtils.isEmpty(name)){
                etName.setError("Enter the name");
                return;
            }

            if(TextUtils.isEmpty(password)){
                etPassword.setError("Enter the password");
                return;
            }

            if(password.length() < 6){
                etPassword.setError("Enter minimum 6 digits password");
                return;
            }
        });



    }

    private void init(){
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etName = findViewById(R.id.etName);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgottenPassword = findViewById(R.id.tvForgottenPassword);
        tvLoginWithGmail = findViewById(R.id.tvgmail);
        tvSignup = findViewById(R.id.tvSignup);
    }
}