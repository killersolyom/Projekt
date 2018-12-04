package com.example.killersolyom.projekt;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthSettings;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    TextView have_account;
    Button login;
    EditText phone_PhoneNum;
    String TAG = "TAG_LOGIN";
    String number;
    private String verificationId;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        have_account = findViewById(R.id.have_account);
        login = findViewById(R.id.btn_signIn);
        phone_PhoneNum = findViewById(R.id.phone_PhoneNum);
        phone_PhoneNum.setText("+40");

        have_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference users = database.getReference();
            @Override
            public void onClick(View v) {
                number = phone_PhoneNum.getText().toString().trim();
                if(number.isEmpty() || number.length()<10){
                    phone_PhoneNum.setError("Nem érvényes telefonszám");
                    phone_PhoneNum.requestFocus();

                    /*Intent intent = new Intent(LoginActivity.this,MainScreenActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);*/

                    return;
                }

                users.child("users").addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        boolean status = false;
                        if (dataSnapshot.exists()){

                            for(DataSnapshot value : dataSnapshot.getChildren()){
                                User user = value.getValue(User.class);
                                if(user.getPhoneNumb().equals(phone_PhoneNum.getText().toString()) && value.getKey().equals(user.getID().toString())){
                                    status = true;
                                    break;
                                    //Log.d(TAG, "checkUser if status: " + status);
                                }
                            }

                            if (status){
                                //Log.d(TAG, "letezik status: " + status);
                                LayoutInflater inflater = getLayoutInflater();
                                View dialoglayout = inflater.inflate(R.layout.custom_aleartdialog, null);

                                AlertDialog.Builder builderLogin;

                                builderLogin = new AlertDialog.Builder(LoginActivity.this);

                                builderLogin.setTitle("Írja be a kódot");

                                builderLogin.setView(dialoglayout);

                                final EditText editCode = dialoglayout.findViewById(R.id.codeEdit);


                                builderLogin.setPositiveButton("Check", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //Toast.makeText(LoginActivity.this,"Get Started!",Toast.LENGTH_LONG).show();

                                        String code="";
                                        try{
                                            if(editCode!=null){
                                                code = editCode.getText().toString();
                                            }
                                        }
                                        catch (Exception e){
                                            Log.d(TAG,"Editcode is null");
                                            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }

                                        if(code.isEmpty() || code.length() <6){
                                            editCode.setError("Írja be a kódot!");
                                            editCode.requestFocus();
                                            return;
                                        }
                                        Log.d(TAG,"Code: " + code);
                                        verifyCode(code);
                                        //dialog.dismiss();
                                    }
                                });
                                builderLogin.create();
                                sendVerificationCode(number);
                                if(!((Activity) LoginActivity.this).isFinishing())
                                {
                                    builderLogin.show();
                                }

                            }
                            else{
                                //Log.d(TAG, "nem letezik status: " + status);
                                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                                intent.putExtra("phoneNumber",number);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }

                            //Log.d(TAG, "checkUser ifen kivul status: " + status);

                        } else {
                            Log.d(TAG, "dataSnapshot is not extist.");
                            status = false;
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
            }
        });


    }

    private void verifyCode(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId,code);
        signInWithCredential(credential);
    }


    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(LoginActivity.this,MainScreenActivity.class);
                            intent.putExtra("phoneNumber",number);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(LoginActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void sendVerificationCode(String number){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(number,60,TimeUnit.SECONDS,TaskExecutors.MAIN_THREAD,mCallBack);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack
            = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if(code != null){
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
