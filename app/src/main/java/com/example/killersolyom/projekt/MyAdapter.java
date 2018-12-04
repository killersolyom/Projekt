package com.example.killersolyom.projekt;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.RecyclerViewHolder> {
    ImageView advertismentImage;
    ImageView profilePicture;
    TextView title;
    TextView details;
    TextView counter;

    private ArrayList<Advertisment> advertisments = new ArrayList<>();
    private Context context;

    public MyAdapter(Context context, ArrayList<Advertisment> advertisment) {
        this.context = context;
        advertisments = advertisment;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_advertisment, parent, false));
    }

    @Override
    public void onBindViewHolder( final RecyclerViewHolder holder, int position) {

        try {
            Advertisment advertisment  = advertisments.get(position);
            holder.title.setText(advertisment.getAdvertismentTitle());
            holder.details.setText(advertisment.getAdvertismentDetails());
            holder.counter.setText(advertisment.getViewedCounter()+"");

            Glide.with(context).load("https://thumbs-prod.si-cdn.com/drt63Nyhy9WUiHzpRSm2ABjnMcA=/800x600/filters:no_upscale()/https://public-media.smithsonianmag.com/filer/Three-toed-sloth-Panama-631.jpg").diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.advertismentPicture);


            //Bitmap advImage = MediaStore.Images.Media.getBitmap(context.getContentResolver(), advertisment.getAdvertismentImage());
            //Glide.with(context).load(advImage).into(holder.advertismentPicture);
           /* Bitmap profileImage = MediaStore.Images.Media.getBitmap(context.getContentResolver(), advertisment.getAdvertismentProfilePicture());
            holder.advertismentPicture.setImageBitmap(advImage);
            holder.profilePicture.setImageBitmap(profileImage);*/

            //Toast.makeText(context,"Adapter Size: "+advertisments.size()+"",Toast.LENGTH_SHORT).show();
            //holder.itemView.setClickListener(...);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context,"Adapter Baj!",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public int getItemCount() {
        return advertisments.size();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private ImageView advertismentPicture;
        private ImageView profilePicture;
        private TextView title;
        private TextView details;
        private TextView counter;



        RecyclerViewHolder(View itemView) {
            super(itemView);
            advertismentPicture = itemView.findViewById(R.id.AdvertismentImage);
            profilePicture = itemView.findViewById(R.id.ProfilePicture);
            title = itemView.findViewById(R.id.AdvertismentTitle);
            details = itemView.findViewById(R.id.AdvertismentDetails);
            counter = itemView.findViewById(R.id.ViewCounter);
        }
    }



}