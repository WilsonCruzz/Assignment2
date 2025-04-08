package com.example.assignment2.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.assignment2.R;
import com.example.assignment2.databinding.ActivityRegisterBinding;
import com.example.assignment2.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Register extends AppCompatActivity {

    ActivityRegisterBinding binding;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference usersCollection = db.collection("Users");




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser(binding.registerEmail.getText().toString(), binding.registerPassword.getText().toString());
            }
        });

    }

    private void registerUser(String email, String password){

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Log.d("TAG", "createUserWithEmail:success");
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            if (firebaseUser != null) {
                                String uid = firebaseUser.getUid();
                                String email = firebaseUser.getEmail();
                                SaveUserToFirestore(uid, email);
                            }
                            Intent intentObj = new Intent(getApplicationContext(), Login.class);
                            startActivity(intentObj);
                            finish();
                        }else{
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Register.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private void SaveUserToFirestore(String uid, String email){
        UserModel user = new UserModel(uid, email);
        // Save the user data to Firestore under the "Users" collection with the user's UID as the document ID
        usersCollection.document(uid).set(user);

    }

}