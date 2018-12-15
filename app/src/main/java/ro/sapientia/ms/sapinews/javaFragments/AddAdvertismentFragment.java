package ro.sapientia.ms.sapinews.javaFragments;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;

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

import ro.sapientia.ms.sapinews.R;
import ro.sapientia.ms.sapinews.javaActivities.LoginActivity;
import ro.sapientia.ms.sapinews.javaClasses.Advertisment;
import ro.sapientia.ms.sapinews.javaClasses.UriContainer;
import ro.sapientia.ms.sapinews.javaClasses.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static android.support.constraint.Constraints.TAG;


public class AddAdvertismentFragment extends Fragment {
    private ImageView firstPicture;
    private ImageView secondPicture;
    private ImageView next;
    private ImageView back;
    private ImageView addButton;
    private EditText title;
    private EditText shortDescription;
    private EditText longDescription;
    private EditText phoneNumber;
    private EditText location;
    private ProgressBar uploadPictureInProgress;
    private static final int PICK_IMAGE = 1;
    private UriContainer images = new UriContainer();
    private ArrayList<String> imagesString = new ArrayList<>();
    private ArrayList<String> advKeys = new ArrayList<>();
    private String TAG = "TAG_ADDADVERTISMENT";
   // private OnFragmentInteractionListener mListener;

    public AddAdvertismentFragment() {
        // Required empty public constructor
    }



    public static AddAdvertismentFragment newInstance() {
        AddAdvertismentFragment fragment = new AddAdvertismentFragment();
        Bundle args = new Bundle();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_add_advertisment, container, false);
        firstPicture = view.findViewById(R.id.imageFirst);
        secondPicture = view.findViewById(R.id.imageSecond);
        next = view.findViewById(R.id.nextButton);
        back = view.findViewById(R.id.previousButton);
        addButton = view.findViewById(R.id.addButton);
        title = view.findViewById(R.id.titleText);
        shortDescription = view.findViewById(R.id.shortDescription);
        longDescription = view.findViewById(R.id.longDescription);
        phoneNumber = view.findViewById(R.id.phoneNumber);
        location = view.findViewById(R.id.locationText);
        uploadPictureInProgress = view.findViewById(R.id.updatingPicture);
        uploadPictureInProgress.setVisibility(View.INVISIBLE);
        phoneNumber.setText(User.getInstance().getPhoneNumb());

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!images.isEmpty()){
                        Glide.with(getContext()).load(images.getNextImage()).into( firstPicture);
                        Glide.with(getContext()).load(images.getNextImage()).into( secondPicture);
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!images.isEmpty()){
                        Glide.with(getContext()).load(images.getPreviousImage()).into( firstPicture);
                        Glide.with(getContext()).load(images.getPreviousImage()).into( secondPicture);
                }
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });
        addButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int i;
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference databaseReference = database.getReference();
                final StorageReference storageRef = FirebaseStorage.getInstance().getReference("uploads/advertismentPics");
                final String key = databaseReference.push().getKey();

                if (isValidContent()) {
                    for(i = 0; i< images.getUri().size(); i++) {
                        //final int i_1 = i;
                        uploadPics(databaseReference, storageRef,key,i);
                    }
                    assert key != null;
                    Toast.makeText(getContext(), "Feltöltés: helyes", Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    Toast.makeText(getContext(), "Feltöltés: helytelen", Toast.LENGTH_SHORT).show();
                    return true;
                }
            }
        });
        if(!images.isEmpty()){
            refreshView();
        }

        return view;
    }

    public void uploadPics(final DatabaseReference databaseReference, final StorageReference storageRef,final String key,final int i){
        storageRef.child(Objects.requireNonNull(key)).child("adv" + i + ".jpg").putFile(images.getUri().get(i))
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //imagesString.add(imagesUri.getUri().get(i_1).toString());
                //Log.d(TAG,"UriSTRING: " + imagesUri.getUri().get(i_1).toString());
                uploadPictureInProgress.setVisibility(View.INVISIBLE);

                Toast.makeText(getContext(), "Image upload sucessfull.", Toast.LENGTH_SHORT).show();

                storageRef.child(Objects.requireNonNull(key)).child("adv"+i+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        //Log.d(TAG,"UriSTRING: " + imagesUri.getUri().get(0).toString());
                        imagesString.add(uri.toString());
                        //Log.d(TAG,imagesString.get(0));
                        //advKeys.add(key);
                        User.getInstance().setAdvKeysToArrayList(key);
                        databaseReference.child("advertisments").child(key).setValue(new Advertisment(imagesString,title.getText().toString(),shortDescription.getText().toString(),longDescription.getText().toString(), User.getInstance().getImageUrl(),0, User.getInstance().getPhoneNumb()));
                        //User.getInstance().setAdKeys(advKeys);
                        databaseReference.child("users").child(phoneNumber.getText().toString()).child("advertisments").setValue(User.getInstance().getAdKeys());
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(),"Image download failed from storage.",Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Image upload failed.", Toast.LENGTH_SHORT).show();
            }
        })
        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                uploadPictureInProgress.setVisibility(View.VISIBLE);
            }
        });
    }

    public boolean isValidContent(){
        if(isEmpty() && isValidDataInserted()){
            return true;
        }
        return false;
    }

    public boolean isValidDataInserted(){
        if(title.getText().toString().matches("[^*./\\}[{}?%^#@$!`'\"~])(=;>,<]*?") &&
            location.getText().toString().matches("[^*./\\}[{}?%^#@$!`'\"~])(=;>,<]*?") &&
                phoneNumber.getText().toString().matches("^[+][0-9]{10,13}$") ){
            return true;
        }
        return false;
    }

    public boolean isEmpty(){
        if(!images.isEmpty() &&
                !title.getText().toString().isEmpty() &&
                !shortDescription.getText().toString().isEmpty() &&
                !longDescription.getText().toString().isEmpty() &&
                !phoneNumber.getText().toString().isEmpty() &&
                !location.getText().toString().isEmpty()){
            return true;
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && data != null && data.getData() != null) {
            Uri uri = data.getData();
            if(!images.addUri(uri)){
                Toast.makeText(this.getContext(), "Nem tölthet fel több képet!",Toast.LENGTH_SHORT).show();
            }else{
                refreshView();
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void refreshView(){
        if(images.isEmpty()==false){
            Glide.with(getContext()).load(images.getCurrentImage()).into( firstPicture);
            Glide.with(getContext()).load(images.getNextImage()).into( secondPicture);
        }
    }




    public interface OnFragmentInteractionListener {
    }
}
