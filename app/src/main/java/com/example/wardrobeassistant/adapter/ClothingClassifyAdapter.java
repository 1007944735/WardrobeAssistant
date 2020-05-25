package com.example.wardrobeassistant.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wardrobeassistant.R;
import com.example.wardrobeassistant.util.StringUtils;

public class ClothingClassifyAdapter extends RecyclerView.Adapter<ClothingClassifyAdapter.ClothingClassifyViewHolder> {
    private String[] classifies;
    private int selectPos = 0;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;
    private final int[] classifyLogos = {
            R.mipmap.ico_clothing_overcoat,
            R.mipmap.ico_clothing_outerwear,
            R.mipmap.ico_clothing_trousers,
            R.mipmap.ico_clothing_shoes
    };

    public ClothingClassifyAdapter(Context context) {
        mContext = context;
        classifies = context.getResources().getStringArray(R.array.clothingClassify);
    }

    @NonNull
    @Override
    public ClothingClassifyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ClothingClassifyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clothing_classify, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ClothingClassifyViewHolder holder, final int position) {
        holder.ivClassifyLogo.setImageResource(classifyLogos[position]);
        holder.tvClassifyName.setText(classifies[position]);
        holder.itemView.setBackgroundColor(position == selectPos ? mContext.getResources().getColor(R.color.colorShadow) : Color.WHITE);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectPos != position) {
                    selectPos = position;
                    notifyDataSetChanged();
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(StringUtils.clothingTypeToDb(classifies[position]));
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return classifies.length;
    }

    class ClothingClassifyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivClassifyLogo;
        TextView tvClassifyName;
        View itemView;

        public ClothingClassifyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            ivClassifyLogo = itemView.findViewById(R.id.iv_classify_logo);
            tvClassifyName = itemView.findViewById(R.id.tv_classify_name);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(String classifyType);
    }
}
