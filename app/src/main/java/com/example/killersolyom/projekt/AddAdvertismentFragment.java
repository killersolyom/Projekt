package com.example.killersolyom.projekt;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
    private BitmapContainer images = new BitmapContainer();

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
                    firstPicture.setImageBitmap(images.getNextImage());
                    secondPicture.setImageBitmap(images.getNextImage());
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!images.isEmpty()){
                    firstPicture.setImageBitmap(images.getPreviousImage());
                    secondPicture.setImageBitmap(images.getPreviousImage());
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
            firstPicture.setImageBitmap(images.getCurrentImage());
            secondPicture.setImageBitmap(images.getNextImage());
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
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                images.addUri(bitmap);
                refreshView();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public boolean alert(){
        final boolean[] temp = new boolean[1];
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setMessage("Fel szeretné tölteni a hírdetést?");
        builder.setCancelable(false);

        builder.setPositiveButton("Igen", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getContext(),"igen",Toast.LENGTH_SHORT).show();
                temp[0] = true;
                dialog.cancel();
            }
        });
        builder.setNegativeButton("Nem", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getContext(),"nem",Toast.LENGTH_SHORT).show();
                temp[0] = false;
                dialog.cancel();
            }
        });
        builder.setNeutralButton("Mégsem", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getContext(),"mégsem",Toast.LENGTH_SHORT).show();
                temp[0] = true;
                dialog.cancel();
            }
        });
        super.onDetach();
        AlertDialog alert11 = builder.create();
        alert11.show();
        return temp[0];
    }

    @Override
    public void onDetach() {
            super.onDetach();
    }

    public void refreshView(){
        if(images.isEmpty()==false){
            firstPicture.setImageBitmap(images.getCurrentImage());
            secondPicture.setImageBitmap(images.getNextImage());
        }
    }



    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public interface OnFragmentInteractionListener {
    }
}
