package com.example.wardrobeassistant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.wardrobeassistant.Constant;
import com.example.wardrobeassistant.R;
import com.example.wardrobeassistant.db.DbManager;
import com.example.wardrobeassistant.db.entity.Clothing;
import com.qmuiteam.qmui.arch.QMUIActivity;
import com.qmuiteam.qmui.widget.QMUITopBar;

import java.util.List;

public class MainActivity extends BaseActivity {
    private CardView cvMyClothing;
    private CardView cvSuit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
    }

    private void initView() {
        cvMyClothing = findViewById(R.id.cv_my_clothing);
        cvSuit = findViewById(R.id.cv_suit);

    }


    private void initListener() {
        cvMyClothing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MyClothingListActivity.class));
            }
        });

        cvSuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
