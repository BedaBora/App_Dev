package com.example.smartattendancesystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LogInPage extends AppCompatActivity {

    EditText email;                     //          User login information          //
    EditText password;                  //                                          //

    Button login;
    ProgressBar pBar;

    FirebaseAuth fAuth;                         //        Communicate with firebase            //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_page);

        email= findViewById(R.id.enterEmail);
        password=findViewById(R.id.enterPassword);
        login= findViewById(R.id.btn_signIn);
        pBar=findViewById(R.id.loginProgress);

        fAuth=FirebaseAuth.getInstance();

        //------------------If user is already loggin in, go to main page-------------------------//
        if(fAuth.getCurrentUser()!=null){
            Intent attendancePage= new Intent(LogInPage.this, AttendancePage.class );
            startActivity(attendancePage);
            finish();
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String log_email=email.getText().toString();
                String log_password=password.getText().toString();

                //---------------------------Check if email field is empty------------------------//
                if(TextUtils.isEmpty(log_email)){
                    email.setError("Email is required.");
                    return;
                }

                //-------------------------Check if password field is empty-----------------------//
                if(TextUtils.isEmpty(log_password)){
                    password.setError("Password is required.");
                    return;
                }

                pBar.setVisibility(View.VISIBLE);

                //------------------------Authenticate the User-----------------------------------//
                fAuth.signInWithEmailAndPassword(log_email,log_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            pBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(LogInPage.this, "Logged in successfully.", Toast.LENGTH_SHORT).show();
                            Intent attendancePage= new Intent(LogInPage.this, AttendancePage.class);
                            startActivity(attendancePage);
                            finish();
                        }

                        else{
                            Toast.makeText(LogInPage.this, "Error: "+ Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            pBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        });

    }

    //--------------------Connect registration activity to login activity-------------------------//
    public void goToRegistration(View v){
        Intent registrationPage= new Intent(LogInPage.this, RegistrationPage.class );
        startActivity(registrationPage);

    }
}
