package ro.sapientia.ms.sapinews.javaClasses;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import ro.sapientia.ms.sapinews.javaActivities.AdvertismentDetailActivity;
import ro.sapientia.ms.sapinews.javaActivities.MyAdvertismentDetailActivity;
import ro.sapientia.ms.sapinews.R;

import java.util.ArrayList;

import static android.support.v4.content.ContextCompat.startActivity;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.RecyclerViewHolder> {
    ImageView advertismentImage;
    ImageView profilePicture;
    TextView title;
    TextView details;
    TextView counter;

    private ArrayList<Advertisment> advertisments = new ArrayList<>();
    private Context context;
    private String name = "";

    public MyAdapter(Context context, ArrayList<Advertisment> advertisment, String name) {
        this.context = context;
        advertisments = advertisment;
        this.name = name;
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
            final Advertisment advertisment  = advertisments.get(position);
            holder.title.setText(advertisment.getAdvertismentTitle());
            holder.details.setText(advertisment.getAdvertismentDetails());
            holder.counter.setText(advertisment.getViewedCounter()+"");
            Glide.with(context).load(advertisment.getAdvertismentImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.advertismentPicture);
            Glide.with(context).load(advertisment.getAdvertismentProfilePicture()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.profilePicture);
            holder.advertismentPicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        if(name.equals("GlobalAdvertisment")){
                            Intent intent = new Intent(context, AdvertismentDetailActivity.class);
                            intent.putExtra("Title",advertisment.getAdvertismentTitle());
                            startActivity(context,intent,null);

                        }else if(name.equals("MyAdvertisment")){
                            Intent intent = new Intent(context, MyAdvertismentDetailActivity.class);
                            intent.putExtra("Title",advertisment.getAdvertismentTitle());
                            startActivity(context,intent,null);
                        }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context,"Adapter Baj!",Toast.LENGTH_LONG).show();
        }

    }


    public void erase(){
        advertisments.clear();
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