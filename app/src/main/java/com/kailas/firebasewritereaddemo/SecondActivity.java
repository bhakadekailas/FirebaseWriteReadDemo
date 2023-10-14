package com.kailas.firebasewritereaddemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SecondActivity extends AppCompatActivity {
    EditText etFirstName;
    Button btnGetMobileNo;
    TextView tvMobileNo;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initViews();
    }

    private void initViews() {
        etFirstName = findViewById(R.id.etFirstName);
        btnGetMobileNo = findViewById(R.id.btnGetMobileNo);
        tvMobileNo = findViewById(R.id.tvMobileNo);

        firebaseDatabase = FirebaseDatabase.getInstance();
        dbRef = firebaseDatabase.getReference("users");

        btnGetMobileNo.setOnClickListener(v -> {
            if (validation()) {
                dbRef.child(etFirstName.getText().toString()).get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult().exists()) {
                            DataSnapshot dataSnapshot = task.getResult();
                            String mobileNo = dataSnapshot.child("mobile").getValue(String.class);
                            tvMobileNo.setText(mobileNo);
                        }else {
                            tvMobileNo.setText("User not found !!");
                        }
                    }
                });
            }
        });
    }

    private boolean validation() {
        if (etFirstName.getText().toString().isEmpty()) {
            etFirstName.setError("Please Enter First Name");
            etFirstName.requestFocus();
            return false;
        }
        return true;
    }
}