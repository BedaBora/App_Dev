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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegistrationPage extends AppCompatActivity {

    EditText name;                              //                                          //
    EditText email;                             //    Get information about the user        //
    EditText password;                          //    that will be sent to firebase         //
    EditText confPassword;                     //                                          //
    ProgressBar pBar;
    Button btnRegister;

    FirebaseAuth fAuth;                         //    Communicate with firebase            //
    FirebaseFirestore fStore;                   //    Communicate with database            //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        name=findViewById(R.id.getName);
        email=findViewById(R.id.getEmail);
        password=findViewById(R.id.getPassword);
        confPassword=findViewById(R.id.getConfPassword);
        btnRegister=findViewById(R.id.btn_signUp);
        pBar=findViewById(R.id.progressBar2);


        fAuth=FirebaseAuth.getInstance();          // Current Instance of the database            //
        fStore=FirebaseFirestore.getInstance();    //                                             //


        //-----------------------------------Get user data----------------------------------------//
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String reg_name= name.getText().toString();                //                      //
                final String reg_email=email.getText().toString().trim();               // Store all data into  //
                String reg_password=password.getText().toString().trim();         //   string variables   //
                String conf_password=confPassword.getText().toString().trim();    //                      //

                //---------------------------Check if name field is empty-------------------------//
                if(TextUtils.isEmpty(reg_name)){
                    name.setError("Enter your name.");
                    return;
                }

                //---------------------------Check if email field is empty------------------------//
                if(TextUtils.isEmpty(reg_email)){
                    email.setError("Email is required.");
                    return;
                }

                //-------------------------Check if password field is empty-----------------------//
                if(TextUtils.isEmpty(reg_password)){
                    password.setError("Password is required.");
                    return;
                }

                if(TextUtils.isEmpty(conf_password) || !conf_password.equals(reg_password)){
                    confPassword.setError("Password does not match.");
                    return;
                }

                pBar.setVisibility(View.VISIBLE);

                //------------------------Register user to firebase-------------------------------//
                fAuth.createUserWithEmailAndPassword(reg_email, reg_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            pBar.setVisibility(View.INVISIBLE);

//                            DocumentReference docRef=fStore.collection("Users").document(Objects.requireNonNull(fAuth.getCurrentUser()).getUid());

                            Map<String,Object> user= new HashMap<>();
                            user.put("Name", reg_name);
                            user.put("Email",reg_email);
                            fStore.collection("Users").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(RegistrationPage.this, "User created", Toast.LENGTH_SHORT).show();
                                }
                            });

                            Intent loginPage= new Intent(RegistrationPage.this, LogInPage.class);
                            startActivity(loginPage);
                            finish();
                        }
                        else{
                            Toast.makeText(RegistrationPage.this, "Error: "+ Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            pBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });


            }
        });


    }

    //--------------------Connect login activity to registration activity-------------------------//
    public void goToLogIn(View v){
        Intent loginPage= new Intent(RegistrationPage.this, LogInPage.class);
        startActivity(loginPage);

    }
}
