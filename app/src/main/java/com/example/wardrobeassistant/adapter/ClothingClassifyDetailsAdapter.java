package com.example.wardrobeassistant.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wardrobeassistant.db.entity.Clothing;

import java.util.List;

public class ClothingClassifyDetailsAdapter extends RecyclerView.Adapter<ClothingClassifyDetailsAdapter.ClothingClassifyDetailViewHolder> {
    private List<Clothing> clothings;

    public ClothingClassifyDetailsAdapter() {

    }

    @NonNull
    @Override
    public ClothingClassifyDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ClothingClassifyDetailViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ClothingClassifyDetailViewHolder extends RecyclerView.ViewHolder {

        public ClothingClassifyDetailViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
