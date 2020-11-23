package com.example.miplantiot.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.miplantiot.ForgotPassword;
import com.example.miplantiot.HomePage;
import com.example.miplantiot.MainActivity;
import com.example.miplantiot.R;
import com.example.miplantiot.SignInUpPage;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.concurrent.Executor;

public class FragmentSignIn extends Fragment {

    EditText edt_email_sign_in,edt_password_sign_in;
    Button btn_signin_email;
    TextView txt_forgot_password;
    LinearLayout btn_signupwithgoogle1;

    private FirebaseAuth auth;

    private GoogleSignInClient mGoogleSignInClient;
    private  final  static int RC_SIGN_IN = 123;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_sign_in,container,false);

        btn_signupwithgoogle1 = rootView.findViewById(R.id.btn_signupwithgoogle1);
        txt_forgot_password =rootView.findViewById(R.id.txt_forgot_password);
        edt_email_sign_in = rootView.findViewById(R.id.edt_email_sign_in);
        edt_password_sign_in = rootView.findViewById(R.id.edt_password_sign_in);
        btn_signin_email = rootView.findViewById(R.id.btn_signin_email);
        auth = FirebaseAuth.getInstance();

        mAuth = FirebaseAuth.getInstance();

        createRequest();

        txt_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ForgotPassword.class);
                startActivity(intent);
            }
        });

        btn_signin_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edt_email_sign_in.getText().toString();
                String password = edt_password_sign_in.getText().toString();

                if (email.isEmpty()) {
                    Toast.makeText(getContext(), "email cannot be empty", Toast.LENGTH_LONG).show();
                }else if (password.isEmpty()) {
                    Toast.makeText(getContext(), "password cannot be empty", Toast.LENGTH_LONG).show();
                }else {
                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.fragment_container, new FragmentRegisterDevice());
                                fragmentTransaction.commit();
                            } else {
                                Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        btn_signupwithgoogle1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        return rootView;
    }

    private void createRequest(){
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getContext(),gso);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {

            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(getActivity() , HomePage.class);
                            startActivity(intent);
                        } else {

                        }
                    }
                });
    }

}
