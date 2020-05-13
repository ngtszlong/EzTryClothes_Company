package com.ngtszlong.eztryclothes_company;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth fAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoadLocale();
        SharedPreferences pref = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firststart = pref.getBoolean("firstStart", true);
        if (firststart) {
            showStartDialog();
        }
        setContentView(R.layout.activity_main);
        setTitle(R.string.CompanyLogin);
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
                    edt_email.setError(getText(R.string.EmailisRequired));
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    edt_pw.setError(getText(R.string.Passwordmorethan8words));
                    return;
                }

                progressDialog.setMessage(getText(R.string.Loggingin));
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

    private void showStartDialog() {
        final String[] listItems = {"中文", "English"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("選擇語言 Choose Language");
        builder.setCancelable(false);
        builder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    setLocale("zh");
                    finish();
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                    firststart();
                }
                if (which == 1) {
                    setLocale("en");
                    finish();
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                    firststart();
                }
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void firststart() {
        SharedPreferences pref = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Setting", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
    }

    public void LoadLocale() {
        SharedPreferences preferences = getSharedPreferences("Setting", MODE_PRIVATE);
        String language = preferences.getString("My_Lang", "");
        setLocale(language);
    }



}
