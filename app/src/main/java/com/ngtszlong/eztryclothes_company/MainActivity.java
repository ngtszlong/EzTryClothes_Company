package com.ngtszlong.eztryclothes_company;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth fAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Company Login");
        fAuth = FirebaseAuth.getInstance();
        if (fAuth.getCurrentUser() != null){
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
        }else{
            nouserfound();
        }
    }

    private void nouserfound() {
        CardView btn_login = findViewById(R.id.btn_login);
        final EditText edt_email = findViewById(R.id.edt_login_email);
        final EditText edt_pw = findViewById(R.id.edt_login_pw);
        progressDialog = new ProgressDialog(this);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = edt_email.getText().toString();
                String password = edt_pw.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    edt_email.setError("Email is Required");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    edt_pw.setError("Password must be more than 8 words");
                    return;
                }

                progressDialog.setMessage("Logging in, Please wait...");
                progressDialog.show();

                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(MainActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            System.out.println(task.getException().getMessage());
                        }
                    }
                });
            }
        });
    }
}
