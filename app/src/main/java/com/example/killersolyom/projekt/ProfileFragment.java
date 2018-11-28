package com.example.killersolyom.projekt;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;


public class ProfileFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private ImageView profilePicture;
    private ImageView logOut;
    private EditText firstNameInput;
    private EditText lastNameInput;
    private EditText phoneNumber;
    private EditText emailInput;
    private EditText addressInput;
    private String TAG = "TAG_PROFILEFRAGMENT";
    public static final int PICK_IMAGE = 1;
    private String key="";
    private Uri uri;
    ImageView myAdvertisments;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference datebaseRef = database.getReference();
    private StorageReference storageRef = FirebaseStorage.getInstance().getReference("uploads");

    private User user = new User();
    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }


    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        profilePicture = view.findViewById(R.id.profilePicture);
        profilePicture.setBackgroundResource(R.drawable.shrek);
        firstNameInput = view.findViewById(R.id.firstNameInput);
        lastNameInput = view.findViewById(R.id.lastNameInput);
        phoneNumber = view.findViewById(R.id.phoneNumberInput);
        emailInput = view.findViewById(R.id.emailInput);
        addressInput = view.findViewById(R.id.adressInput);

        String number = getArguments().getString("phone");

        getInformation(number);

        logOut = view.findViewById(R.id.logoutView);
        myAdvertisments = view.findViewById(R.id.myAdvertisments);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        myAdvertisments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyAdvertisments.class);
                startActivity(intent);
            }
        });

        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

        return view;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        //Bitmap bitmap = null;
        if (requestCode == PICK_IMAGE && data != null && data.getData() != null) {
            //TODO: action
            uri = data.getData();
            Log.d(TAG,"onActivityResult");
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                //InputStream inputStream = getContext().getContentResolver().openInputStream(data.getData());
                //bitmap  = BitmapFactory.decodeStream(inputStream);
                profilePicture.setImageBitmap(bitmap);
                uploadFile();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    private void uploadFile(){
        if (uri!=null){
            final StorageReference fileReference = storageRef.child(System.currentTimeMillis() + "." + getFileExtension(uri));
            fileReference.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getContext(),"Upload successful!",Toast.LENGTH_SHORT).show();
                    Upload upload = new Upload ("profilePicture",fileReference.getDownloadUrl().toString());
                    String uploadId = datebaseRef.child("pictures").push().getKey();
                    datebaseRef.child("pictures").child(uploadId).setValue(upload);
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {


                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            LayoutInflater inflater = getLayoutInflater();
                            View dialoglayout = inflater.inflate(R.layout.custom_progressbar, null);
                            final ProgressBar progressBar = dialoglayout.findViewById(R.id.progressBar);
                            AlertDialog.Builder builderProgress=new AlertDialog.Builder(getContext());

                            builderProgress.setTitle("Kép feltöltése...");

                            //if(progressBar.getParent()!=null)
                            //    ((ViewGroup)progressBar.getParent()).removeView(progressBar); // <- fix
                           // dialoglayout.addView(progressBar);

                            builderProgress.setView(dialoglayout);

                            progressBar.setProgress((int)progress);

                            /*builderProgress.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //Toast.makeText(LoginActivity.this,"Get Started!",Toast.LENGTH_LONG).show();

                                    dialog.dismiss();
                                }
                            });*/
                            builderProgress.create();
                            builderProgress.show();

                        }
                    });
        }
        else{
            Toast.makeText(getContext(),"No file selected",Toast.LENGTH_SHORT).show();
        }
    }

    public void getInformation(final String  number){
        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference datebaseRef = database.getReference();
        datebaseRef.child("users").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (dataSnapshot.exists()){
                    for(DataSnapshot value : dataSnapshot.getChildren()){
                        Log.d(TAG,value.getKey());
                        user = value.getValue(User.class);
                        //Log.d(TAG, "getInformation : " + user.toString());
                        if(user.getPhoneNumb().equals(number) && value.getKey().equals(user.getID().toString())){
                            key = user.getID().toString();
                            Log.d(TAG,"key: " + key);
                            firstNameInput.setText(user.getFirstName());
                            lastNameInput.setText(user.getLastName());
                            phoneNumber.setText(user.getPhoneNumb());
                            emailInput.setText(user.getEmailAddress());
                            addressInput.setText(user.getAddress());
                            break;
                            //Log.d(TAG, "checkUser if status: " + status);
                        }
                    }

                    //Log.d(TAG, "checkUser ifen kivul status: " + status);

                } else {
                    Log.d(TAG, "dataSnapshot is not extist.");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "elmentem innen, key: " + key);
        writeUser(key,firstNameInput.getText().toString(),lastNameInput.getText().toString(),phoneNumber.getText().toString(),emailInput.getText().toString(),addressInput.getText().toString());
        Log.d(TAG,"useremail: " + user.getEmailAddress());
        mListener = null;
    }

    private void writeUser(String userId, String firstName, String lastName, String phoneNumber, String emailAddress, String address){
        //User user = new User(firstName,lastName,phoneNumber,emailAddress, address);
        user.setID(Integer.parseInt(userId));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhoneNumb(phoneNumber);
        user.setEmailAddress(emailAddress);
        user.setAddress(address );
        datebaseRef.child("users").child(userId).setValue(user);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
