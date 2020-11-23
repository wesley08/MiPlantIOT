package com.example.miplantiot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.miplantiot.fragment.FragmentSignIn;
import com.example.miplantiot.fragment.FragmentSignUp;

public class SignInUpPage extends AppCompatActivity {
    TextView txt_signin, txt_signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_up_page);

        txt_signin = findViewById(R.id.txt_signin);
        txt_signup = findViewById(R.id.txt_signup);

        txt_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentSignIn fragmentSignIn = new FragmentSignIn();
                replaceFragment(fragmentSignIn);
            }
        });

        txt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentSignUp fragmentSignUp = new FragmentSignUp();
                replaceFragment(fragmentSignUp);
            }
        });

    }
    private void replaceFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container,fragment);
        transaction.commit();
    }
}
