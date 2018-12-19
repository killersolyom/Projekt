package ro.sapientia.ms.sapinews.javaFragments;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import ro.sapientia.ms.sapinews.R;
import ro.sapientia.ms.sapinews.javaActivities.MainActivity;
import ro.sapientia.ms.sapinews.javaActivities.MyAdvertismentsActivity;
import ro.sapientia.ms.sapinews.javaClasses.User;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;


public class ProfileFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ImageView profilePicture;
    private ImageView logOut;
    private EditText firstNameInput;
    private EditText lastNameInput;
    private TextView phoneNumber;
    private EditText emailInput;
    private EditText addressInput;
    private String TAG = "TAG_PROFILEFRAGMENT";
    public static final int PICK_IMAGE = 1;
    private String key="";
    private Uri uri;
    private ImageView myAdvertisments;
    private AlertDialog builderProgress=null;
    private String number;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference datebaseRef = database.getReference();
    private StorageReference storageRef = FirebaseStorage.getInstance().getReference("uploads/profilepics");

    private User user = User.getInstance();
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
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        profilePicture = view.findViewById(R.id.profilePicture);
        firstNameInput = view.findViewById(R.id.firstNameInput);
        lastNameInput = view.findViewById(R.id.lastNameInput);
        phoneNumber = view.findViewById(R.id.phoneNumberInput);
        emailInput = view.findViewById(R.id.emailInput);
        addressInput = view.findViewById(R.id.adressInput);

        number = getArguments().getString("phone");

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
                Intent intent = new Intent(getActivity(), MyAdvertismentsActivity.class);
                startActivity(intent);
            }
        });

        Log.d(TAG,"Kep URL: " + User.getInstance().getImageUrl());

        Glide.with(getActivity()).load(User.getInstance().getImageUrl()).into(profilePicture);

        getInformation();

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
        if (requestCode == PICK_IMAGE && data != null && data.getData() != null) {
            uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                //InputStream inputStream = getContext().getContentResolver().openInputStream(data.getData());
                //bitmap  = BitmapFactory.decodeStream(inputStream);
                uploadFile();
                profilePicture.setImageBitmap(bitmap);
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
            final StorageReference fileReference = storageRef.child(number).child("profilepicture" + "." + "jpg"); //getFileExtension(uri)
            fileReference.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getContext(),"Upload successful!",Toast.LENGTH_SHORT).show();
                    addPictureToUser();
                    builderProgress.dismiss();
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
                            ProgressBar progressBar = dialoglayout.findViewById(R.id.progressBar);
                            AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                            builder.setTitle("Kép feltöltése...");
                            builder.setView(dialoglayout);
                            progressBar.setProgress((int)progress);
                            if(builderProgress != null && builderProgress.isShowing()){
                                builderProgress.dismiss();
                                Log.d(TAG,"Ifen belul progress: " + progress);
                                progressBar.setProgress((int)progress);
                                builderProgress = builder.create();
                                builderProgress.setCancelable(false);
                                builderProgress.show();
                            }
                            else {
                                builderProgress = builder.create();
                                builderProgress.setCancelable(false);
                                builderProgress.show();
                            }
                        }
                    });
        }
        else{
            Toast.makeText(getContext(),"No file selected",Toast.LENGTH_SHORT).show();
        }
    }

    public void addPictureToUser(){
        final StorageReference fileReference = storageRef.child(number).child("profilepicture" + "." + "jpg");
        fileReference.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        datebaseRef.child("users").child(number).child("imageUrl").setValue(uri.toString());
                        User.getInstance().setImageUrl(uri.toString());
                        Log.d(TAG,"Userben kep URL " + User.getInstance().getImageUrl());
                        Glide.with(getActivity()).load(User.getInstance().getImageUrl()).into(profilePicture);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void getInformation(){

        firstNameInput.setText(user.getFirstName());
        lastNameInput.setText(user.getLastName());
        phoneNumber.setText(user.getPhoneNumb());
        emailInput.setText(user.getEmailAddress());
        addressInput.setText(user.getAddress());

    }


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
        //Log.d(TAG, "elmentem innen, key: " + key);
        writeUser(firstNameInput.getText().toString(),lastNameInput.getText().toString(),phoneNumber.getText().toString(),emailInput.getText().toString(),addressInput.getText().toString());
        //Log.d(TAG,"useremail: " + user.getEmailAddress());
        //mListener = null;
    }

    private void writeUser(String firstName, String lastName, String phoneNumber, String emailAddress, String address){
        //User user = new User(firstName,lastName,phoneNumber,emailAddress, address);
        user.setID(phoneNumber);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhoneNumb(phoneNumber);
        user.setEmailAddress(emailAddress);
        user.setAddress(address);
        user.setImageUrl(User.getInstance().getImageUrl());
        user.setAdKeys(User.getInstance().getAdKeys());

        datebaseRef.child("users").child(phoneNumber).setValue(user);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
