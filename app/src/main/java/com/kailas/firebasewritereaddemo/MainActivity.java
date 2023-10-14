package com.kailas.firebasewritereaddemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText etFirstName, etLastName, etEmail, etMobile;
    Button buttonSubmit, buttonGoToSecondActivity;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference dbRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etMobile = findViewById(R.id.etMobile);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonGoToSecondActivity = findViewById(R.id.buttonGoToSecondActivity);

        firebaseDatabase = FirebaseDatabase.getInstance();
        dbRef = firebaseDatabase.getReference("users");

        buttonGoToSecondActivity.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        });

        buttonSubmit.setOnClickListener(v -> {
            if (formValidation()) {
                UserDataModel userDataModel = new UserDataModel();
                userDataModel.setFirstName(etFirstName.getText().toString());
                userDataModel.setLastName(etLastName.getText().toString());
                userDataModel.setEmail(etEmail.getText().toString());
                userDataModel.setMobile(etMobile.getText().toString());

                dbRef.child(userDataModel.firstName).setValue(userDataModel).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "User added successfully !!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
    }

    private boolean formValidation() {
        if (etFirstName.getText().toString().isEmpty()) {
            etFirstName.setError("Please Enter First Name");
            etFirstName.requestFocus();
            return false;
        } else if (etLastName.getText().toString().isEmpty()) {
            etLastName.setError("Please Enter Last Name");
            etLastName.requestFocus();
            return false;
        } else if (etEmail.getText().toString().isEmpty()) {
            etEmail.setError("Please Enter Email");
            etEmail.requestFocus();
            return false;
        } else if (etMobile.getText().toString().isEmpty()) {
            etMobile.setError("Please Enter Mobile");
            etMobile.requestFocus();
            return false;
        }
        return true;
    }
}