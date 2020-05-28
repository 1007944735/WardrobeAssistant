package com.example.wardrobeassistant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wardrobeassistant.R;
import com.example.wardrobeassistant.db.entity.Clothing;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView2;

import java.util.List;

public class ClothingSelectedAdapter extends RecyclerView.Adapter<ClothingSelectedAdapter.ClothingSelectedViewHolder> {
    private List<Clothing> clothing;
    private Context mContext;
    private OnItemClickListener listener;

    public ClothingSelectedAdapter(Context context, List<Clothing> clothing) {
        this.clothing = clothing;
        this.mContext = context;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ClothingSelectedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ClothingSelectedViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clothing_selected, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ClothingSelectedViewHolder holder, final int position) {
        Glide.with(mContext).load(clothing.get(position).getClothingImageUrl()).into(holder.ivClothingImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.OnItemClick(clothing.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return clothing.size();
    }

    class ClothingSelectedViewHolder extends RecyclerView.ViewHolder {
        QMUIRadiusImageView2 ivClothingImage;
        View itemView;

        public ClothingSelectedViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            ivClothingImage = itemView.findViewById(R.id.iv_clothing_image);
        }
    }

    public interface OnItemClickListener {
        void OnItemClick(Clothing clothing);
    }
}
