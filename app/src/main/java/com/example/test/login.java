package com.example.test;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class login extends AppCompatActivity {
    EditText txtEmail, txtPassword;
    Button loginbtn;

    private static final String TAG = "MyActivity";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView sup;
        Activity activity = login.this;
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        txtEmail = findViewById(R.id.txteml);
        txtPassword = findViewById(R.id.txtpwd);


        sup = (TextView) findViewById(R.id.supred);
        sup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, signup.class);
                activity.startActivity(intent);
            }
        });
        loginbtn = findViewById(R.id.btnLogin);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateEmail() || !validatePassword()) {
                } else {
                    checkUser();
                }
            }
        });
    }

    public Boolean validateEmail() {
        String val = txtEmail.getText().toString();
        if (val.isEmpty()) {
            txtEmail.setError("Email cannot be empty");
            return false;
        } else {
            txtEmail.setError(null);
            return true;
        }
    }
    public Boolean validatePassword() {
        String val = txtPassword.getText().toString();
        if (val.isEmpty()) {
            txtPassword.setError("Password cannot be empty");
            return false;
        } else {
            txtPassword.setError(null);
            return true;
        }
    }

    public void checkUser(){
        String userEmail = txtEmail.getText().toString().trim();
        String userPassword = txtPassword.getText().toString().trim();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users")
                .whereEqualTo("Email", userEmail)
                .whereEqualTo("Password", userPassword)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if(task.getResult().isEmpty()){
                                txtEmail.setError("Email might be wrong");
                                txtPassword.setError("Password might be wrong");
                            }else{
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Activity activity = login.this;
                                    Intent intent = new Intent(login.this, home.class);
                                    activity.startActivity(intent);
                                }
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

}