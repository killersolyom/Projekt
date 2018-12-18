package ro.sapientia.ms.sapinews.javaActivities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ro.sapientia.ms.sapinews.R;
import ro.sapientia.ms.sapinews.javaClasses.User;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

import static android.support.v4.content.ContextCompat.startActivity;

public class SignUpActivity extends AppCompatActivity {

    EditText phoneNumber;
    EditText firstName;
    EditText lastName;
    Button signUp;
    String TAG = "TAG_SIGNUP";
    private String verificationId;
    private FirebaseAuth mAuth;
    String number;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference users = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        signUp = findViewById(R.id.signUpButton);
        phoneNumber = findViewById(R.id.phoneNumberInput);
        firstName = findViewById(R.id.firstNameInput);
        lastName = findViewById(R.id.lastNameInput);

        Intent i = getIntent();
        String phoneNumberNumber = i.getStringExtra("phoneNumber");
        phoneNumber.setText(phoneNumberNumber);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = phoneNumber.getText().toString().trim();
                if(number.isEmpty() || number.length()<10){
                    phoneNumber.setError("Nem érvényes telefonszám");
                    phoneNumber.requestFocus();
                    return;
                }

                LayoutInflater inflater = getLayoutInflater();
                View dialoglayout = inflater.inflate(R.layout.custom_aleartdialog, null);

                AlertDialog.Builder builder;

                builder = new AlertDialog.Builder(SignUpActivity.this);

                builder.setTitle("Írja be a kódot");

                builder.setView(dialoglayout);

                final EditText editCode = dialoglayout.findViewById(R.id.codeEdit);

                builder.setPositiveButton("Check", new DialogInterface.OnClickListener() {
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
                            Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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

                builder.create();
                sendVerificationCode(number);

                if(!((Activity) SignUpActivity.this).isFinishing()){
                    builder.show();
                }
            }
        });
    }

    private void writeNewUser(String firstName, String lastName){
        User.getInstance().setID(number);
        User.getInstance().setEmailAddress("");
        User.getInstance().setAddress("");
        User.getInstance().setLastName(lastName);
        User.getInstance().setFirstName(firstName);
        User.getInstance().setPhoneNumb(number);

        users.child("users").child(number).setValue(User.getInstance());
    }
    private void verifyCode(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId,code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        Log.d(TAG,"Sign in credintials. ");
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            writeNewUser(firstName.getText().toString(),lastName.getText().toString());
                            Intent intent = new Intent(SignUpActivity.this,MainScreenActivity.class); //ide mainscreen
                            intent.putExtra("phoneNumber",number);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(SignUpActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void sendVerificationCode(String number){
        Log.d(TAG, "Send verification code to: " + number);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(number,60,TimeUnit.SECONDS,TaskExecutors.MAIN_THREAD,mCallBack);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack
            = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            Log.d(TAG,"Code sent.");
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

}
