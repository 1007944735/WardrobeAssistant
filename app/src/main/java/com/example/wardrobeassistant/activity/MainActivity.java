package com.example.wardrobeassistant.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.wardrobeassistant.Constant;
import com.example.wardrobeassistant.R;
import com.example.wardrobeassistant.db.DbManager;
import com.example.wardrobeassistant.db.entity.Clothing;
import com.qmuiteam.qmui.arch.QMUIActivity;
import com.qmuiteam.qmui.widget.QMUITopBar;

import java.util.List;

public class MainActivity extends QMUIActivity {
    QMUITopBar topBar;
    TextView diaplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        diaplay = findViewById(R.id.diaplay);
        topBar = findViewById(R.id.topBar);
        topBar.setTitle("132");
    }

    public void insert(View view) {
        Clothing clothing = new Clothing();
        clothing.setClothingName("衣服");
        clothing.setClothingColorSystem(Constant.COLOR_COLD_SYSTEM);
        clothing.setClothingInputTime(System.currentTimeMillis());
        clothing.setClothingLocation("123");
        clothing.setClothingOccasion(Constant.OCCASION_SPORT);
        clothing.setClothingType(Constant.TYPE_OVERCOAT);
        clothing.setClothingWarmthLevel(Constant.WARMTH_LEVEL_SUMMER);
        clothing.setClothingLocationChangeTime(System.currentTimeMillis());
        DbManager.getInstance().getSession().insert(clothing);
    }

    public void read(View view) {
        List<Clothing> list = DbManager.getInstance().getSession().getClothingDao().loadAll();
        diaplay.setText(list.toString());
    }
}
