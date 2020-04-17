package com.example.smartattendancesystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class AttendancePage extends AppCompatActivity {
    TextView welcomeMsg;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_page);

        fAuth=FirebaseAuth.getInstance();
        welcomeMsg=findViewById(R.id.msgWelcome);
        fStore=FirebaseFirestore.getInstance();
        userId= fAuth.getCurrentUser().getUid();

        //-----------------------------Display Name of Current User-------------------------------//
        DocumentReference documentReference= fStore.collection("Users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                assert documentSnapshot != null;
                welcomeMsg.setText(documentSnapshot.getString("Name"));
            }
        });

    }


    public void logOut(View v){
        fAuth.signOut();
        Intent logInPage=new Intent(AttendancePage.this, LogInPage.class);
        startActivity(logInPage);
        finish();
    }
}
