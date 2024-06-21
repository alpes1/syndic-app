package com.example.test;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class signup extends AppCompatActivity {

    EditText edtnom,edtnum_imm,edtnum_app,edtcin,edtemail,edtpassword,edtconfpassword;
    String nom,num_imm,num_app,cin,email,password,confpassword;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView log;
        Activity activity = signup.this;

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        edtnom= findViewById(R.id.nom);
        edtnum_imm= findViewById(R.id.immeuble);
        edtnum_app= findViewById(R.id.appart);
        edtcin= findViewById(R.id.cin);
        edtemail= findViewById(R.id.email);
        edtpassword= findViewById(R.id.password);
        edtconfpassword= findViewById(R.id.confpassword);

        log = (TextView) findViewById(R.id.logred);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signup.this, login.class);
                activity.startActivity(intent);
            }
        });

        Button button = (Button) findViewById(R.id.BtnSignup);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!validate()) {
                } else {
                    addUser();
                }
            }
        });
    }
    public Boolean validate() {

        boolean end= true;

        nom= edtnom.getText().toString();
        num_imm= edtnum_imm.getText().toString();
        num_app= edtnum_app.getText().toString();
        email= edtemail.getText().toString();
        password= edtpassword.getText().toString();
        confpassword= edtconfpassword.getText().toString();

        if (nom.isEmpty()) {
            edtnom.setError("Le nom et prenom sont obligatoires");
            end=false;
        } else {
            edtnom.setError(null);
        }
        if (num_imm.isEmpty()) {
            edtnum_imm.setError("Le numero de l'immeuble est obligatoire");
            end=false;
        } else {
            edtnum_imm.setError(null);
        }
        if (num_app.isEmpty()) {
            edtnum_app.setError("Le numero de l'appartement est obligatoire");
            end=false;
        } else {
            edtnum_app.setError(null);
        }
        if (email.isEmpty()) {
            edtemail.setError("L'email est obligatoire");
            end=false;
        } else {
            edtemail.setError(null);
        }
        if (password.isEmpty()) {
            edtpassword.setError("Le mot de passe est obligatoire");
            end=false;
        } else {
            edtpassword.setError(null);
        }
        if (!Objects.equals(password, confpassword)) {
            edtconfpassword.setError("Le mot de passe n'est pas le même!");
            end=false;
        } else {
            edtconfpassword.setError(null);
        }
        return end;
    }

    public void addUser(){
        Activity activity = signup.this;
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        nom= edtnom.getText().toString();
        num_imm= edtnum_imm.getText().toString();
        num_app= edtnum_app.getText().toString();
        cin= edtcin.getText().toString();
        email= edtemail.getText().toString();
        password= edtpassword.getText().toString();

        Map<String, Object> user = new HashMap<>();
        user.put("NomPrenom", nom);
        user.put("Num_immeuble", num_imm);
        user.put("Num_appartement", num_app);
        user.put("Cin", cin);
        user.put("Email", email);
        user.put("Password", password);

        db.collection("users").add(user);
        Log.d("BUTTONS", "User added");

        Intent intent = new Intent(signup.this, login.class);
        activity.startActivity(intent);
    }
}