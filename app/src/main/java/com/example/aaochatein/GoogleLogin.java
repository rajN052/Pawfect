package com.example.aaochatein;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import com.example.aaochatein.databinding.ActivityGoogleLoginBinding;
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
import com.google.firebase.database.FirebaseDatabase;


public class GoogleLogin extends AppCompatActivity {

    ActivityGoogleLoginBinding binding;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityGoogleLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Signing in");
        progressDialog.setMessage("Were creating your Account");

        if (mAuth.getCurrentUser() != null) {
            Intent intent = new Intent(GoogleLogin.this, HomePage.class);
            startActivity(intent);
        }


        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).
                requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);


        binding.google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signininten = gsc.getSignInIntent();
                startActivityForResult(signininten, 100);
                progressDialog.show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);

                firebaseAuthwithGoogle(account.getIdToken());


            } catch (ApiException e) {
                e.printStackTrace();
                Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show();

            }
        }
    }


    private void firebaseAuthwithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    String id = task.getResult().getUser().getUid();

                    Toast.makeText(GoogleLogin.this, "welcome " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
                    navigatetosecondactivity(id);
                }

                else
                {
                    Toast.makeText(GoogleLogin.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();

            }
        });
    }


    void navigatetosecondactivity(String ID)
    {
        Intent intent = new Intent(GoogleLogin.this, Dog_Info.class);
        intent.putExtra("ID", ID);
        startActivity(intent);
    }





}
