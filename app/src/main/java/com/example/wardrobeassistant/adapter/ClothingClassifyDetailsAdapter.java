package com.example.wardrobeassistant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wardrobeassistant.R;
import com.example.wardrobeassistant.activity.ClothingDetailsActivity;
import com.example.wardrobeassistant.db.entity.Clothing;

import java.util.ArrayList;
import java.util.List;

public class ClothingClassifyDetailsAdapter extends RecyclerView.Adapter<ClothingClassifyDetailsAdapter.ClothingClassifyDetailViewHolder> {
    private List<Clothing> clothings;
    private Context mContext;
    private OnItemClickListener listener;

    public ClothingClassifyDetailsAdapter(Context context) {
        clothings = new ArrayList<>();
        this.mContext = context;
    }

    public void addAll(List<Clothing> clothings) {
        if (clothings == null || clothings.isEmpty()) {
            return;
        }
        this.clothings.addAll(clothings);
        notifyDataSetChanged();
    }

    public void clear() {
        clothings.clear();
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ClothingClassifyDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ClothingClassifyDetailViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clothing_classify_detail, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ClothingClassifyDetailViewHolder holder, int position) {
        final Clothing clothing = clothings.get(position);
        holder.tvClothingName.setText(clothing.getClothingName());
        Glide.with(mContext).load(clothing.getClothingImageUrl()).into(holder.ivClothingImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(clothing);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return clothings.size();
    }

    class ClothingClassifyDetailViewHolder extends RecyclerView.ViewHolder {
        ImageView ivClothingImage;
        TextView tvClothingName;
        View itemView;

        public ClothingClassifyDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            ivClothingImage = itemView.findViewById(R.id.iv_clothing_image);
            tvClothingName = itemView.findViewById(R.id.tv_clothing_name);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Clothing clothing);
    }
}
