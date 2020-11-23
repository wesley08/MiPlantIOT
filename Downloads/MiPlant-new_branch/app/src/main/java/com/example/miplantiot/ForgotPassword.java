package com.example.miplantiot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    Button btn_send_email;
    EditText edt_forget_email;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        btn_send_email = findViewById(R.id.btn_send_email);
        edt_forget_email = findViewById(R.id.edt_forget_email);

        auth = FirebaseAuth.getInstance();

        btn_send_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = edt_forget_email.getText().toString();

                if (email.isEmpty()) {
                    Toast.makeText(getApplication(), "email cannot be empty", Toast.LENGTH_LONG).show();
                }else {

                    auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });
    }
}
