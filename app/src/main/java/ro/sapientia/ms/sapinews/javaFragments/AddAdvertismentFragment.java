package ro.sapientia.ms.sapinews.javaFragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import ro.sapientia.ms.sapinews.R;
import ro.sapientia.ms.sapinews.javaClasses.UriContainer;

import java.io.FileNotFoundException;
import java.io.IOException;


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
    private static final int PICK_IMAGE = 1;
    private UriContainer images = new UriContainer();

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
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!images.isEmpty()){
                    try {
                        Bitmap bitmapFirst = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), images.getNextImage());
                        Bitmap bitmapSecond = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), images.getNextImage());
                        firstPicture.setImageBitmap(bitmapFirst);
                        secondPicture.setImageBitmap(bitmapSecond);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!images.isEmpty()){
                    try {
                        Bitmap bitmapFirst = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), images.getPreviousImage());
                        Bitmap bitmapSecond = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), images.getPreviousImage());
                        firstPicture.setImageBitmap(bitmapFirst);
                        secondPicture.setImageBitmap(bitmapSecond);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
                if (isValidContent()) {
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

    public boolean isValidContent(){
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
                images.addUri(uri);
                refreshView();
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
            try {
                Bitmap bitmapFirst = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), images.getCurrentImage());
                Bitmap bitmapSecond = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), images.getNextImage());
                firstPicture.setImageBitmap(bitmapFirst);
                secondPicture.setImageBitmap(bitmapSecond);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




    public interface OnFragmentInteractionListener {
    }
}
