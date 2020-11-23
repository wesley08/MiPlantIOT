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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.miplantiot.HomePage;
import com.example.miplantiot.R;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.concurrent.Executor;

public class FragmentSignUp extends Fragment {

    Button btn_createAccount;
    EditText edt_email, edt_password, edt_username, edt_re_password;
    String email,password,repassword,username;
    LinearLayout btn_signupwithgoogle2;

    private FirebaseAuth auth,mAuth;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;

    private GoogleSignInClient mGoogleSignInClient;
    private  final  static int RC_SIGN_IN = 123;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_sign_up,container,false);

        btn_signupwithgoogle2 = rootView.findViewById(R.id.btn_signupwithgoogle2);
        btn_createAccount = rootView.findViewById(R.id.btn_createAccount);
        edt_email = rootView.findViewById(R.id.edt_email);
        edt_password = rootView.findViewById(R.id.edt_password);
        edt_username = rootView.findViewById(R.id.edt_username);
        edt_re_password = rootView.findViewById(R.id.edt_re_password);

        mAuth = FirebaseAuth.getInstance();

        createRequest();
        auth = FirebaseAuth.getInstance();

        btn_createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edt_email.getText().toString();
                password = edt_password.getText().toString();
                repassword = edt_re_password.getText().toString();
                username = edt_username.getText().toString();

                if(username.isEmpty()){
                    Toast.makeText(getContext(), "username is empty", Toast.LENGTH_LONG).show();
                }else if(email.isEmpty()){
                    Toast.makeText(getContext(), "email is empty", Toast.LENGTH_LONG).show();
                }else if(password.isEmpty()){
                    Toast.makeText(getContext(), "password is empty", Toast.LENGTH_LONG).show();
                }else if(password.length() < 8){
                    Toast.makeText(getContext(), "Password must more then 8 character",Toast.LENGTH_LONG).show();
                }else if(repassword.isEmpty()){
                    Toast.makeText(getContext(), "retype-password is empty", Toast.LENGTH_LONG).show();
                }else if(!password.equals(repassword)){
                    Toast.makeText(getContext(), "the value of password and retype-password is not same", Toast.LENGTH_LONG).show();
                }else if(!email.contains("@")){
                    Toast.makeText(getContext(), "email must be contain @", Toast.LENGTH_LONG).show();
                }else {
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                firebaseUser = auth.getCurrentUser();
                                assert firebaseUser != null;
                                String id = firebaseUser.getUid();
                                Toast.makeText(getContext(), "success", Toast.LENGTH_LONG).show();
                                databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(id);

                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("email", email);
                                hashMap.put("id", id);
                                hashMap.put("username", username);

                                databaseReference.setValue(hashMap).addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                            fragmentTransaction.replace(R.id.fragment_container, new FragmentSignIn());
                                            fragmentTransaction.commit();
                                        } else {
                                            Toast.makeText(getContext(), "unsuccess in databasereference", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(getContext(), "failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
            });

        btn_signupwithgoogle2.setOnClickListener(new View.OnClickListener() {
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


