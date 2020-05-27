package com.example.wardrobeassistant.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wardrobeassistant.R;
import com.example.wardrobeassistant.db.entity.Clothing;
import com.example.wardrobeassistant.db.entity.Suit;
import com.example.wardrobeassistant.util.TimeUtils;

import java.util.ArrayList;
import java.util.List;

public class ClothingSuitAdapter extends RecyclerView.Adapter<ClothingSuitAdapter.ClothingSuitViewHolder> {
    private List<Suit> suits;
    private OnItemClickListener listener;

    public ClothingSuitAdapter() {
        suits = new ArrayList<>();
    }

    public void addAll(List<Suit> suits) {
        if (suits == null || suits.isEmpty()) {
            return;
        }
        this.suits.addAll(suits);
        notifyDataSetChanged();
    }

    public void clear() {
        suits.clear();
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ClothingSuitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ClothingSuitViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clothing_suit, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ClothingSuitViewHolder holder, int position) {
        Suit suit = suits.get(position);
        holder.tvSuitOrder.setText(position + 1 + "");
        holder.tvSuitName.setText(suit.getSuitName());
        holder.tvSuitCreateTime.setText(TimeUtils.format(suit.getSuitCreateTime()));
        holder.tvPreset.setVisibility(suit.getSuitPreset() ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return suits.size();
    }

    class ClothingSuitViewHolder extends RecyclerView.ViewHolder {
        TextView tvSuitOrder;
        TextView tvSuitName;
        TextView tvSuitCreateTime;
        TextView tvPreset;
        View itemView;

        public ClothingSuitViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            tvSuitOrder = itemView.findViewById(R.id.tv_suit_order);
            tvSuitName = itemView.findViewById(R.id.tv_suit_name);
            tvSuitCreateTime = itemView.findViewById(R.id.tv_suit_create_time);
            tvPreset = itemView.findViewById(R.id.tv_preset);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Clothing clothing);
    }
}
