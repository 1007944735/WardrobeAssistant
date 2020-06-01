package com.example.wardrobeassistant.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.wardrobeassistant.R;
import com.example.wardrobeassistant.activity.ClothingDetailsActivity;
import com.example.wardrobeassistant.db.entity.Suit;
import com.qmuiteam.qmui.widget.QMUIPagerAdapter;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView2;

public class SuitViewPagerAdapter extends QMUIPagerAdapter {
    private Suit suit;

    public SuitViewPagerAdapter(Suit suit) {
        this.suit = suit;
    }

    @NonNull
    @Override
    protected Object hydrate(@NonNull ViewGroup container, int position) {
        return new ItemView(position, container.getContext());
    }

    @Override
    protected void populate(@NonNull ViewGroup container, @NonNull Object item, int position) {
        ItemView itemView = (ItemView) item;
        itemView.setData(suit);
        container.addView(itemView);
    }

    @Override
    protected void destroy(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    static class ItemView extends FrameLayout {
        private TextView tvType;
        private QMUIRadiusImageView2 ivClothingImage;
        private int postion;
        private Context mContext;

        public ItemView(int position, Context context) {
            super(context);
            this.postion = position;
            this.mContext = context;
            View view = LayoutInflater.from(context).inflate(R.layout.layout_suit_page, null);
            tvType = view.findViewById(R.id.tv_type);
            ivClothingImage = view.findViewById(R.id.iv_clothing_image);
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            addView(view, lp);
        }

        public void setData(final Suit suit) {
            switch (postion) {
                case 0:
                    tvType.setText("外套");
                    Glide.with(mContext).load(suit.getSuitOvercoat().getClothingImageUrl()).into(ivClothingImage);
                    ivClothingImage.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, ClothingDetailsActivity.class);
                            intent.putExtra("clothing", suit.getSuitOvercoat());
                            mContext.startActivity(intent);
                        }
                    });
                    break;
                case 1:
                    tvType.setText("上衣");
                    Glide.with(mContext).load(suit.getSuitOuterwear().getClothingImageUrl()).into(ivClothingImage);
                    ivClothingImage.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, ClothingDetailsActivity.class);
                            intent.putExtra("clothing", suit.getSuitOuterwear());
                            mContext.startActivity(intent);
                        }
                    });
                    break;
                case 2:
                    tvType.setText("裤子");
                    Glide.with(mContext).load(suit.getSuitTrousers().getClothingImageUrl()).into(ivClothingImage);
                    ivClothingImage.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, ClothingDetailsActivity.class);
                            intent.putExtra("clothing", suit.getSuitTrousers());
                            mContext.startActivity(intent);
                        }
                    });
                    break;
                case 3:
                    tvType.setText("鞋子");
                    Glide.with(mContext).load(suit.getSuitShoes().getClothingImageUrl()).into(ivClothingImage);
                    ivClothingImage.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, ClothingDetailsActivity.class);
                            intent.putExtra("clothing", suit.getSuitShoes());
                            mContext.startActivity(intent);
                        }
                    });
                    break;
            }
        }
    }
}
