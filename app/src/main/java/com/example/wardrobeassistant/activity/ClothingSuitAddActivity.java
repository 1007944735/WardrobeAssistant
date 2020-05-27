package com.example.wardrobeassistant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.example.wardrobeassistant.R;
import com.example.wardrobeassistant.db.entity.Clothing;
import com.example.wardrobeassistant.db.entity.Suit;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView2;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;

import java.util.List;

public class ClothingSuitAddActivity extends BaseActivity {
    private QMUITopBarLayout topBar;
    private QMUIRadiusImageView2 ivOvercoat;
    private QMUIRadiusImageView2 ivOuterwear;
    private QMUIRadiusImageView2 ivTrousers;
    private QMUIRadiusImageView2 ivShoes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothing_suit_add);
        initView();
        initTopBar();
        initListener();
    }

    private void initView() {
        topBar = findViewById(R.id.topBar);
        ivOvercoat = findViewById(R.id.iv_overcoat);
        ivOuterwear = findViewById(R.id.iv_outerwear);
        ivTrousers = findViewById(R.id.iv_trousers);
        ivShoes = findViewById(R.id.iv_shoes);
    }

    private void initTopBar() {
        topBar.setTitle("添加套装");
        topBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        topBar.addRightTextButton("完成", R.id.clothing_add_finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    private void initListener() {
        ivOvercoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ivOuterwear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ivTrousers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ivShoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
